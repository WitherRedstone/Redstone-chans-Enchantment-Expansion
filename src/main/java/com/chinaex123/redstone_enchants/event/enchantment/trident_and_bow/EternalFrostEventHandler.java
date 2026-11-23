package com.chinaex123.redstone_enchants.event.enchantment.trident_and_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 永冻：命中时在目标位置展开冰晶动画，并冻结周围的方块。
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
        context.discard(projectile);
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
