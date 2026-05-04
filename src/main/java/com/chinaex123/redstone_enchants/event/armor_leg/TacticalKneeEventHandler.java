package com.chinaex123.redstone_enchants.event.armor_leg;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;

/**
 * 战术护膝：落地时潜行可抵消掉落伤害
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class TacticalKneeEventHandler {
    private static final Identifier TACTICAL_KNEE_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "tactical_knee");

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        // 检查护腿是否有战术护膝附魔
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        if (leggings.isEmpty()) return;

        Holder.Reference<Enchantment> tacticalKneeEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(TACTICAL_KNEE_ID)
                .orElse(null);

        if (tacticalKneeEnchant == null) return;

        @SuppressWarnings("deprecation")
        int enchantLevel = leggings.getEnchantments().getLevel(tacticalKneeEnchant);
        if (enchantLevel <= 0) return;

        // 检查玩家是否潜行
        if (player.isShiftKeyDown()) {
            // 取消掉落伤害
            event.setCanceled(true);
        }
    }
}
