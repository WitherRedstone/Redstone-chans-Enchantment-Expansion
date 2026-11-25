package com.chinaex123.redstone_enchants.event.enchantment.leg_armor;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
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
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class TacticalKneeEventHandler {
    private static final ResourceLocation TACTICAL_KNEE_ID = RedstoneEnchants.id("tactical_knee");

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        // 检查护腿是否有战术护膝附魔
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        if (leggings.isEmpty()) return;

        ResourceKey<Enchantment> tacticalKneeEnchant = EnchantContext.key(TACTICAL_KNEE_ID);

        int enchantLevel = EnchantContext.level(leggings, tacticalKneeEnchant);
        if (enchantLevel <= 0) return;

        // 检查玩家是否潜行
        if (player.isShiftKeyDown()) {
            // 取消掉落伤害
            event.setCanceled(true);
        }
    }
}
