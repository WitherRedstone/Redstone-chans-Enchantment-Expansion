package com.chinaex123.redstone_enchants.event.enchantment.weapon;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 经验之刃：消耗15点经验值提升攻击伤害(需10级启动)。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class XpBladeEventHandler {

    private static final String ID = "xp_blade";

    /** 小号的经验值 */
    private static final int XP_COST = -15;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!(attacker.level() instanceof ServerLevel level)) return;
        if (EnchantContext.level(attacker, ID) <= 0) return;

        new EnchantContext(level, attacker, attacker.position()).giveExperience(XP_COST);
    }
}
