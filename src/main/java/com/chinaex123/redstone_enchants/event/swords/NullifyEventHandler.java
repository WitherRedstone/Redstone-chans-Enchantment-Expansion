package com.chinaex123.redstone_enchants.event.swords;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
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
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class NullifyEventHandler {
    private static final Identifier NULLIFY_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "nullify");
    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getDirectEntity() instanceof Player attacker)) return;

        LivingEntity target = event.getEntity();
        ItemStack weapon = attacker.getMainHandItem();

        if (weapon.isEmpty()) return;

        Holder.Reference<Enchantment> nullifyEnchant = attacker.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(NULLIFY_ID)
                .orElse(null);

        if (nullifyEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = weapon.getEnchantments().getLevel(nullifyEnchant);
        if (level <= 0) return;

        // 每级5%概率触发
        float chance = level * 0.05F;
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
