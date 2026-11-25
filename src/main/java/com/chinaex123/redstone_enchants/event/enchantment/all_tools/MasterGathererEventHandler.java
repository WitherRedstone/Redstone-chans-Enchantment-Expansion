package com.chinaex123.redstone_enchants.event.enchantment.all_tools;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
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

        // 根据附魔等级计算触发概率：每级20%
        float triggerChance = Math.min(enchantLevel * 0.20f, 1.0f);

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
                            event.getPos().getX() + 0.5,
                            event.getPos().getY() + 0.5,
                            event.getPos().getZ() + 0.5,
                            extraDrop
                    );
                    itemEntity.setPickUpDelay(0);
                    serverLevel.addFreshEntity(itemEntity);
                }
            }
        }
    }
}
