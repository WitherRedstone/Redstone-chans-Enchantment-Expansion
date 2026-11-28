package com.chinaex123.redstone_enchants.event.enchantment.swords;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
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
 * 福佑：攻击时有概率获得随机一个正面效果
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class BoonsEventHandler {
    private static final ResourceLocation BOONS_ID = RedstoneEnchants.id("boons");

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

        ResourceKey<Enchantment> boonsEnchant = EnchantContext.key(BOONS_ID);

        int level = EnchantContext.level(weapon, boonsEnchant);
        if (level <= 0) return;

        // 1 级 5% 概率触发，每级额外 +5%
        double chance = Math.min(1.0D, CHANCE_BASE + CHANCE_PER_LEVEL * (level - 1));
        if (RANDOM.nextFloat() < chance) {
            // 获取所有正面效果
            List<Holder.Reference<MobEffect>> positiveEffects = new ArrayList<>();
            attacker.level().registryAccess()
                    .registryOrThrow(Registries.MOB_EFFECT)
                    .holders()
                    .forEach(effect -> {
                        if (effect.value().isBeneficial()) {
                            positiveEffects.add(effect);
                        }
                    });

            if (!positiveEffects.isEmpty()) {
                // 随机选择一个正面效果
                Holder.Reference<MobEffect> effect = positiveEffects.get(RANDOM.nextInt(positiveEffects.size()));

                // 持续时间：4-10秒
                int duration = (MIN_DURATION_SECONDS + RANDOM.nextInt(DURATION_RANGE_SECONDS)) * TICKS_PER_SECOND;
                int amplifier = AMPLIFIER;

                attacker.addEffect(new MobEffectInstance(effect, duration, amplifier));
            }
        }
    }
}
