package com.chinaex123.redstone_enchants.event.enchantment.all_tools;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 挖掘机：可以挖掘3x3/5x5/7x7区域的方块
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class ExcavatorEventHandler {
    private static final ResourceLocation EXCAVATOR_ID = RedstoneEnchants.id("excavator");

    /** 1 级时的挖掘半径（3x3 区域） */
    private static final int RADIUS_BASE = 1;
    /** 每级额外增加的挖掘半径 */
    private static final int RADIUS_PER_LEVEL = 1;
    /** 挖掘半径上限（7x7 区域） */
    private static final int RADIUS_MAX = 3;
    /** 每破坏一个方块消耗的耐久 */
    private static final int DURABILITY_COST = 1;
    /** 掉落物生成位置相对方块中心的偏移量 */
    private static final double SPAWN_OFFSET = 0.5D;
    /** 俯仰角向下挖掘的阈值 */
    private static final float PITCH_DOWN_THRESHOLD = 45.0F;
    /** 俯仰角向上挖掘的阈值 */
    private static final float PITCH_UP_THRESHOLD = -45.0F;
    /** 不可破坏方块的挖掘速度阈值 */
    private static final float UNBREAKABLE_DESTROY_SPEED = 0.0F;
    /** 完整圆周的角度值 */
    private static final float FULL_CIRCLE = 360.0F;
    /** 半圆的角度值 */
    private static final float HALF_CIRCLE = 180.0F;
    /** 朝向扇区的基础边界角度 */
    private static final float YAW_SECTOR = 45.0F;
    /** 朝向扇区的对角边界角度 */
    private static final float YAW_SECTOR_DIAGONAL = 135.0F;
    /** 坐标轴上不产生偏移的量 */
    private static final int NO_OFFSET = 0;

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();

        if (player.level().isClientSide()) return;

        // 创造模式不执行
        if (player.isCreative()) return;

        ItemStack tool = player.getMainHandItem();
        ResourceKey<Enchantment> excavatorEnchant = EnchantContext.key(EXCAVATOR_ID);

        int level = EnchantContext.level(tool, excavatorEnchant);
        if (level <= 0) return;

        int radius = Math.min(RADIUS_BASE + RADIUS_PER_LEVEL * (level - 1), RADIUS_MAX);

        // 只在主方块被破坏时触发（避免重复执行）
        if (!event.isCanceled()) {
            ServerLevel serverLevel = (ServerLevel) player.level();
            BlockPos centerPos = event.getPos();

            // 获取挖掘方向
            Direction facing = getFacingFromPlayer(player);

            // 获取挖掘区域内的所有位置
            List<BlockPos> areaPositions = getAreaPositions(centerPos, facing, radius);

            // 遍历挖掘区域
            for (BlockPos pos : areaPositions) {
                // 跳过中心方块（已经由原版处理）
                if (pos.equals(centerPos)) continue;

                BlockState targetState = serverLevel.getBlockState(pos);

                // 检查是否可以挖掘
                if (targetState.getDestroySpeed(serverLevel, pos) < UNBREAKABLE_DESTROY_SPEED) continue;
                if (!tool.isCorrectToolForDrops(targetState)) continue;

                // 获取掉落物
                var drops = Block.getDrops(targetState, serverLevel, pos, serverLevel.getBlockEntity(pos), player, tool);

                // 移除方块
                serverLevel.destroyBlock(pos, false, player);

                // 生成掉落物
                for (ItemStack drop : drops) {
                    var itemEntity = new ItemEntity(
                            serverLevel,
                            pos.getX() + SPAWN_OFFSET,
                            pos.getY() + SPAWN_OFFSET,
                            pos.getZ() + SPAWN_OFFSET,
                            drop
                    );
                    serverLevel.addFreshEntity(itemEntity);
                }

                // 消耗耐久
                tool.hurtAndBreak(DURABILITY_COST, player, EquipmentSlot.MAINHAND);
            }
        }
    }

    private static Direction getFacingFromPlayer(Player player) {
        float pitch = player.getXRot();

        // 垂直方向
        if (pitch > PITCH_DOWN_THRESHOLD) return Direction.DOWN;
        if (pitch < PITCH_UP_THRESHOLD) return Direction.UP;

        // 水平方向
        float yaw = player.getYRot() % FULL_CIRCLE;
        if (yaw > HALF_CIRCLE) yaw -= FULL_CIRCLE;
        if (yaw < -HALF_CIRCLE) yaw += FULL_CIRCLE;

        if (yaw > -YAW_SECTOR && yaw <= YAW_SECTOR) return Direction.SOUTH;
        if (yaw > YAW_SECTOR && yaw <= YAW_SECTOR_DIAGONAL) return Direction.WEST;
        if (yaw > YAW_SECTOR_DIAGONAL || yaw <= -YAW_SECTOR_DIAGONAL) return Direction.NORTH;
        return Direction.EAST;
    }

    private static List<BlockPos> getAreaPositions(BlockPos center, Direction facing, int radius) {
        List<BlockPos> positions = new ArrayList<>();

        Direction.Axis axis1, axis2;

        if (facing.getAxis() == Direction.Axis.Y) {
            axis1 = Direction.Axis.X;
            axis2 = Direction.Axis.Z;
        } else {
            axis1 = Direction.UP.getAxis();
            axis2 = facing.getClockWise().getAxis();
        }

        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                BlockPos offset = center;

                if (axis1 == Direction.Axis.X) offset = offset.offset(i, NO_OFFSET, NO_OFFSET);
                else if (axis1 == Direction.Axis.Y) offset = offset.offset(NO_OFFSET, i, NO_OFFSET);
                else if (axis1 == Direction.Axis.Z) offset = offset.offset(NO_OFFSET, NO_OFFSET, i);

                if (axis2 == Direction.Axis.X) offset = offset.offset(j, NO_OFFSET, NO_OFFSET);
                else if (axis2 == Direction.Axis.Y) offset = offset.offset(NO_OFFSET, j, NO_OFFSET);
                else if (axis2 == Direction.Axis.Z) offset = offset.offset(NO_OFFSET, NO_OFFSET, j);

                positions.add(offset);
            }
        }

        return positions;
    }
}
