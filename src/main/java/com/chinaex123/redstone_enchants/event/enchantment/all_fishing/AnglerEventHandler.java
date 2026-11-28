package com.chinaex123.redstone_enchants.event.enchantment.all_fishing;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 渔夫：钓鱼后收获鱼的数量有概率翻倍
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class AnglerEventHandler {
    private static final ResourceLocation ANGLER_ID = RedstoneEnchants.id("angler");

    /** 1 级时的翻倍概率 */
    private static final double CHANCE_BASE = 0.1D;
    /** 每级额外增加的概率 */
    private static final double CHANCE_PER_LEVEL = 0.1D;

    @SubscribeEvent
    public static void onItemFished(ItemFishedEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        Player player = event.getEntity();
        ItemStack rod = player.getMainHandItem();

        if (rod.isEmpty() || !rod.is(Items.FISHING_ROD)) return;

        ResourceKey<Enchantment> anglerEnchant = EnchantContext.key(ANGLER_ID);

        int level = EnchantContext.level(rod, anglerEnchant);
        if (level <= 0) return;

        // 翻倍概率随附魔等级提高
        double chance = Math.min(1.0D, CHANCE_BASE + CHANCE_PER_LEVEL * (level - 1));

        // 延迟一tick后复制掉落物
        Objects.requireNonNull(player.level().getServer()).execute(() -> {
            List<ItemStack> originalDrops = new ArrayList<>(event.getDrops());

            TagKey<Item> tideFishTag = TagKey.create(Registries.ITEM, ResourceLocation.parse("tide:fish"));

            // 概率翻倍
            if (RedstoneEnchants.RANDOM.nextFloat() < chance) {
                for (ItemStack drop : originalDrops) {
                    // 只复制鱼类物品
                    if (drop.is(ItemTags.FISHES) || drop.is(tideFishTag)) {
                        ItemEntity itemEntity = new ItemEntity(
                                player.level(),
                                player.getX(),
                                player.getY(),
                                player.getZ(),
                                drop.copy()
                        );
                        player.level().addFreshEntity(itemEntity);
                    }
                }
            }
        });
    }
}
