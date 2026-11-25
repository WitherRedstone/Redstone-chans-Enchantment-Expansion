package com.chinaex123.redstone_enchants.event.enchantment.unbreaking;

import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.util.PreservationFeedback;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

/**
 * 保全：装备不会因无耐久而损坏消失
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class PreservationEventHandler {
    private static final ResourceKey<Enchantment> PRESERVATION = EnchantContext.key("preservation");

    private static boolean isExhausted(ItemStack stack, ResourceKey<Enchantment> enchantment) {
        if (stack.isEmpty() || !stack.isDamageableItem()) return false;
        if (EnchantContext.level(stack, enchantment) <= 0) return false;
        return stack.getDamageValue() >= stack.getMaxDamage();
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();

        if (isExhausted(player.getMainHandItem(), PRESERVATION)) {
            PreservationFeedback.warnIfNeeded(player.getMainHandItem(), player);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (event.getEntity() == null) return;

        // 耐久耗尽后显示已损坏提示
        if (isExhausted(event.getItemStack(), PRESERVATION)) {
            event.getToolTip().add(Component.translatable("tooltip.redstone_enchants.preservation_broken"));
        }
    }
}
