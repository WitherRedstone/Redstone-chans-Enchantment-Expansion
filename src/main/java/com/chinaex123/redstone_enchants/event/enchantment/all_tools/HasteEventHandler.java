package com.chinaex123.redstone_enchants.event.enchantment.all_tools;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * 急速：手持时获得急迫效果
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
@SuppressWarnings("deprecation")
public class HasteEventHandler {
    private static final ResourceLocation HASTE_ID = RedstoneEnchants.id("haste");

    /** 持续时间 */
    private static final int HASTE_DURATION = 120;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        ItemStack mainHand = player.getMainHandItem();

        Holder.Reference<Enchantment> hasteEnchant = player.level()
                .registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(HASTE_ID)
                .orElse(null);

        if (hasteEnchant == null) return;

        int level = mainHand.getEnchantments().getLevel(hasteEnchant);

        if (level > 0) {
            if (player.hasEffect(MobEffects.DIG_SPEED)) {
                MobEffectInstance existingEffect = player.getEffect(MobEffects.DIG_SPEED);
                if (existingEffect != null && existingEffect.getAmplifier() >= level - 1) {
                    return;
                }
            }

            player.addEffect(new MobEffectInstance(
                    MobEffects.DIG_SPEED,
                    HASTE_DURATION,
                    level - 1,
                    false,
                    false
            ));
        } else {
            if (player.hasEffect(MobEffects.DIG_SPEED)) {
                player.removeEffect(MobEffects.DIG_SPEED);
            }
        }
    }
}