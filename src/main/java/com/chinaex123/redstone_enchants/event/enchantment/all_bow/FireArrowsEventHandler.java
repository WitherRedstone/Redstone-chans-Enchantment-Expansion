package com.chinaex123.redstone_enchants.event.enchantment.all_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 火焰箭：命中区域产生火焰效果。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class FireArrowsEventHandler {

    private static final String ID = "fire_arrows";

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof AbstractArrow arrow)) return;
        if (!(event.getRayTraceResult() instanceof BlockHitResult hit)) return;
        if (!(arrow.level() instanceof ServerLevel level)) return;
        if (EnchantContext.level(level, ID, arrow.getWeaponItem()) <= 0) return;

        ignite(level, hit.getLocation());
        arrow.discard();
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)) return;
        if (!(arrow.level() instanceof ServerLevel level)) return;
        if (EnchantContext.level(level, ID, arrow.getWeaponItem()) <= 0) return;

        ignite(level, event.getEntity().position());
        arrow.discard();
    }

    private static void ignite(ServerLevel level, Vec3 center) {
        BlockPos origin = BlockPos.containing(center);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos pos = origin.offset(dx, dy, dz);
                    if (level.getBlockState(pos).isAir()) {
                        level.setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
                    }
                }
            }
        }
        level.sendParticles(ParticleTypes.FLAME, center.x, center.y, center.z, 50, 1.0D, 1.0D, 1.0D, 0.5D);
    }
}
