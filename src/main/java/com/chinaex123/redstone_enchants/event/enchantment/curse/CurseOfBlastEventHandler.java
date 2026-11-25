package com.chinaex123.redstone_enchants.event.enchantment.curse;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 自爆：穿戴者受到任何伤害时都有几率在脚下引发一次 TNT 爆炸。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class CurseOfBlastEventHandler {

    private static final String ID = "curse_of_blast";

    /** 触发概率。 */
    private static final float CHANCE = 0.3F;

    /** 爆炸半径。 */
    private static final float RADIUS = 3.0F;

    /** 点燃时长（5 秒）。 */
    private static final int IGNITE_TICKS = 100;

    /** 爆炸结算过程中产生的伤害不再触发新的爆炸，避免无限递归。 */
    private static boolean exploding = false;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (exploding) return;

        LivingEntity victim = event.getEntity();
        if (!(victim.level() instanceof ServerLevel level)) return;
        if (EnchantContext.level(victim, ID) <= 0) return;
        if (level.getRandom().nextFloat() >= CHANCE) return;

        exploding = true;
        try {
            level.explode(null, victim.getX(), victim.getY() + 1.0D, victim.getZ(), RADIUS, true, Level.ExplosionInteraction.TNT);
        } finally {
            exploding = false;
        }

        victim.setRemainingFireTicks(Math.max(victim.getRemainingFireTicks(), IGNITE_TICKS));
    }
}
