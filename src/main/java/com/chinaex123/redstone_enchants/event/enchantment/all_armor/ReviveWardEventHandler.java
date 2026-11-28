package com.chinaex123.redstone_enchants.event.enchantment.all_armor;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 重生护盾：受到致命伤害时阻止死亡，并给予生命恢复/抗性提升和抗火，触发后该附魔消失1个等级
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class ReviveWardEventHandler {
    private static final ResourceKey<Enchantment> REVIVE_WARD = EnchantContext.key("revive_ward");

    /** 触发后保留的生命值，避免直接死亡 */
    private static final float REMAINING_HEALTH = 0.5F;
    /** 不死图腾粒子的生成次数 */
    private static final int PARTICLE_COUNT = 30;
    /** 粒子的随机扩散幅度 */
    private static final double PARTICLE_SPREAD = 0.02D;
    /** 粒子生成位置相对生物高度的比例 */
    private static final double PARTICLE_HEIGHT_FACTOR = 0.5D;
    /** 每次生成的粒子数量 */
    private static final int PARTICLE_COUNT_PER_EMISSION = 1;
    /** 粒子运动速度 */
    private static final double PARTICLE_SPEED = 1.0D;
    /** 药水效果持续时间（tick） */
    private static final int EFFECT_DURATION_TICKS = 20 * 40;
    /** 抗火效果等级 */
    private static final int FIRE_RESISTANCE_AMPLIFIER = 0;
    /** 生命恢复效果等级 */
    private static final int REGENERATION_AMPLIFIER = 1;
    /** 抗性提升效果等级 */
    private static final int DAMAGE_RESISTANCE_AMPLIFIER = 2;
    /** 等级记录的初始值（视为未附魔） */
    private static final int NO_LEVEL = 0;
    /** 触发降级的等级阈值（高于该值时降级） */
    private static final int LEVEL_REDUCTION_THRESHOLD = 1;
    /** 每次触发降低的等级数 */
    private static final int LEVEL_REDUCTION_AMOUNT = 1;
    /** 不死图腾音效音量 */
    private static final float SOUND_VOLUME = 1.0F;
    /** 不死图腾音效音调 */
    private static final float SOUND_PITCH = 1.0F;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (event.getEntity().level().isClientSide()) return;
        LivingEntity entity = event.getEntity();

        // 检查是否是玩家（或其他生物）
        float damage = event.getOriginalDamage();
        float remainingHealth = entity.getHealth() - damage;

        // 只有当伤害会导致死亡时才触发
        if (remainingHealth > 0) return;

        // 检查所有装备槽位是否有重生护盾附魔
        List<EquipmentSlot> slotsWithEnchant = new ArrayList<>();
        int maxLevel = NO_LEVEL;
        EquipmentSlot maxLevelSlot = null;

        EquipmentSlot[] armorSlots = {
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET
        };

        for (EquipmentSlot slot : armorSlots) {
            ItemStack armor = entity.getItemBySlot(slot);
            if (armor.isEmpty()) continue;

            int enchantLevel = EnchantContext.level(armor, REVIVE_WARD);

            if (enchantLevel > 0) {
                slotsWithEnchant.add(slot);
                if (enchantLevel > maxLevel) {
                    maxLevel = enchantLevel;
                    maxLevelSlot = slot;
                }
            }
        }

        // 如果没有重生护盾附魔，不触发
        if (slotsWithEnchant.isEmpty()) return;

        // 阻止死亡：将伤害设置为当前生命值-0.5（保留一点血）
        event.setNewDamage(entity.getHealth() - REMAINING_HEALTH);

        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                SoundEvents.TOTEM_USE, SoundSource.PLAYERS, SOUND_VOLUME, SOUND_PITCH);

        if (entity.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < PARTICLE_COUNT; ++i) {
                double d0 = serverLevel.random.nextGaussian() * PARTICLE_SPREAD;
                double d1 = serverLevel.random.nextGaussian() * PARTICLE_SPREAD;
                double d2 = serverLevel.random.nextGaussian() * PARTICLE_SPREAD;
                serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING,
                        entity.getX(), entity.getY() + entity.getBbHeight() * PARTICLE_HEIGHT_FACTOR, entity.getZ(),
                        PARTICLE_COUNT_PER_EMISSION, d0, d1, d2, PARTICLE_SPEED);
            }
        }

        // 抗火
        entity.addEffect(new MobEffectInstance(
                MobEffects.FIRE_RESISTANCE, EFFECT_DURATION_TICKS, FIRE_RESISTANCE_AMPLIFIER, false, true
        ));
        // 生命恢复
        entity.addEffect(new MobEffectInstance(
                MobEffects.REGENERATION, EFFECT_DURATION_TICKS, REGENERATION_AMPLIFIER, false, true
        ));
        // 抗性提升
        entity.addEffect(new MobEffectInstance(
                MobEffects.DAMAGE_RESISTANCE, EFFECT_DURATION_TICKS, DAMAGE_RESISTANCE_AMPLIFIER, false, true
        ));

        ItemStack armor = entity.getItemBySlot(maxLevelSlot);
        if (!armor.isEmpty()) {
            ItemEnchantments currentEnchants = armor.get(DataComponents.ENCHANTMENTS);
            if (currentEnchants == null) return;

            Holder<Enchantment> reviveWard = null;
            for (Object2IntMap.Entry<Holder<Enchantment>> entry : currentEnchants.entrySet()) {
                ResourceKey<Enchantment> entryKey = entry.getKey().getKey();
                if (entryKey != null && entryKey.equals(REVIVE_WARD)) {
                    reviveWard = entry.getKey();
                    break;
                }
            }
            if (reviveWard == null) return;

            int currentLevel = currentEnchants.getLevel(reviveWard);
            ItemEnchantments.Mutable mutableEnchants = new ItemEnchantments.Mutable(currentEnchants);

            if (currentLevel > LEVEL_REDUCTION_THRESHOLD) {
                // 如果等级大于1，减少1级
                mutableEnchants.set(reviveWard, currentLevel - LEVEL_REDUCTION_AMOUNT);
            } else {
                // 如果等级是1，移除附魔
                Holder<Enchantment> target = reviveWard;
                mutableEnchants.removeIf(holder -> holder.equals(target));
            }

            armor.set(DataComponents.ENCHANTMENTS, mutableEnchants.toImmutable());
        }
    }
}
