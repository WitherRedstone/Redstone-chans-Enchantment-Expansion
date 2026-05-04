package com.chinaex123.redstone_enchants.event.swords;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
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
 * 恩赐：攻击时有概率获得随机一个正面效果
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class BoonsEventHandler {
    private static final Identifier BOONS_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "boons");
    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getDirectEntity() instanceof Player attacker)) return;

        LivingEntity target = event.getEntity();
        ItemStack weapon = attacker.getMainHandItem();

        if (weapon.isEmpty()) return;

        Holder.Reference<Enchantment> boonsEnchant = attacker.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(BOONS_ID)
                .orElse(null);

        if (boonsEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = weapon.getEnchantments().getLevel(boonsEnchant);
        if (level <= 0) return;

        // 每级5%概率触发
        float chance = level * 0.05F;
        if (RANDOM.nextFloat() < chance) {
            // 获取所有正面效果
            List<Holder.Reference<MobEffect>> positiveEffects = new ArrayList<>();
            attacker.level().registryAccess()
                    .lookupOrThrow(Registries.MOB_EFFECT)
                    .listElements()
                    .forEach(effect -> {
                        if (effect.value().isBeneficial()) {
                            positiveEffects.add(effect);
                        }
                    });

            if (!positiveEffects.isEmpty()) {
                // 随机选择一个正面效果
                Holder.Reference<MobEffect> effect = positiveEffects.get(RANDOM.nextInt(positiveEffects.size()));

                // 持续时间：4-10秒
                int duration = (4 + RANDOM.nextInt(7)) * 20;
                int amplifier = 0; // 1级效果

                attacker.addEffect(new MobEffectInstance(effect, duration, amplifier));
            }
        }
    }
}
