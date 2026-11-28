package com.chinaex123.redstone_enchants.event.enchantment.all_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

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

    /** 铁砧雨的坠落方块 */
    private static final BlockState RAIN_FORGE_BLOCK = Blocks.ANVIL.defaultBlockState();
    /** 铁砧每下落一格造成的伤害 */
    private static final float RAIN_FORGE_HURT_AMOUNT = 1.6F;

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

    /** 石锥雨的坠落方块 */
    private static final BlockState RAIN_DRIPSTONE_BLOCK = Blocks.POINTED_DRIPSTONE.defaultBlockState();
    /** 石锥每下落一格造成的伤害 */
    private static final float RAIN_DRIPSTONE_HURT_AMOUNT = 2.3F;

    /** 坠落物召唤高度（格） */
    private static final double SUMMON_HEIGHT = 5.0D;
    /** 坠落物初始下落刻数 */
    private static final int FALL_TIME = 1;
    /** 坠落物最大伤害 */
    private static final int FALL_HURT_MAX = 40;
    /** 坠落物的「已下落距离」，决定落地时的伤害 */
    private static final float FALL_DISTANCE = 5.0F;

    private static void rainForge(EnchantContext context) {
        spawnRain(context, RAIN_FORGE_OFFSETS, RAIN_FORGE_BLOCK, RAIN_FORGE_HURT_AMOUNT);
    }

    private static void rainDripstone(EnchantContext context) {
        spawnRain(context, RAIN_DRIPSTONE_OFFSETS, RAIN_DRIPSTONE_BLOCK, RAIN_DRIPSTONE_HURT_AMOUNT);
    }

    /**
     * 按偏移表在命中点上方召唤一圈坠落方块。
     */
    private static void spawnRain(EnchantContext context, double[][] offsets, BlockState state, float hurtAmount) {
        for (double[] offset : offsets) {
            FallingBlockEntity block = new FallingBlockEntity(EntityType.FALLING_BLOCK, context.level());
            CompoundTag tag = new CompoundTag();
            tag.put("BlockState", NbtUtils.writeBlockState(state));
            block.load(tag);
            block.blocksBuilding = true;
            block.time = FALL_TIME;
            block.dropItem = false;
            block.disableDrop();
            block.setHurtsEntities(hurtAmount, FALL_HURT_MAX);
            block.fallDistance = FALL_DISTANCE;
            context.spawn(block, offset[0], SUMMON_HEIGHT, offset[1]);
        }
    }

    private static final Map<String, Consumer<EnchantContext>> RAINS = new LinkedHashMap<>();

    static {
        RAINS.put("rain_forge", RainEventHandler::rainForge);
        RAINS.put("rain_dripstone", RainEventHandler::rainDripstone);
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (event.getProjectile().level().isClientSide()) return;
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
