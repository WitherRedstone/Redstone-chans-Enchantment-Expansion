package com.chinaex123.redstone_enchants.event.enchantment.curse;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 死亡诅咒：攻击时有几率立即死亡。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class CurseOfDeathEventHandler {

    private static final String ID = "curse_of_death";

    /** 触发死亡的概率。 */
    private static final float CHANCE = 0.05F;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!(attacker.level() instanceof ServerLevel level)) return;
        if (EnchantContext.level(attacker, ID) <= 0) return;
        if (level.getRandom().nextFloat() >= CHANCE) return;

        new EnchantContext(level, attacker, attacker.position()).kill(attacker);
    }
}
