package com.chinaex123.redstone_enchants.event.enchantment.all_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

/**
 * 雨系列附魔：箭矢命中方块时，在命中点上方召唤一圈实体。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class RainEventHandler {

    private static final double[][] RAIN_FORGE_OFFSETS = {
            {01.0D, 00.0D},
            {00.7D, 00.7D},
            {00.0D, 01.0D},
            {-0.7D, 00.7D},
            {-1.0D, 00.0D},
            {-0.7D, -0.7D},
            {00.0D, -1.0D},
            {00.7D, -0.7D},
            {2D, 0D},
            {1.847D, 0.765D},
            {1.414D, 1.414D},
            {0.765D, 1.847D},
            {0D, 2D},
            {-0.765D, 1.847D},
            {-1.414D, 1.414D},
            {-1.847D, 0.765D},
            {-2D, 0D},
            {-1.847D, -0.765D},
            {-1.414D, -1.414D},
            {-0.765D, -1.847D},
            {0D, -2D},
            {0.765D, -1.847D},
            {1.414D, -1.414D},
            {1.847D, -0.765D},
            {0D, 0D}
    };

    private static final String RAIN_FORGE_NBT = "{id:\"minecraft:falling_block\",BlockState:{Name:\"minecraft:anvil\"},Time:1,DropItem:0b,CancelDrop:1b,HurtEntities:1b,FallHurtMax:40,FallDistance:5f,FallHurtAmount:1.6f}";

    private static final double[][] RAIN_DRIPSTONE_OFFSETS = {
            {01.0D, 00.0D},
            {00.7D, 00.7D},
            {00.0D, 01.0D},
            {-0.7D, 00.7D},
            {-1.0D, 00.0D},
            {-0.7D, -0.7D},
            {00.0D, -1.0D},
            {00.7D, -0.7D},
            {2D, 0D},
            {1.847D, 0.765D},
            {1.414D, 1.414D},
            {0.765D, 1.847D},
            {0D, 2D},
            {-0.765D, 1.847D},
            {-1.414D, 1.414D},
            {-1.847D, 0.765D},
            {-2D, 0D},
            {-1.847D, -0.765D},
            {-1.414D, -1.414D},
            {-0.765D, -1.847D},
            {0D, -2D},
            {0.765D, -1.847D},
            {1.414D, -1.414D},
            {1.847D, -0.765D},
            {0D, 0D}
    };

    private static final String RAIN_DRIPSTONE_NBT = "{id:\"minecraft:falling_block\",BlockState:{Name:\"minecraft:pointed_dripstone\"},Time:1,DropItem:0b,CancelDrop:1b,HurtEntities:1b,FallHurtMax:40,FallDistance:5f,FallHurtAmount:2.3f}";

    private static void rainForge(EnchantContext context) {
        for (double[] offset : RAIN_FORGE_OFFSETS) {
            context.summon(RAIN_FORGE_NBT, offset[0], 5.0D, offset[1]);
        }
    }

    private static void rainDripstone(EnchantContext context) {
        for (double[] offset : RAIN_DRIPSTONE_OFFSETS) {
            context.summon(RAIN_DRIPSTONE_NBT, offset[0], 5.0D, offset[1]);
        }
    }

    private static final Map<String, Consumer<EnchantContext>> RAINS = new LinkedHashMap<>();

    static {
        RAINS.put("rain_forge", RainEventHandler::rainForge);
        RAINS.put("rain_dripstone", RainEventHandler::rainDripstone);
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof AbstractArrow arrow)) return;
        if (!(event.getRayTraceResult() instanceof BlockHitResult hit)) return;
        if (!(arrow.level() instanceof ServerLevel level)) return;

        EnchantContext context = new EnchantContext(level, arrow, hit.getLocation());
        boolean hitSomething = false;
        for (Map.Entry<String, Consumer<EnchantContext>> entry : RAINS.entrySet()) {
            if (EnchantContext.level(level, entry.getKey(), arrow.getWeaponItem()) <= 0) continue;
            entry.getValue().accept(context);
            hitSomething = true;
        }
        if (hitSomething) {
            arrow.discard();
        }
    }
}
