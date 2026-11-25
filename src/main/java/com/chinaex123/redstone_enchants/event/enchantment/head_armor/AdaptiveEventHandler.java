package com.chinaex123.redstone_enchants.event.enchantment.head_armor;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantEffectTracker;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

/**
 * 矿工：在低于Y0时获得夜视效果
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
@SuppressWarnings("deprecation")
public class AdaptiveEventHandler {
    private static final ResourceLocation ADAPTIVE_ID = RedstoneEnchants.id("adaptive");

    /** 夜视持续时间 */
    private static final int NIGHT_VISION_DURATION = 20 * 12;

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;

        Holder.Reference<Enchantment> adaptiveEnchant = player.level()
                .registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(ADAPTIVE_ID)
                .orElse(null);

        if (adaptiveEnchant == null) return;

        // 检查头盔是否有矿工附魔
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        int enchantLevel = helmet.isEmpty() ? 0 : helmet.getEnchantments().getLevel(adaptiveEnchant);

        // 检查是否在Y0以下
        if (enchantLevel > 0 && player.getY() < 0) {
            EnchantEffectTracker.apply(player, MobEffects.NIGHT_VISION, NIGHT_VISION_DURATION, 0);
        } else {
            EnchantEffectTracker.removeIfTracked(player, MobEffects.NIGHT_VISION);
        }
    }
}
