package com.chinaex123.redstone_enchants.event.enchantment.all_tools;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 连锁效率：挖掘方块时，把周围同类型的方块一起连锁挖掉
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class ChainHasteEventHandler {

    private static final String ID = "chain_haste";

    /** 防止连锁掉落触发的后续事件再次进入本方法 */
    private static final ThreadLocal<Boolean> CHAINING = ThreadLocal.withInitial(() -> Boolean.FALSE);

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (CHAINING.get()) {
            return;
        }
        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack tool = player.getMainHandItem();
        int enchantLevel = EnchantContext.level(level, ID, tool);
        if (enchantLevel <= 0) {
            return;
        }

        BlockPos origin = event.getPos();
        BlockState originState = event.getState();
        if (originState.isAir()) {
            return;
        }
        // 基岩这类不可破坏的方块不参与连锁
        if (originState.getDestroySpeed(level, origin) < 0.0F) {
            return;
        }

        int radius = Math.min(1 + enchantLevel, 4);
        int maxBlocks = enchantLevel * 8;
        List<BlockPos> targets = collect(level, origin, originState, radius, maxBlocks);
        if (targets.isEmpty()) {
            return;
        }

        CHAINING.set(Boolean.TRUE);
        try {
            for (BlockPos pos : targets) {
                BlockState state = level.getBlockState(pos);
                if (!state.is(originState.getBlock())) {
                    continue;
                }
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (!player.isCreative()) {
                    Block.dropResources(state, level, pos, blockEntity, player, tool);
                }
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            }
        } finally {
            CHAINING.set(Boolean.FALSE);
        }
    }

    private static List<BlockPos> collect(ServerLevel level, BlockPos origin, BlockState originState,
                                          int radius, int maxBlocks) {
        List<BlockPos> result = new ArrayList<>();
        Set<BlockPos> visited = new HashSet<>();
        Deque<BlockPos> queue = new ArrayDeque<>();
        visited.add(origin);
        queue.add(origin);

        while (!queue.isEmpty() && result.size() < maxBlocks) {
            BlockPos current = queue.poll();
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        if (dx == 0 && dy == 0 && dz == 0) {
                            continue;
                        }
                        BlockPos next = current.offset(dx, dy, dz);
                        if (!visited.add(next)) {
                            continue;
                        }
                        if (Math.abs(next.getX() - origin.getX()) > radius
                                || Math.abs(next.getY() - origin.getY()) > radius
                                || Math.abs(next.getZ() - origin.getZ()) > radius) {
                            continue;
                        }
                        BlockState state = level.getBlockState(next);
                        if (!state.is(originState.getBlock())) {
                            continue;
                        }
                        if (state.getDestroySpeed(level, next) < 0.0F) {
                            continue;
                        }
                        result.add(next);
                        queue.add(next);
                        if (result.size() >= maxBlocks) {
                            return result;
                        }
                    }
                }
            }
        }
        return result;
    }
}
