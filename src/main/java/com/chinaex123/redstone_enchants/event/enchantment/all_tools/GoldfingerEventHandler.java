package com.chinaex123.redstone_enchants.event.enchantment.all_tools;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
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
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.Random;

/**
 * 点石成金：挖掘石头类方块有概率掉落金粒
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
@SuppressWarnings("deprecation")
public class GoldfingerEventHandler {
    private static final Random RANDOM = new Random();
    private static final ResourceLocation GOLDFINGER_ID = RedstoneEnchants.id("goldfinger");

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();

        if (player.level().isClientSide()) return;

        ItemStack tool = player.getMainHandItem();
        Holder.Reference<Enchantment> goldfingerEnchant = player.level()
                .registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(GOLDFINGER_ID)
                .orElse(null);

        if (goldfingerEnchant == null) return;

        int level = EnchantmentHelper.getEnchantmentLevel(goldfingerEnchant, player);
        if (level <= 0) return;

        // 检查是否是石头类方块
        Block block = event.getState().getBlock();
        if (!block.defaultBlockState().is(Tags.Blocks.STONES)) return;

        // 计算掉落概率：每级2.5%
        float dropChance = level * 0.025f;

        if (RANDOM.nextFloat() < dropChance) {
            // 掉落1-3个金粒
            int goldNuggetCount = RANDOM.nextInt(3) + 1;

            // 应用时运效果
            Holder.Reference<Enchantment> fortuneEnchant = player.level()
                    .registryAccess()
                    .registryOrThrow(Registries.ENCHANTMENT)
                    .getHolder(Enchantments.FORTUNE)
                    .orElse(null);

            int fortuneLevel = 0;
            if (fortuneEnchant != null) {
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
