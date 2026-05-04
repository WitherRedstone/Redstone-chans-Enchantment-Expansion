package com.chinaex123.redstone_enchants.event.all_armor;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
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
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class ReviveWardEventHandler {
    private static final Identifier REVIVE_WARD_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "revive_ward");

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        LivingEntity entity = event.getEntity();

        // 检查是否是玩家（或其他生物）
        float damage = event.getOriginalDamage();
        float remainingHealth = entity.getHealth() - damage;

        // 只有当伤害会导致死亡时才触发
        if (remainingHealth > 0) return;

        // 检查所有装备槽位是否有重生护盾附魔
        List<EquipmentSlot> slotsWithEnchant = new ArrayList<>();
        int maxLevel = 0;
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

            Holder.Reference<Enchantment> reviveWardEnchant = entity.level()
                    .registryAccess()
                    .lookupOrThrow(Registries.ENCHANTMENT)
                    .get(REVIVE_WARD_ID)
                    .orElse(null);

            if (reviveWardEnchant == null) continue;

            @SuppressWarnings("deprecation")
            int enchantLevel = armor.getEnchantments().getLevel(reviveWardEnchant);

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
        event.setNewDamage(entity.getHealth() - 0.5f);

        // 播放不死图腾音效
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);

        // 生成粒子效果
        if (entity.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 30; ++i) {
                double d0 = serverLevel.getRandom().nextGaussian() * 0.02D;
                double d1 = serverLevel.getRandom().nextGaussian() * 0.02D;
                double d2 = serverLevel.getRandom().nextGaussian() * 0.02D;
                serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING,
                        entity.getX(), entity.getY() + entity.getBbHeight() * 0.5D, entity.getZ(),
                        1, d0, d1, d2, 1.0D);
            }
        }

        // 抗火
        entity.addEffect(new MobEffectInstance(
                MobEffects.FIRE_RESISTANCE, 20 * 40, 0, false, true
        ));
        // 生命恢复
        entity.addEffect(new MobEffectInstance(
                MobEffects.REGENERATION, 20 * 40, 1, false, true
        ));
        // 抗性提升
        entity.addEffect(new MobEffectInstance(
                MobEffects.RESISTANCE, 20 * 40, 2, false, true
        ));

        // 减少最高等级的附魔1级
        ItemStack armor = entity.getItemBySlot(maxLevelSlot);
        if (!armor.isEmpty()) {
            Holder.Reference<Enchantment> reviveWardEnchant = entity.level()
                    .registryAccess()
                    .lookupOrThrow(Registries.ENCHANTMENT)
                    .get(REVIVE_WARD_ID)
                    .orElse(null);

            if (reviveWardEnchant != null) {
                @SuppressWarnings("deprecation")
                int currentLevel = armor.getEnchantments().getLevel(reviveWardEnchant);

                ItemEnchantments currentEnchants = armor.get(DataComponents.ENCHANTMENTS);
                if (currentEnchants != null) {
                    // 使用 Mutable 来修改附魔
                    ItemEnchantments.Mutable mutableEnchants = new ItemEnchantments.Mutable(currentEnchants);

                    if (currentLevel > 1) {
                        // 如果等级大于1，减少1级
                        mutableEnchants.set(reviveWardEnchant, currentLevel - 1);
                    } else {
                        // 如果等级是1，移除附魔
                        mutableEnchants.removeIf(enchant -> enchant.equals(reviveWardEnchant));
                    }

                    armor.set(DataComponents.ENCHANTMENTS, mutableEnchants.toImmutable());
                }
            }
        }
    }
}
