package com.chinaex123.redstone_enchants.event.enchantment.trident_and_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 传送：命中时与目标交换位置。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class TeleportEventHandler {

    private static final String ID = "teleport";

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!(attacker.level() instanceof ServerLevel level)) return;
        if (!(event.getEntity() instanceof LivingEntity victim)) return;
        if (EnchantContext.level(attacker, ID) <= 0) return;

        EnchantContext context = new EnchantContext(level, attacker, attacker.position());
        // 亡灵与傀儡不会被传送
        if (context.isNonLiving(victim)) return;

        Vec3 attackerPos = attacker.position();
        Vec3 victimPos = victim.position();

        teleport(context, attacker, victimPos);
        teleport(context, victim, attackerPos);
    }

    private static void teleport(EnchantContext context, Entity target, Vec3 destination) {
        context.teleport(target, destination);
        EnchantContext effect = new EnchantContext(context.level(), target, destination);
        effect.particleAt(destination.add(0.0D, 0.5D, 0.0D), ParticleTypes.REVERSE_PORTAL,
                100, 0.3D, 0.7D, 0.3D, 0.0D);
        effect.soundHostile(SoundEvents.ENDERMAN_TELEPORT, 0.5F, 0.5F);
    }
}
