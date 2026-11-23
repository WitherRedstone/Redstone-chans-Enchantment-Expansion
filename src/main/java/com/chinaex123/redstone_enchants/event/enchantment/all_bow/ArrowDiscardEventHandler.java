package com.chinaex123.redstone_enchants.event.enchantment.all_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

/**
 * 爆炸箭系列：命中方块后让箭矢消失（爆炸本身由附魔数据里的 explode 效果负责）。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class ArrowDiscardEventHandler {

    private static final String[] DISCARD_ON_HIT = {
            "blast_arrows",
            "bomb_arrows",
            "splash_delayed_explosion"
    };

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof AbstractArrow arrow)) return;
        if (!(event.getRayTraceResult() instanceof BlockHitResult)) return;
        if (!(arrow.level() instanceof ServerLevel level)) return;

        for (String id : DISCARD_ON_HIT) {
            if (EnchantContext.level(level, id, arrow.getWeaponItem()) > 0) {
                arrow.discard();
                return;
            }
        }
    }
}
