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
 * 激发潜能：饥饿值低于 3 个鸡腿（6 点）时获得急迫、速度、力量，饥饿值越低效果越强。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class UnleashPotentialEventHandler {

    private static final String ID = "unleash_potential";

    /** 效果持续时间（秒） */
    private static final float DURATION_SECONDS = 2.0F;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!(player.level() instanceof ServerLevel level)) return;
        if (EnchantContext.level(player, ID) <= 0) return;

        int food = player.getFoodData().getFoodLevel();
        if (food > 6) return;

        int amplifier = 6 - food;
        EnchantContext context = new EnchantContext(level, player, player.position());
        context.effect(MobEffects.DIG_SPEED, DURATION_SECONDS, amplifier);
        context.effect(MobEffects.MOVEMENT_SPEED, DURATION_SECONDS, amplifier);
        context.effect(MobEffects.DAMAGE_BOOST, DURATION_SECONDS, amplifier);
    }
}
