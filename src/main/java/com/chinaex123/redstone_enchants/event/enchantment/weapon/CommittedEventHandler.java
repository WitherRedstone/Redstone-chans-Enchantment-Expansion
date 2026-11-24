package com.chinaex123.redstone_enchants.event.enchantment.weapon;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 承诺：击中已经受伤的生物会造成更高的伤害。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class CommittedEventHandler {

    private static final String ID = "committed";
    private static final String MARK_TAG = "harmed_by_committed";

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!(attacker.level() instanceof ServerLevel level)) return;
        LivingEntity victim = event.getEntity();
        if (EnchantContext.level(attacker, ID) <= 0) return;

        EnchantContext context = new EnchantContext(level, victim, victim.position());
        context.particleAt(context.pos().add(0.0D, 2.2D, 0.0D), ParticleTypes.ANGRY_VILLAGER,
                1, 0.0D, 0.0D, 0.0D, 0.0D);
        context.tagAdd(victim, MARK_TAG);
    }
}
