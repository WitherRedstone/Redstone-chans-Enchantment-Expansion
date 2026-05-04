package com.chinaex123.redstone_enchants.event.armor_wolf;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

/**
 * 尸体回收：狼击杀生物后回复最大生命值
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class CarrionEaterEventHandler {
    private static final Identifier CARRION_EATER_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "carrion_eater");

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getSource().getDirectEntity() instanceof Wolf wolf)) return;

        // 检查狼是否有主人
        if (!wolf.isTame()) return;

        // 检查狼铠是否有尸体回收附魔
        ItemStack armor = wolf.getItemBySlot(EquipmentSlot.BODY);
        if (armor.isEmpty()) return;

        Holder.Reference<Enchantment> carrionEaterEnchant = wolf.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(CARRION_EATER_ID)
                .orElse(null);

        if (carrionEaterEnchant == null) return;

        @SuppressWarnings("deprecation")
        int enchantLevel = armor.getEnchantments().getLevel(carrionEaterEnchant);
        if (enchantLevel <= 0) return;

        // 计算治疗量：每级25%最大生命值
        float maxHealth = wolf.getMaxHealth();
        float healAmount = maxHealth * (enchantLevel * 0.25f);

        // 应用治疗
        wolf.heal(healAmount);
    }
}
