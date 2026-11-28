package com.chinaex123.redstone_enchants.event.enchantment.all_tools;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 地质学：挖掘石头有概率掉落矿石
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class GeologyEventHandler {
    private static final ResourceLocation GEOLOGY_ID = RedstoneEnchants.id("geology");

    /** 1 级时的掉落概率 */
    private static final double DROP_CHANCE_BASE = 0.05D;
    /** 每级额外增加的掉落概率 */
    private static final double DROP_CHANCE_PER_LEVEL = 0.05D;
    /** 掉落概率上限 */
    private static final double DROP_CHANCE_MAX = 1.0D;
    /** 时运加成的最低倍率 */
    private static final int FORTUNE_BONUS_MIN = 1;
    /** 掉落物生成位置相对方块中心的偏移量 */
    private static final double SPAWN_OFFSET = 0.5D;

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();

        if (player.level().isClientSide()) return;

        ItemStack tool = player.getMainHandItem();
        ResourceKey<Enchantment> geologyEnchant = EnchantContext.key(GEOLOGY_ID);

        int level = EnchantContext.level(player, geologyEnchant);
        if (level <= 0) return;

        // 只处理石头类方块
        Block block = event.getState().getBlock();
        if (block != Blocks.STONE && block != Blocks.ANDESITE && block != Blocks.DIORITE && block != Blocks.GRANITE) return;

        // 获取所有矿石
        List<ItemStack> ores = getAllOres();
        if (ores.isEmpty()) return;

        // 计算掉落概率：1 级 5%，每级再增加 5%
        double dropChance = Math.min(DROP_CHANCE_MAX, DROP_CHANCE_BASE + DROP_CHANCE_PER_LEVEL * (level - 1));

        if (RedstoneEnchants.RANDOM.nextFloat() < dropChance) {
            ItemStack oreDrop = ores.get(RedstoneEnchants.RANDOM.nextInt(ores.size()));

            // 应用时运效果
            ResourceKey<Enchantment> fortuneEnchant = EnchantContext.key(Enchantments.FORTUNE);

            int fortuneLevel = 0;
            if (fortuneEnchant != null) {
                int fortLvl = EnchantContext.level(tool, fortuneEnchant);
                fortuneLevel = fortLvl;
            }

            // 时运增加掉落数量
            if (fortuneLevel > 0) {
                int bonus = RedstoneEnchants.RANDOM.nextInt(fortuneLevel + 2) - 1;
                if (bonus < FORTUNE_BONUS_MIN) bonus = FORTUNE_BONUS_MIN;
                oreDrop.setCount(oreDrop.getCount() * bonus);
            }

            ServerLevel serverLevel = (ServerLevel) player.level();
            ItemEntity itemEntity = new ItemEntity(
                    serverLevel,
                    event.getPos().getX() + SPAWN_OFFSET,
                    event.getPos().getY() + SPAWN_OFFSET,
                    event.getPos().getZ() + SPAWN_OFFSET,
                    oreDrop
            );
            serverLevel.addFreshEntity(itemEntity);
        }
    }

    private static List<ItemStack> getAllOres() {
        List<ItemStack> ores = new ArrayList<>();

        for (ResourceLocation blockId : BuiltInRegistries.BLOCK.keySet()) {
            if (BuiltInRegistries.BLOCK.get(blockId).defaultBlockState().is(Tags.Blocks.ORES)) {
                ItemStack drop = new ItemStack(BuiltInRegistries.BLOCK.get(blockId));
                if (!drop.isEmpty()) {
                    ores.add(drop);
                }
            }
        }

        return ores;
    }
}
