package com.chinaex123.redstone_enchants.event.all_fishing;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
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
import java.util.Random;

/**
 * 潮汐感知：雷雨天时，钓鱼等待时间大幅缩短且鱼的几率大幅提高
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class TideSenseEventHandler {
    private static final Identifier TIDE_SENSE_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "tide_sense");
    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onItemFished(ItemFishedEvent event) {
        Player player = event.getEntity();

        if (!player.level().isThundering()) return;

        ItemStack rod = player.getMainHandItem();
        if (rod.isEmpty() || !rod.is(Items.FISHING_ROD)) return;

        Holder.Reference<Enchantment> tideSenseEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(TIDE_SENSE_ID)
                .orElse(null);

        if (tideSenseEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = rod.getEnchantments().getLevel(tideSenseEnchant);
        if (level <= 0) return;

        // 雷雨天时大幅提高鱼的几率（替换垃圾和宝藏）
        float fishChance = 0.7F + (level * 0.1F); // 70%-90%概率是鱼

        if (RANDOM.nextFloat() < fishChance) {
            // 强制设为鱼类掉落
            event.getDrops().clear();

            // 从鱼类标签中随机选择
            var registry = player.level().registryAccess().lookupOrThrow(Registries.ITEM);
            var fishTag = ItemTags.FISHES;
            var tideFishTag = TagKey.create(Registries.ITEM, Identifier.parse("tide:fish"));

            List<Item> fishItems = new ArrayList<>();
            for (var holder : registry.getTagOrEmpty(fishTag)) {
                fishItems.add(holder.value());
            }
            for (var holder : registry.getTagOrEmpty(tideFishTag)) {
                fishItems.add(holder.value());
            }

            if (!fishItems.isEmpty()) {
                Item randomFish = fishItems.get(RANDOM.nextInt(fishItems.size()));
                event.getDrops().add(new ItemStack(randomFish));
            }
        }
    }
}
