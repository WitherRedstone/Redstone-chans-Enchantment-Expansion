package com.chinaex123.redstone_enchants.event.enchantment.unbreaking;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;

/**
 * 坚不可摧：物品不再消耗耐久
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class IndestructibleEventHandler {
    private static final ResourceLocation INDESTRUCTIBLE_ID = RedstoneEnchants.id("indestructible");

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        LivingEntity entity = event.getEntity();

        EquipmentSlot[] slots = {
                EquipmentSlot.MAINHAND,
                EquipmentSlot.OFFHAND,
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET,
                EquipmentSlot.BODY
        };

        for (EquipmentSlot slot : slots) {
            ItemStack stack = entity.getItemBySlot(slot);
            if (stack.isEmpty()) continue;

            ResourceKey<Enchantment> indestructibleEnchant = EnchantContext.key(INDESTRUCTIBLE_ID);

            int enchantLevel = EnchantContext.level(stack, indestructibleEnchant);

            if (enchantLevel > 0) {
                // 添加不可摧毁组件
                stack.set(DataComponents.UNBREAKABLE, new Unbreakable(true));
            } else {
                // 如果没有附魔，移除不可摧毁组件
                stack.remove(DataComponents.UNBREAKABLE);
            }
        }
    }
}
