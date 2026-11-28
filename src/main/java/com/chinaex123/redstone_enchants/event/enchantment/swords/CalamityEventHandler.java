package com.chinaex123.redstone_enchants.event.enchantment.swords;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 灾厄：攻击时有概率让目标获得随机一个负面效果
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class CalamityEventHandler {
    private static final ResourceLocation CALAMITY_ID = RedstoneEnchants.id("calamity");

    /** 1 级时的触发概率 */
    private static final double CHANCE_BASE = 0.05D;
    /** 每级额外增加的触发概率 */
    private static final double CHANCE_PER_LEVEL = 0.05D;
    /** 效果最短持续时间（秒） */
    private static final int MIN_DURATION_SECONDS = 4;
    /** 效果持续时间的随机秒数范围 */
    private static final int DURATION_RANGE_SECONDS = 7;
    /** 每秒对应的 tick 数 */
    private static final int TICKS_PER_SECOND = 20;
    /** 药水效果等级（0 为 I 级） */
    private static final int AMPLIFIER = 0;

    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getDirectEntity() instanceof Player attacker)) return;

        LivingEntity target = event.getEntity();
        ItemStack weapon = attacker.getMainHandItem();

        if (weapon.isEmpty()) return;

        ResourceKey<Enchantment> calamityEnchant = EnchantContext.key(CALAMITY_ID);

        int level = EnchantContext.level(weapon, calamityEnchant);
        if (level <= 0) return;

        // 1 级 5% 概率触发，每级额外 +5%
        double chance = Math.min(1.0D, CHANCE_BASE + CHANCE_PER_LEVEL * (level - 1));
        if (RANDOM.nextFloat() < chance) {
            // 获取所有负面效果
            List<Holder.Reference<MobEffect>> negativeEffects = new ArrayList<>();
            attacker.level().registryAccess()
                    .registryOrThrow(Registries.MOB_EFFECT)
                    .holders()
                    .forEach(effect -> {
                        if (!effect.value().isBeneficial()) {
                            // 黑名单：排除试炼之兆和不祥之兆
                            if (effect.is(MobEffects.BAD_OMEN) || effect.is(MobEffects.TRIAL_OMEN)) {
                                return;
                            }
                            negativeEffects.add(effect);
                        }
                    });

            if (!negativeEffects.isEmpty()) {
                // 随机选择一个负面效果
                Holder.Reference<MobEffect> effect = negativeEffects.get(RANDOM.nextInt(negativeEffects.size()));

                // 持续时间：4-10秒
                int duration = (MIN_DURATION_SECONDS + RANDOM.nextInt(DURATION_RANGE_SECONDS)) * TICKS_PER_SECOND;
                int amplifier = AMPLIFIER;

                target.addEffect(new MobEffectInstance(effect, duration, amplifier));
            }
        }
    }
}
