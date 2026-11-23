package com.chinaex123.redstone_enchants.event.enchantment.trident;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

/**
 * 雷击：三叉戟在雨天命中避雷针时召唤雷暴天气。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class ThunderingEventHandler {

    private static final String ID = "thundering";

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        HitResult hit = event.getRayTraceResult();
        if (!(hit instanceof BlockHitResult blockHit)) return;
        if (!(event.getProjectile() instanceof ThrownTrident trident)) return;
        if (!(trident.level() instanceof ServerLevel level)) return;

        // 必须命中避雷针
        if (!level.getBlockState(blockHit.getBlockPos()).is(Blocks.LIGHTNING_ROD)) return;
        // 必须正在下雨，并且能看见天空
        if (!level.isRaining()) return;
        if (!level.canSeeSky(blockHit.getBlockPos())) return;

        ItemStack weapon = trident.getWeaponItem();
        if (EnchantContext.level(level, ID, weapon) <= 0) return;

        new EnchantContext(level, trident, trident.position()).thunder();
    }
}
