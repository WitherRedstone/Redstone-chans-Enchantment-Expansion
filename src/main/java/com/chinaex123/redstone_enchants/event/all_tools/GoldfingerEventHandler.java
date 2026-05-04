package com.chinaex123.redstone_enchants.event.all_tools;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;

import java.util.Random;

/**
 * 点石成金：挖掘石头类方块有概率掉落金粒
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class GoldfingerEventHandler {
    private static final Random RANDOM = new Random();
    private static final Identifier GOLDFINGER_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "goldfinger");

    // c:stones 标签
    private static final TagKey<Block> STONE_TAG = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("c", "stones"));

    @SubscribeEvent
    public static void onBlockBreak(BreakBlockEvent event) {
        Player player = event.getPlayer();

        ItemStack tool = player.getMainHandItem();
        Holder.Reference<Enchantment> goldfingerEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(GOLDFINGER_ID)
                .orElse(null);

        if (goldfingerEnchant == null) return;

        int level = EnchantmentHelper.getEnchantmentLevel(goldfingerEnchant, player);
        if (level <= 0) return;

        // 检查是否是石头类方块
        Block block = event.getState().getBlock();
        if (!block.defaultBlockState().is(STONE_TAG)) return;

        // 计算掉落概率：每级2.5%
        float dropChance = level * 0.025f;

        if (RANDOM.nextFloat() < dropChance) {
            // 掉落1-3个金粒
            int goldNuggetCount = RANDOM.nextInt(3) + 1;

            // 应用时运效果
            Holder.Reference<Enchantment> fortuneEnchant = player.level()
                    .registryAccess()
                    .lookupOrThrow(Registries.ENCHANTMENT)
                    .get(Enchantments.FORTUNE)
                    .orElse(null);

            int fortuneLevel = 0;
            if (fortuneEnchant != null) {
                @SuppressWarnings("deprecation")
                int fortLvl = tool.getEnchantments().getLevel(fortuneEnchant);
                fortuneLevel = fortLvl;
            }

            // 时运增加掉落数量
            if (fortuneLevel > 0) {
                int bonus = RANDOM.nextInt(fortuneLevel + 2) - 1;
                if (bonus < 1) bonus = 1;
                goldNuggetCount *= bonus;
            }

            ItemStack goldNuggets = new ItemStack(Items.GOLD_NUGGET, goldNuggetCount);

            ServerLevel serverLevel = (ServerLevel) player.level();
            ItemEntity itemEntity = new ItemEntity(
                    serverLevel,
                    event.getPos().getX() + 0.5,
                    event.getPos().getY() + 0.5,
                    event.getPos().getZ() + 0.5,
                    goldNuggets
            );
            serverLevel.addFreshEntity(itemEntity);
        }
    }
}
