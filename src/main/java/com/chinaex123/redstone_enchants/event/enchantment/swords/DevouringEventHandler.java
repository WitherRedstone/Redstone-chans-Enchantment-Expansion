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
    /** 饥饿值上限 */
    private static final int MAX_FOOD_LEVEL = 20;
    /** 药水效果等级（0 为 I 级） */
    private static final int AMPLIFIER = 0;
    /** 恢复饥饿值时的饱和度系数 */
    private static final float SATURATION_MODIFIER = 1.0F;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getEntity() instanceof Player attacker)) return;
        if (event.getEntity() == attacker) return;
        if (!(attacker.level() instanceof ServerLevel level)) return;
        if (EnchantContext.level(attacker, ID) <= 0) return;

        FoodData food = attacker.getFoodData();
        if (food.getFoodLevel() >= MAX_FOOD_LEVEL) {
            // 饥饿值已满：不再回复饥饿值，改为生命恢复
            new EnchantContext(level, attacker, attacker.position())
                    .effect(MobEffects.REGENERATION, REGENERATION_SECONDS, AMPLIFIER);
        } else {
            // 直接回复饥饿值，而不是给饱和效果
            food.eat(HUNGER_PER_HIT, SATURATION_MODIFIER);
        }
    }
}
