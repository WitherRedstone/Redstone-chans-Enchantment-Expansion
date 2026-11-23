package com.chinaex123.redstone_enchants.event.enchantment.swords;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 背水一战：攻击时消耗主手武器（放手一搏），并在目标身上炸开冲击波粒子。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class LastHopeEventHandler {

    private static final String ID = "last_hope";

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!(attacker.level() instanceof ServerLevel level)) return;
        if (EnchantContext.level(attacker, ID) <= 0) return;
        if (attacker.getType().is(EnchantContext.BLACK_ENTITY)) return;

        EnchantContext context = new EnchantContext(level, attacker, attacker.position());
        context.clearMainHand();

        if (event.getEntity() != null) {
            new EnchantContext(level, event.getEntity(), event.getEntity().position())
                    .particle(ParticleTypes.SONIC_BOOM, 50, 1.0D, 1.0D, 1.0D, 0.5D);
        }
    }
}
