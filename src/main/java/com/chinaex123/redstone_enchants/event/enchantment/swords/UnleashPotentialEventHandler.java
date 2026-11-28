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
    /** 饥饿值触发阈值（3 个鸡腿 = 6 点） */
    private static final int FOOD_THRESHOLD = 6;
    /** 效果等级基数（饥饿值越低效果越强） */
    private static final int AMPLIFIER_BASE = 6;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        Player player = event.getEntity();
        if (!(player.level() instanceof ServerLevel level)) return;
        if (EnchantContext.level(player, ID) <= 0) return;

        int food = player.getFoodData().getFoodLevel();
        if (food > FOOD_THRESHOLD) return;

        int amplifier = AMPLIFIER_BASE - food;
        EnchantContext context = new EnchantContext(level, player, player.position());
        context.effect(MobEffects.DIG_SPEED, DURATION_SECONDS, amplifier);
        context.effect(MobEffects.MOVEMENT_SPEED, DURATION_SECONDS, amplifier);
        context.effect(MobEffects.DAMAGE_BOOST, DURATION_SECONDS, amplifier);
    }
}
