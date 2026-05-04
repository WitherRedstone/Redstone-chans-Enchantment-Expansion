package com.chinaex123.redstone_enchants.event.unbreaking;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
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
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class SacrificeEventHandler {
    private static final Identifier SACRIFICE_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "sacrifice");

    @SubscribeEvent
    @SuppressWarnings("deprecation")
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        LivingEntity entity = event.getEntity();

        // 检查所有装备槽位
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = entity.getItemBySlot(slot);
            if (stack.isEmpty()) continue;

            Holder.Reference<Enchantment> sacrificeEnchant = entity.level()
                    .registryAccess()
                    .lookupOrThrow(Registries.ENCHANTMENT)
                    .get(SACRIFICE_ID)
                    .orElse(null);

            if (sacrificeEnchant == null) continue;

            int level = stack.getEnchantments().getLevel(sacrificeEnchant);
            if (level <= 0) continue;

            // 计算修复量：基础-1.0，每级额外-0.5
            int repairAmount = (int) Math.floor(1.0 + (level - 1) * 0.5);

            // 修复物品（减少耐久值）
            int currentDamage = stack.getDamageValue();
            int newDamage = Math.max(0, currentDamage - repairAmount);
            stack.setDamageValue(newDamage);
        }
    }
}
