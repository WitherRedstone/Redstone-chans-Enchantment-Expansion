package com.chinaex123.redstone_enchants.event.all_fishing;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
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
import java.util.Random;

/**
 * 渔夫：钓鱼后收获鱼的数量有概率翻倍
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class AnglerEventHandler {
    private static final Identifier ANGLER_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "angler");
    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onItemFished(ItemFishedEvent event) {
        Player player = event.getEntity();
        ItemStack rod = player.getMainHandItem();

        if (rod.isEmpty() || !rod.is(Items.FISHING_ROD)) return;

        Holder.Reference<Enchantment> anglerEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(ANGLER_ID)
                .orElse(null);

        if (anglerEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = rod.getEnchantments().getLevel(anglerEnchant);
        if (level <= 0) return;

        // 每级+10%概率翻倍
        float chance = level * 0.1F;

        // 延迟一tick后复制掉落物
        Objects.requireNonNull(player.level().getServer()).execute(() -> {
            List<ItemStack> originalDrops = new ArrayList<>(event.getDrops());

            TagKey<Item> tideFishTag = TagKey.create(Registries.ITEM, Identifier.parse("tide:fish"));

            // 概率翻倍
            if (RANDOM.nextFloat() < chance) {
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
