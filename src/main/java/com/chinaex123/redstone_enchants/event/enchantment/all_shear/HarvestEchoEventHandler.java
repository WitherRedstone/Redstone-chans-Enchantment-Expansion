package com.chinaex123.redstone_enchants.event.enchantment.all_shear;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

/**
 * 收获回响：剪刀右键使用时，获得生命恢复
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
@SuppressWarnings("deprecation")
public class HarvestEchoEventHandler {
    private static final ResourceLocation HARVEST_ECHO_ID = RedstoneEnchants.id("harvest_echo");

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        ItemStack stack = event.getItemStack();
        if (!stack.is(Items.SHEARS)) return;

        Holder.Reference<Enchantment> harvestEchoEnchant = player.level()
                .registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(HARVEST_ECHO_ID)
                .orElse(null);

        if (harvestEchoEnchant == null) return;

        int enchantLevel = stack.getEnchantments().getLevel(harvestEchoEnchant);
        if (enchantLevel <= 0) return;

        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 0, false, true));
    }
}
