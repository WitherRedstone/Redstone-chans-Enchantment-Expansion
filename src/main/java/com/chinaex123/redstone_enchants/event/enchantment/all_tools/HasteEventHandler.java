package com.chinaex123.redstone_enchants.event.enchantment.all_tools;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantEffectTracker;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * 急速：手持时获得急迫效果
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class HasteEventHandler {
    private static final ResourceLocation HASTE_ID = RedstoneEnchants.id("haste");

    /** 持续时间 */
    private static final int HASTE_DURATION = 120;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        ItemStack mainHand = player.getMainHandItem();

        ResourceKey<Enchantment> hasteEnchant = EnchantContext.key(HASTE_ID);

        int level = EnchantContext.level(mainHand, hasteEnchant);

        if (level > 0) {
            int amplifier = level - 1;

            if (EnchantEffectTracker.isTracked(player, MobEffects.DIG_SPEED)) {
                EnchantEffectTracker.apply(player, MobEffects.DIG_SPEED, HASTE_DURATION, amplifier);
                return;
            }

            MobEffectInstance existing = player.getEffect(MobEffects.DIG_SPEED);
            if (existing != null && existing.getAmplifier() >= amplifier) {
                return;
            }

            EnchantEffectTracker.apply(player, MobEffects.DIG_SPEED, HASTE_DURATION, amplifier);
        } else {
            EnchantEffectTracker.removeIfTracked(player, MobEffects.DIG_SPEED);
        }
    }
}
