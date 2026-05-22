package com.chinaex123.redstone_enchants.event.all_tools;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;

import java.util.List;
import java.util.Random;

/**
 * 精通采集：挖掘矿石时有概率触发双倍掉落
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class MasterGathererEventHandler {
    private static final Identifier MASTER_GATHERER_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "master_gatherer");
    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onBlockBreak(BreakBlockEvent event) {
        Player player = event.getPlayer();

        if (player.level().isClientSide()) return;

        // 检查主手工具是否有精通采集附魔
        ItemStack tool = player.getMainHandItem();
        if (tool.isEmpty()) return;

        Holder.Reference<Enchantment> masterGathererEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(MASTER_GATHERER_ID)
                .orElse(null);

        if (masterGathererEnchant == null) return;

        @SuppressWarnings("deprecation")
        int enchantLevel = tool.getEnchantments().getLevel(masterGathererEnchant);
        if (enchantLevel <= 0) return;

        // 检查是否是矿石
        Block block = event.getState().getBlock();
        if (!isOre(Item.byBlock(block))) return;

        // 根据附魔等级计算触发概率：每级20%
        float triggerChance = Math.min(enchantLevel * 0.20f, 1.0f);

        // 随机判定是否触发双倍掉落
        if (RANDOM.nextFloat() < triggerChance) {
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

    private static boolean isOre(Item item) {
        // 检查方块是否属于矿石标签
        return item.builtInRegistryHolder().is(TagKey.create(
                Registries.ITEM, Identifier.fromNamespaceAndPath("c", "ores")
        ));
    }
}
