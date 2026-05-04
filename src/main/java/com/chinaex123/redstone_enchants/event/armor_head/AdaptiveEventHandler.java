package com.chinaex123.redstone_enchants.event.armor_head;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
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
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class AdaptiveEventHandler {
    private static final Identifier ADAPTIVE_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "adaptive");

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;

        // 检查头盔是否有矿工附魔
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.isEmpty()) return;

        Holder.Reference<Enchantment> adaptiveEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(ADAPTIVE_ID)
                .orElse(null);

        if (adaptiveEnchant == null) return;

        @SuppressWarnings("deprecation")
        int enchantLevel = helmet.getEnchantments().getLevel(adaptiveEnchant);
        if (enchantLevel <= 0) return;

        // 检查是否在Y0以下
        if (player.getY() < 0) {
            // 给予夜视效果
            player.addEffect(new MobEffectInstance(
                    MobEffects.NIGHT_VISION, 20 * 12, 0, false, false
            ));
        } else {
            // 不在Y0以下时，移除夜视效果
            if (player.hasEffect(MobEffects.NIGHT_VISION)) {
                player.removeEffect(MobEffects.NIGHT_VISION);
            }
        }
    }
}
