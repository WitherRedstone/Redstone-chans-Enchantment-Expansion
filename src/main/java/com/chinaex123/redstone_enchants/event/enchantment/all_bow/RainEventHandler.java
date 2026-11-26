package com.chinaex123.redstone_enchants.event.enchantment.all_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

/**
 * 铁砧雨与石锥雨：箭矢命中方块或实体时，在命中点上方召唤一圈坠落物。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class RainEventHandler {

    /** 铁砧雨的坠落物水平偏移表（半径 1 格与半径 2 格各一圈，末尾为中心点） */
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

    /** 石锥雨的坠落物水平偏移表（半径 1 格与半径 2 格各一圈，末尾为中心点） */
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

    /** 坠落物召唤高度（格） */
    private static final double SUMMON_HEIGHT = 5.0D;

    private static void rainForge(EnchantContext context) {
        for (double[] offset : RAIN_FORGE_OFFSETS) {
            context.summon(RAIN_FORGE_NBT, offset[0], SUMMON_HEIGHT, offset[1]);
        }
    }

    private static void rainDripstone(EnchantContext context) {
        for (double[] offset : RAIN_DRIPSTONE_OFFSETS) {
            context.summon(RAIN_DRIPSTONE_NBT, offset[0], SUMMON_HEIGHT, offset[1]);
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
        if (!(arrow.level() instanceof ServerLevel level)) return;

        // 命中方块与命中实体都触发
        HitResult hit = event.getRayTraceResult();
        if (hit == null || hit.getType() == HitResult.Type.MISS) return;

        EnchantContext context = new EnchantContext(level, arrow, hit.getLocation());
        boolean hitSomething = false;
        for (Map.Entry<String, Consumer<EnchantContext>> entry : RAINS.entrySet()) {
            int enchantLevel = EnchantContext.level(level, entry.getKey(), arrow.getWeaponItem());
            if (enchantLevel <= 0) continue;
            entry.getValue().accept(context);
            hitSomething = true;
        }
        if (hitSomething) {
            arrow.discard();
        }
    }
}
