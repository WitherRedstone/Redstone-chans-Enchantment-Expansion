package com.chinaex123.redstone_enchants.event.all_tools;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 连锁急迫：连续挖掘同类型方块时，速度逐渐加快
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class ChainHasteEventHandler {
    private static final Identifier CHAIN_HASTE_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "chain_haste");
    private static final Identifier CHAIN_HASTE_MODIFIER_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "chain_haste_bonus");
    private static final Map<UUID, MiningStreak> MINING_STREAKS = new HashMap<>();

    private static class MiningStreak {
        BlockPos lastPos;
        BlockState lastBlock;
        int streak;
        long lastTime;

        MiningStreak(BlockPos pos, BlockState state) {
            this.lastPos = pos;
            this.lastBlock = state;
            this.streak = 1;
            this.lastTime = System.currentTimeMillis();
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BreakBlockEvent event) {
        Player player = event.getPlayer();

        ItemStack tool = player.getMainHandItem();
        Holder.Reference<Enchantment> chainHasteEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(CHAIN_HASTE_ID)
                .orElse(null);

        if (chainHasteEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = tool.getEnchantments().getLevel(chainHasteEnchant);
        if (level <= 0) return;

        UUID playerId = player.getUUID();
        BlockPos currentPos = event.getPos();
        BlockState currentState = event.getState();

        MiningStreak streak = MINING_STREAKS.get(playerId);

        // 检查是否是连续挖掘同类型方块
        if (streak != null &&
                streak.lastBlock.is(currentState.getBlock()) &&
                System.currentTimeMillis() - streak.lastTime < 2000) { // 2秒内

            streak.streak++;
            streak.lastPos = currentPos;
            streak.lastBlock = currentState;
            streak.lastTime = System.currentTimeMillis();

            // 每连挖1个方块增加 (level)% 挖掘速度，最多80%
            double bonusPerBlock = level * 0.01;
            double bonus = Math.min(streak.streak * bonusPerBlock, 0.8);
            applyMiningSpeed(player, bonus);
        } else {
            // 重置连击
            removeMiningSpeed(player);
            MINING_STREAKS.put(playerId, new MiningStreak(currentPos, currentState));
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        UUID playerId = player.getUUID();

        MiningStreak streak = MINING_STREAKS.get(playerId);
        if (streak != null && System.currentTimeMillis() - streak.lastTime >= 2000) {
            // 超过2秒未挖掘，移除属性并清理记录
            removeMiningSpeed(player);
            MINING_STREAKS.remove(playerId);
        }
    }

    private static void applyMiningSpeed(Player player, double bonus) {
        AttributeInstance attribute = player.getAttribute(Attributes.BLOCK_BREAK_SPEED);
        if (attribute == null) return;

        // 移除旧的修饰符
        attribute.removeModifier(CHAIN_HASTE_MODIFIER_ID);

        // 添加新的修饰符
        AttributeModifier modifier = new AttributeModifier(
                CHAIN_HASTE_MODIFIER_ID,
                bonus,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        attribute.addTransientModifier(modifier);
    }

    private static void removeMiningSpeed(Player player) {
        AttributeInstance attribute = player.getAttribute(Attributes.BLOCK_BREAK_SPEED);
        if (attribute == null) return;

        attribute.removeModifier(CHAIN_HASTE_MODIFIER_ID);
    }
}
