package com.chinaex123.redstone_enchants.event.all_tools;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 地质学：挖掘石头有概率掉落矿石
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class GeologyEventHandler {
    private static final Random RANDOM = new Random();
    private static final Identifier GEOLOGY_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "geology");

    // c:ores 标签
    private static final TagKey<Block> ORES_TAG = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("c", "ores"));

    @SubscribeEvent
    public static void onBlockBreak(BreakBlockEvent event) {
        Player player = event.getPlayer();

        if (player.level().isClientSide()) return;

        ItemStack tool = player.getMainHandItem();
        Holder.Reference<Enchantment> geologyEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(GEOLOGY_ID)
                .orElse(null);

        if (geologyEnchant == null) return;

        int level = EnchantmentHelper.getEnchantmentLevel(geologyEnchant, player);
        if (level <= 0) return;

        // 只处理石头类方块
        Block block = event.getState().getBlock();
        if (block != Blocks.STONE && block != Blocks.ANDESITE && block != Blocks.DIORITE && block != Blocks.GRANITE) return;

        // 获取所有矿石
        List<ItemStack> ores = getAllOres();
        if (ores.isEmpty()) return;

        // 计算掉落概率：每级5%
        float dropChance = level * 0.05f;

        if (RANDOM.nextFloat() < dropChance) {
            ItemStack oreDrop = ores.get(RANDOM.nextInt(ores.size()));

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
                oreDrop.setCount(oreDrop.getCount() * bonus);
            }

            ServerLevel serverLevel = (ServerLevel) player.level();
            ItemEntity itemEntity = new ItemEntity(
                    serverLevel,
                    event.getPos().getX() + 0.5,
                    event.getPos().getY() + 0.5,
                    event.getPos().getZ() + 0.5,
                    oreDrop
            );
            serverLevel.addFreshEntity(itemEntity);
        }
    }

    private static List<ItemStack> getAllOres() {
        List<ItemStack> ores = new ArrayList<>();

        BuiltInRegistries.BLOCK.listElements().forEach(blockHolder -> {
            Block block = blockHolder.value();
            if (block.defaultBlockState().is(ORES_TAG)) {
                ItemStack drop = new ItemStack(block);
                if (!drop.isEmpty()) {
                    ores.add(drop);
                }
            }
        });

        return ores;
    }
}
