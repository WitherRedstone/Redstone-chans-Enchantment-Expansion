package com.chinaex123.redstone_enchants.event.enchantment.all_tools;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.Random;

/**
 * 点石成金：挖掘石头类方块有概率掉落金粒
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class GoldfingerEventHandler {
    private static final Random RANDOM = new Random();
    private static final ResourceLocation GOLDFINGER_ID = RedstoneEnchants.id("goldfinger");

    /** 1 级时的掉落概率 */
    private static final double DROP_CHANCE_BASE = 0.025D;
    /** 每级额外增加的掉落概率 */
    private static final double DROP_CHANCE_PER_LEVEL = 0.025D;
    /** 掉落概率上限 */
    private static final double DROP_CHANCE_MAX = 1.0D;
    /** 金粒掉落数量下限 */
    private static final int NUGGET_COUNT_MIN = 1;
    /** 金粒掉落数量随机上限（不含） */
    private static final int NUGGET_COUNT_BOUND = 3;
    /** 时运加成的最低倍率 */
    private static final int FORTUNE_BONUS_MIN = 1;
    /** 掉落物生成位置相对方块中心的偏移量 */
    private static final double SPAWN_OFFSET = 0.5D;

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();

        if (player.level().isClientSide()) return;

        ItemStack tool = player.getMainHandItem();
        ResourceKey<Enchantment> goldfingerEnchant = EnchantContext.key(GOLDFINGER_ID);

        int level = EnchantContext.level(player, goldfingerEnchant);
        if (level <= 0) return;

        // 检查是否是石头类方块
        Block block = event.getState().getBlock();
        if (!block.defaultBlockState().is(Tags.Blocks.STONES)) return;

        // 计算掉落概率：1 级 2.5%，每级再增加 2.5%
        double dropChance = Math.min(DROP_CHANCE_MAX, DROP_CHANCE_BASE + DROP_CHANCE_PER_LEVEL * (level - 1));

        if (RANDOM.nextFloat() < dropChance) {
            // 掉落1-3个金粒
            int goldNuggetCount = RANDOM.nextInt(NUGGET_COUNT_BOUND) + NUGGET_COUNT_MIN;

            // 应用时运效果
            ResourceKey<Enchantment> fortuneEnchant = EnchantContext.key(Enchantments.FORTUNE);

            int fortuneLevel = 0;
            if (fortuneEnchant != null) {
                int fortLvl = EnchantContext.level(tool, fortuneEnchant);
                fortuneLevel = fortLvl;
            }

            // 时运增加掉落数量
            if (fortuneLevel > 0) {
                int bonus = RANDOM.nextInt(fortuneLevel + 2) - 1;
                if (bonus < FORTUNE_BONUS_MIN) bonus = FORTUNE_BONUS_MIN;
                goldNuggetCount *= bonus;
            }

            ItemStack goldNuggets = new ItemStack(Items.GOLD_NUGGET, goldNuggetCount);

            ServerLevel serverLevel = (ServerLevel) player.level();
            ItemEntity itemEntity = new ItemEntity(
                    serverLevel,
                    event.getPos().getX() + SPAWN_OFFSET,
                    event.getPos().getY() + SPAWN_OFFSET,
                    event.getPos().getZ() + SPAWN_OFFSET,
                    goldNuggets
            );
            serverLevel.addFreshEntity(itemEntity);
        }
    }
}
