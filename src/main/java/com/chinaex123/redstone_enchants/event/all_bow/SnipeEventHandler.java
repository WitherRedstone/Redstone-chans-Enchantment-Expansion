package com.chinaex123.redstone_enchants.event.all_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 狙击：与目标距离每增加10格，造成的伤害提高
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class SnipeEventHandler {
    private static final Identifier SNIPE_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "snipe");

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)) return;

        Entity shooter = arrow.getOwner();
        if (!(shooter instanceof Player player)) return;

        LivingEntity target = event.getEntity();

        ItemStack bow = player.getMainHandItem();
        if (bow.isEmpty()) bow = player.getOffhandItem();
        if (bow.isEmpty()) return;

        Holder.Reference<Enchantment> snipeEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(SNIPE_ID)
                .orElse(null);

        if (snipeEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = bow.getEnchantments().getLevel(snipeEnchant);
        if (level <= 0) return;

        // 计算距离
        double distance = player.distanceTo(target);

        // 每10格增加15%伤害 × 附魔等级
        int distanceBonus = (int) (distance / 10.0);
        float bonus = distanceBonus * 0.15F * level;

        float newDamage = event.getOriginalDamage() * (1 + bonus);
        event.setNewDamage(newDamage);
    }
}
