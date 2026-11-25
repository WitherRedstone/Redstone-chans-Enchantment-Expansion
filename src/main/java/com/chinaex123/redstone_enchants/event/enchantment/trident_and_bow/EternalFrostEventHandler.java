package com.chinaex123.redstone_enchants.event.enchantment.trident_and_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.util.FreezePicAnimation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 永恒冰霜：命中后冻结目标及周围水域并将水转为霜冰。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class EternalFrostEventHandler {

    private static final String ID = "eternal_frost";

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        HitResult hit = event.getRayTraceResult();
        if (hit.getType() != HitResult.Type.BLOCK) return;

        Projectile projectile = event.getProjectile();
        if (!(projectile.level() instanceof ServerLevel level)) return;
        if (!(projectile instanceof AbstractArrow arrow)) return;

        ItemStack weapon = arrow.getWeaponItem();
        if (EnchantContext.level(level, ID, weapon) <= 0) return;

        EnchantContext context = new EnchantContext(level, projectile, hit.getLocation()).at(0.0D, 0.25D, 0.0D);
        FreezePicAnimation.play(context);
        if (!(projectile instanceof ThrownTrident)) {
            context.discard(projectile);
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!(attacker.level() instanceof ServerLevel level)) return;
        if (EnchantContext.level(attacker, ID) <= 0) return;

        EnchantContext context = new EnchantContext(level, event.getEntity(), event.getEntity().position())
                .at(0.0D, 0.25D, 0.0D);
        FreezePicAnimation.play(context);
    }
}
