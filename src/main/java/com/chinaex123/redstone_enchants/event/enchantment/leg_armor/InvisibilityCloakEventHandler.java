package com.chinaex123.redstone_enchants.event.enchantment.leg_armor;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
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
 * 隐身斗篷：蹲伏时获得隐身效果
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class InvisibilityCloakEventHandler {
    private static final ResourceLocation INVISIBILITY_CLOAK_ID = RedstoneEnchants.id("invisibility_cloak");

    /** 基础时间 */
    private static final int BASE_INVISIBILITY_DURATION = 80;

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ResourceKey<Enchantment> invisibilityCloakEnchant = EnchantContext.key(INVISIBILITY_CLOAK_ID);

        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        int level = leggings.isEmpty() ? 0 : EnchantContext.level(leggings, invisibilityCloakEnchant);

        if (level > 0 && player.isShiftKeyDown()) {
            EnchantEffectTracker.apply(player, MobEffects.INVISIBILITY, BASE_INVISIBILITY_DURATION, 0);
        } else {
            EnchantEffectTracker.removeIfTracked(player, MobEffects.INVISIBILITY);
        }
    }
}
