package com.chinaex123.redstone_enchants.event.enchantment.all_tools;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.List;

/**
 * 精通采集：挖掘矿石时有概率触发双倍掉落
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class MasterGathererEventHandler {
    private static final ResourceLocation MASTER_GATHERER_ID = RedstoneEnchants.id("master_gatherer");

    /** 1 级时的触发概率 */
    private static final double TRIGGER_CHANCE_BASE = 0.20D;
    /** 每级额外增加的触发概率 */
    private static final double TRIGGER_CHANCE_PER_LEVEL = 0.20D;
    /** 触发概率上限 */
    private static final double TRIGGER_CHANCE_MAX = 1.0D;
    /** 掉落物生成位置相对方块中心的偏移量 */
    private static final double SPAWN_OFFSET = 0.5D;
    /** 额外掉落物的拾取延迟（tick），0 表示立即可以拾取 */
    private static final int PICKUP_DELAY_TICKS = 0;

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();

        if (player.level().isClientSide()) return;

        // 检查主手工具是否有精通采集附魔
        ItemStack tool = player.getMainHandItem();
        if (tool.isEmpty()) return;

        ResourceKey<Enchantment> masterGathererEnchant = EnchantContext.key(MASTER_GATHERER_ID);

        int enchantLevel = EnchantContext.level(tool, masterGathererEnchant);
        if (enchantLevel <= 0) return;

        // 检查是否是矿石
        if (!event.getState().is(Tags.Blocks.ORES)) return;

        // 触发概率：1 级 20%，每级再增加 20%
        double triggerChance = Math.min(TRIGGER_CHANCE_MAX,
                TRIGGER_CHANCE_BASE + TRIGGER_CHANCE_PER_LEVEL * (enchantLevel - 1));

        // 随机判定是否触发双倍掉落
        if (RedstoneEnchants.RANDOM.nextFloat() < triggerChance) {
            ServerLevel serverLevel = (ServerLevel) player.level();

            // 获取原方块的掉落物（已经包含时运效果）
            List<ItemStack> drops = Block.getDrops(event.getState(), serverLevel, event.getPos(), null, player, tool);

            // 为每个掉落物额外生成一份（同样享受时运）
            for (ItemStack drop : drops) {
                if (!drop.isEmpty()) {
                    ItemStack extraDrop = drop.copy();
                    ItemEntity itemEntity = new ItemEntity(
                            serverLevel,
                            event.getPos().getX() + SPAWN_OFFSET,
                            event.getPos().getY() + SPAWN_OFFSET,
                            event.getPos().getZ() + SPAWN_OFFSET,
                            extraDrop
                    );
                    itemEntity.setPickUpDelay(PICKUP_DELAY_TICKS);
                    serverLevel.addFreshEntity(itemEntity);
                }
            }
        }
    }
}
