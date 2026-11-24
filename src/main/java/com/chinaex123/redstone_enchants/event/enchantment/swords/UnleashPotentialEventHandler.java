package com.chinaex123.redstone_enchants.event.enchantment.swords;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * 释放潜能：饥饿值低于3鸡腿时获得急迫、速度、力量(随饥饿值降低而增强)。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class UnleashPotentialEventHandler {

    private static final String ID = "unleash_potential";

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!(player.level() instanceof ServerLevel level)) return;
        if (player.getDeltaMovement().horizontalDistanceSqr() < 1.0E-4D) return;
        if (EnchantContext.level(player, ID) <= 0) return;

        EnchantContext context = new EnchantContext(level, player, player.position());
        int food = context.foodLevel();
        if (food < 0 || food > 6) return;

        int amplifier = 6 - food;
        context.effect(MobEffects.DIG_SPEED, 1.0F, amplifier);
        context.effect(MobEffects.MOVEMENT_SPEED, 1.0F, amplifier);
        context.effect(MobEffects.DAMAGE_BOOST, 1.0F, amplifier);
    }
}
