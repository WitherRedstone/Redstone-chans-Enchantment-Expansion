package com.chinaex123.redstone_enchants.event.armor_leg;

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
 * 隐身斗篷：蹲伏时获得隐身效果
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class InvisibilityCloakEventHandler {
    private static final Identifier INVISIBILITY_CLOAK_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "invisibility_cloak");

    private static final int BASE_INVISIBILITY_DURATION = 80; // 基础时间

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        if (leggings.isEmpty()) return;

        Holder.Reference<Enchantment> invisibilityCloakEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(INVISIBILITY_CLOAK_ID)
                .orElse(null);

        if (invisibilityCloakEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = leggings.getEnchantments().getLevel(invisibilityCloakEnchant);

        if (level > 0 && player.isShiftKeyDown()) {
            if (!player.hasEffect(MobEffects.INVISIBILITY)) {
                player.addEffect(new MobEffectInstance(
                        MobEffects.INVISIBILITY,
                        BASE_INVISIBILITY_DURATION,
                        0,
                        false,
                        false
                ));
            }
        } else {
            if (player.hasEffect(MobEffects.INVISIBILITY)) {
                player.removeEffect(MobEffects.INVISIBILITY);
            }
        }
    }
}