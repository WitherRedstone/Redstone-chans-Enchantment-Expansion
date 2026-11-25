package com.chinaex123.redstone_enchants.event.enchantment.swords;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 吞噬：攻击生物时恢复饥饿值；饥饿值已满时，把这次恢复转化为生命恢复。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class DevouringEventHandler {

    private static final String ID = "devouring";

    /** 每次命中恢复的饥饿值（2 点 = 1 个鸡腿） */
    private static final int HUNGER_PER_HIT = 2;

    /** 饥饿值已满时给予的生命恢复持续时间（秒） */
    private static final float REGENERATION_SECONDS = 1.0F;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        // 只有玩家攻击生物才生效
        if (!(event.getSource().getEntity() instanceof Player attacker)) return;
        if (event.getEntity() == attacker) return;
        if (!(attacker.level() instanceof ServerLevel level)) return;
        if (EnchantContext.level(attacker, ID) <= 0) return;

        FoodData food = attacker.getFoodData();
        if (food.getFoodLevel() >= 20) {
            // 饥饿值已满：不再回复饥饿值，改为生命恢复
            new EnchantContext(level, attacker, attacker.position())
                    .effect(MobEffects.REGENERATION, REGENERATION_SECONDS, 0);
        } else {
            // 直接回复饥饿值，而不是给饱和效果
            food.eat(HUNGER_PER_HIT, 1.0F);
        }
    }
}
