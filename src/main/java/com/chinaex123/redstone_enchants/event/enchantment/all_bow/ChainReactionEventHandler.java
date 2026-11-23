package com.chinaex123.redstone_enchants.event.enchantment.all_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 连锁反应：箭矢命中时向六个方向散射同级箭矢。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class ChainReactionEventHandler {

    private static final String ID = "chain_reaction";

    private static final String ARROW_ID = "minecraft:arrow";
    private static final String SPECTRAL_ARROW_ID = "minecraft:spectral_arrow";

    private static final double[][] MOTIONS = {
            {1.0D, 0.0D},
            {-1.0D, 0.0D},
            {0.5D, 0.866D},
            {-0.5D, 0.866D},
            {0.5D, -0.866D},
            {-0.5D, -0.866D}
    };

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)) return;
        if (!(arrow.level() instanceof ServerLevel level)) return;
        if (EnchantContext.level(level, ID, arrow.getWeaponItem()) <= 0) return;

        EnchantContext context = new EnchantContext(level, event.getEntity(), event.getEntity().position());
        String entityId = arrow instanceof net.minecraft.world.entity.projectile.SpectralArrow
                ? SPECTRAL_ARROW_ID : ARROW_ID;
        String snbt = "{id:\"" + entityId + "\",pickup:2b}";

        for (double[] motion : MOTIONS) {
            Entity spawned = context.summon(snbt, 0.0D, 0.8D, 0.0D);
            if (spawned != null) {
                context.motion(spawned, new net.minecraft.world.phys.Vec3(motion[0], 0.0D, motion[1]));
            }
        }
    }
}
