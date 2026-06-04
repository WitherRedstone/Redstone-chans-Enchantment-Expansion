package com.chinaex123.redstone_enchants.event.all_tools;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
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
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class HasteEventHandler {
    private static final Identifier HASTE_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "haste");
    private static final int HASTE_DURATION = 120; // 持续时间

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        ItemStack mainHand = player.getMainHandItem();

        Holder.Reference<Enchantment> hasteEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(HASTE_ID)
                .orElse(null);

        if (hasteEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = mainHand.getEnchantments().getLevel(hasteEnchant);

        if (level > 0) {
            if (player.hasEffect(MobEffects.HASTE)) {
                MobEffectInstance existingEffect = player.getEffect(MobEffects.HASTE);
                if (existingEffect != null && existingEffect.getAmplifier() >= level - 1) {
                    return;
                }
            }

            player.addEffect(new MobEffectInstance(
                    MobEffects.HASTE,
                    HASTE_DURATION,
                    level - 1,
                    false,
                    false
            ));
        } else {
            if (player.hasEffect(MobEffects.HASTE)) {
                player.removeEffect(MobEffects.HASTE);
            }
        }
    }
}