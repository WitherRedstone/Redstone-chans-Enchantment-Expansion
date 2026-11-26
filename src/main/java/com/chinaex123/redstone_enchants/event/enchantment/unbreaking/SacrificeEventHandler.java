package com.chinaex123.redstone_enchants.event.enchantment.unbreaking;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 牺牲：受到攻击时自动修复手持物品
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class SacrificeEventHandler {
    private static final ResourceLocation SACRIFICE_ID = RedstoneEnchants.id("sacrifice");

    /** 1 级时每次修复的耐久。 */
    private static final double REPAIR_BASE = 1.0D;
    /** 每级额外增加的修复耐久。 */
    private static final double REPAIR_PER_LEVEL = 0.5D;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        LivingEntity entity = event.getEntity();

        // 检查所有装备槽位
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = entity.getItemBySlot(slot);
            if (stack.isEmpty()) continue;

            ResourceKey<Enchantment> sacrificeEnchant = EnchantContext.key(SACRIFICE_ID);

            int level = EnchantContext.level(stack, sacrificeEnchant);
            if (level <= 0) continue;

            // 计算修复量：基础-1.0，每级额外-0.5
            int repairAmount = (int) Math.floor(REPAIR_BASE + REPAIR_PER_LEVEL * (level - 1));

            // 修复物品（减少耐久值）
            int currentDamage = stack.getDamageValue();
            int newDamage = Math.max(0, currentDamage - repairAmount);
            stack.setDamageValue(newDamage);
        }
    }
}
