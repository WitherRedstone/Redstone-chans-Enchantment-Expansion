package com.chinaex123.redstone_enchants.event.all_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 伏特：雷雨天射出的箭矢伤害增加
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class VoltEventHandler {
    private static final Identifier VOLT_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "volt");

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)) return;

        Entity shooter = arrow.getOwner();
        if (!(shooter instanceof Player player)) return;

        // 检查是否是雷雨天
        if (!player.level().isThundering()) return;

        ItemStack bow = player.getMainHandItem();
        if (bow.isEmpty()) bow = player.getOffhandItem();
        if (bow.isEmpty()) return;

        Holder.Reference<Enchantment> voltEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(VOLT_ID)
                .orElse(null);

        if (voltEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = bow.getEnchantments().getLevel(voltEnchant);
        if (level <= 0) return;

        // 每级增加25%伤害
        float bonus = level * 0.25F;
        float newDamage = event.getOriginalDamage() * (1 + bonus);
        event.setNewDamage(newDamage);
    }
}
