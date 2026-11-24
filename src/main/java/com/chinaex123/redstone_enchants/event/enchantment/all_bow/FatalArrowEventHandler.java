package com.chinaex123.redstone_enchants.event.enchantment.all_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 绝命箭矢：一击必杀非Boss生物(无掉落战利品，使用后给予DeBuff)。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class FatalArrowEventHandler {

    private static final String ID = "fatal_arrow";

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)) return;
        if (!(arrow.level() instanceof ServerLevel level)) return;
        if (EnchantContext.level(level, ID, arrow.getWeaponItem()) <= 0) return;
        LivingEntity victim = event.getEntity();
        if (victim.getType().is(EnchantContext.BLACK_ENTITY)) return;

        EnchantContext context = new EnchantContext(level, victim, victim.position());
        context.particle(ParticleTypes.SONIC_BOOM, 50, 1.0D, 1.0D, 1.0D, 0.5D);
        context.applyEffect(victim, MobEffects.INVISIBILITY, -1, 0);
        context.mergeNbt(victim, "{DeathLootTable:\"minecraft:empty\"}");
        context.clearInventory();
        context.kill(victim);
        arrow.discard();
    }
}
