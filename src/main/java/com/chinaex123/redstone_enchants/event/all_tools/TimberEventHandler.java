package com.chinaex123.redstone_enchants.event.all_tools;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
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
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;

import java.util.*;

/**
 * 连锁砍树：砍伐树木时，同时破坏其上方所有同种原木
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class TimberEventHandler {
    private static final Identifier TIMBER_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "timber");
    private static final int MAX_BLOCKS = 512;

    @SubscribeEvent
    public static void onBlockBreak(BreakBlockEvent event) {
        Player player = event.getPlayer();

        ItemStack tool = player.getMainHandItem();
        Holder.Reference<Enchantment> timberEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(TIMBER_ID)
                .orElse(null);

        if (timberEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = tool.getEnchantments().getLevel(timberEnchant);
        if (level <= 0) return;

        ServerLevel serverLevel = (ServerLevel) player.level();
        BlockPos startPos = event.getPos();
        BlockState startState = event.getState();

        // 只处理原木
        if (!startState.getBlock().asItem().toString().contains("log") &&
                !startState.getBlock().asItem().toString().contains("wood")) return;

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
