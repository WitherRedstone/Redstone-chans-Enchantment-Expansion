package com.chinaex123.redstone_enchants.event.enchantment.swords;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
 * 消解：攻击时有概率移除目标身上的一个正面效果
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class NullifyEventHandler {
    private static final ResourceLocation NULLIFY_ID = RedstoneEnchants.id("nullify");

    /** 1 级时的触发概率 */
    private static final double CHANCE_BASE = 0.05D;
    /** 每级额外增加的触发概率 */
    private static final double CHANCE_PER_LEVEL = 0.05D;

    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getDirectEntity() instanceof Player attacker)) return;

        LivingEntity target = event.getEntity();
        ItemStack weapon = attacker.getMainHandItem();

        if (weapon.isEmpty()) return;

        ResourceKey<Enchantment> nullifyEnchant = EnchantContext.key(NULLIFY_ID);

        int level = EnchantContext.level(weapon, nullifyEnchant);
        if (level <= 0) return;

        // 1 级 5% 概率触发，每级额外 +5%
        double chance = Math.min(1.0D, CHANCE_BASE + CHANCE_PER_LEVEL * (level - 1));
        if (RANDOM.nextFloat() < chance) {
            // 获取目标身上所有正面效果
            List<MobEffectInstance> positiveEffects = new ArrayList<>();
            target.getActiveEffects().forEach(effectInstance -> {
                if (effectInstance.getEffect().value().isBeneficial()) {
                    positiveEffects.add(effectInstance);
                }
            });

            if (!positiveEffects.isEmpty()) {
                // 随机选择一个正面效果并移除
                MobEffectInstance effectToRemove = positiveEffects.get(RANDOM.nextInt(positiveEffects.size()));
                target.removeEffect(effectToRemove.getEffect());
            }
        }
    }
}
