package com.chinaex123.redstone_enchants.event.enchantment.all_shear;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.resources.ResourceKey;
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
public class HarvestEchoEventHandler {
    private static final ResourceLocation HARVEST_ECHO_ID = RedstoneEnchants.id("harvest_echo");

    /** 生命恢复持续时间（tick） */
    private static final int DURATION_TICKS = 60;
    /** 生命恢复药水等级 */
    private static final int AMPLIFIER = 0;

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        ItemStack stack = event.getItemStack();
        if (!stack.is(Items.SHEARS)) return;

        ResourceKey<Enchantment> harvestEchoEnchant = EnchantContext.key(HARVEST_ECHO_ID);

        int enchantLevel = EnchantContext.level(stack, harvestEchoEnchant);
        if (enchantLevel <= 0) return;

        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, DURATION_TICKS, AMPLIFIER, false, true));
    }
}
