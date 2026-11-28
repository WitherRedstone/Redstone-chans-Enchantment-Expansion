package com.chinaex123.redstone_enchants.event.enchantment.all_tools;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
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

import java.util.*;

/**
 * 连锁砍树：砍伐树木时，同时破坏其上方所有同种原木
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class TimberEventHandler {
    private static final ResourceLocation TIMBER_ID = RedstoneEnchants.id("timber");

    /** 最大破坏方块数量 */
    private static final int MAX_BLOCKS = 512;
    /** 每破坏一个方块消耗的耐久 */
    private static final int DURABILITY_COST = 1;
    /** 掉落物生成位置相对方块中心的偏移量 */
    private static final double SPAWN_OFFSET = 0.5D;

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();

        if (player.level().isClientSide()) return;

        ItemStack tool = player.getMainHandItem();
        ResourceKey<Enchantment> timberEnchant = EnchantContext.key(TIMBER_ID);

        int level = EnchantContext.level(tool, timberEnchant);
        if (level <= 0) return;

        ServerLevel serverLevel = (ServerLevel) player.level();
        BlockPos startPos = event.getPos();
        BlockState startState = event.getState();

        // 只处理原木
        if (!startState.is(BlockTags.LOGS)) return;

        // BFS查找相连的所有原木
        List<BlockPos> connectedLogs = findConnectedLogs(serverLevel, startPos, startState);

        // 破坏所有相连的原木（除了起始方块）
        for (BlockPos pos : connectedLogs) {
            BlockState state = serverLevel.getBlockState(pos);

            // 获取掉落物
            var drops = Block.getDrops(state, serverLevel, pos, serverLevel.getBlockEntity(pos), player, tool);

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

    private static List<BlockPos> findConnectedLogs(ServerLevel level, BlockPos start, BlockState startState) {
        List<BlockPos> result = new ArrayList<>();
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty() && result.size() < MAX_BLOCKS) {
            BlockPos current = queue.poll();

            // 添加到结果（除了起始方块）
            if (!current.equals(start)) {
                result.add(current);
            }

            // 检查6个方向的邻居
            for (Direction direction : Direction.values()) {
                BlockPos neighbor = current.relative(direction);

                if (visited.contains(neighbor)) continue;

                BlockState state = level.getBlockState(neighbor);

                // 检查是否是同种原木
                if (state.is(startState.getBlock())) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }

        return result;
    }
}
