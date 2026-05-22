package com.chinaex123.redstone_enchants.event.all_tools;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 挖掘机：可以挖掘3x3/5x5/7x7区域的方块
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class ExcavatorEventHandler {
    private static final Identifier EXCAVATOR_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "excavator");

    @SubscribeEvent
    public static void onBlockBreak(BreakBlockEvent event) {
        Player player = event.getPlayer();

        if (player.level().isClientSide()) return;

        // 创造模式不执行
        if (player.isCreative()) return;

        ItemStack tool = player.getMainHandItem();
        Holder.Reference<Enchantment> excavatorEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(EXCAVATOR_ID)
                .orElse(null);

        if (excavatorEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = tool.getEnchantments().getLevel(excavatorEnchant);
        if (level <= 0) return;

        // 只在主方块被破坏时触发（避免重复执行）
        if (!event.isCanceled()) {
            ServerLevel serverLevel = (ServerLevel) player.level();
            BlockPos centerPos = event.getPos();

            // 计算挖掘半径：1级=1(3x3), 2级=2(5x5), 3级=3(7x7)
            int radius = level;

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
                if (targetState.getDestroySpeed(serverLevel, pos) < 0) continue;
                if (!tool.isCorrectToolForDrops(targetState)) continue;

                // 获取掉落物
                var drops = net.minecraft.world.level.block.Block.getDrops(
                        targetState, serverLevel, pos, serverLevel.getBlockEntity(pos), player, tool);

                // 移除方块
                serverLevel.destroyBlock(pos, false, player);

                // 生成掉落物
                for (ItemStack drop : drops) {
                    var itemEntity = new net.minecraft.world.entity.item.ItemEntity(
                            serverLevel,
                            pos.getX() + 0.5,
                            pos.getY() + 0.5,
                            pos.getZ() + 0.5,
                            drop
                    );
                    serverLevel.addFreshEntity(itemEntity);
                }

                // 消耗耐久
                tool.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            }
        }
    }

    private static Direction getFacingFromPlayer(Player player) {
        float pitch = player.getXRot();

        // 垂直方向
        if (pitch > 45) return Direction.DOWN;
        if (pitch < -45) return Direction.UP;

        // 水平方向
        float yaw = player.getYRot() % 360;
        if (yaw > 180) yaw -= 360;
        if (yaw < -180) yaw += 360;

        if (yaw > -45 && yaw <= 45) return Direction.SOUTH;
        if (yaw > 45 && yaw <= 135) return Direction.WEST;
        if (yaw > 135 || yaw <= -135) return Direction.NORTH;
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

                if (axis1 == Direction.Axis.X) offset = offset.offset(i, 0, 0);
                else if (axis1 == Direction.Axis.Y) offset = offset.offset(0, i, 0);
                else if (axis1 == Direction.Axis.Z) offset = offset.offset(0, 0, i);

                if (axis2 == Direction.Axis.X) offset = offset.offset(j, 0, 0);
                else if (axis2 == Direction.Axis.Y) offset = offset.offset(0, j, 0);
                else if (axis2 == Direction.Axis.Z) offset = offset.offset(0, 0, j);

                positions.add(offset);
            }
        }

        return positions;
    }
}
