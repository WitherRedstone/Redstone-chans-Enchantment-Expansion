package com.chinaex123.redstone_enchants.event.enchantment.unbreaking;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.util.PreservationFeedback;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
@SuppressWarnings("deprecation")
public class PreservationEventHandler {
    private static final ResourceLocation PRESERVATION_ID = RedstoneEnchants.id("preservation");

    private static boolean isExhausted(ItemStack stack, Holder.Reference<Enchantment> enchantment) {
        if (stack.isEmpty() || !stack.isDamageableItem()) return false;
        if (stack.getEnchantments().getLevel(enchantment) <= 0) return false;
        return stack.getDamageValue() >= stack.getMaxDamage();
    }

    private static Holder.Reference<Enchantment> findEnchantment(Player player) {
        return player.level()
                .registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(PRESERVATION_ID)
                .orElse(null);
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        Holder.Reference<Enchantment> enchantment = findEnchantment(player);
        if (enchantment == null) return;

        if (isExhausted(player.getMainHandItem(), enchantment)) {
            PreservationFeedback.warnIfNeeded(player.getMainHandItem(), player);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (event.getEntity() == null) return;

        Holder.Reference<Enchantment> enchantment = findEnchantment(event.getEntity());
        if (enchantment == null) return;

        // 耐久耗尽后显示已损坏提示
        if (isExhausted(event.getItemStack(), enchantment)) {
            event.getToolTip().add(Component.translatable("tooltip.redstone_enchants.preservation_broken"));
        }
    }
}
