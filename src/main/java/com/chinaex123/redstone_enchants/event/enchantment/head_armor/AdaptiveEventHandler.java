package com.chinaex123.redstone_enchants.event.enchantment.head_armor;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.event.EnchantEffectTracker;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

/**
 * 矿工：在低于Y0时获得夜视效果
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class AdaptiveEventHandler {
    private static final ResourceLocation ADAPTIVE_ID = RedstoneEnchants.id("adaptive");

    /** 夜视持续时间（tick） */
    private static final int NIGHT_VISION_DURATION_TICKS = 20 * 12;
    /** 夜视效果等级（0 = I 级） */
    private static final int NIGHT_VISION_AMPLIFIER = 0;
    /** 触发夜视的高度上限（Y） */
    private static final double TRIGGER_Y = 0.0D;

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getEntity() instanceof Player player)) return;

        ResourceKey<Enchantment> adaptiveEnchant = EnchantContext.key(ADAPTIVE_ID);

        // 检查头盔是否有矿工附魔
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        int enchantLevel = helmet.isEmpty() ? 0 : EnchantContext.level(helmet, adaptiveEnchant);

        // 检查是否在Y0以下
        if (enchantLevel > 0 && player.getY() < TRIGGER_Y) {
            EnchantEffectTracker.apply(player, MobEffects.NIGHT_VISION, NIGHT_VISION_DURATION_TICKS, NIGHT_VISION_AMPLIFIER);
        } else {
            EnchantEffectTracker.removeIfTracked(player, MobEffects.NIGHT_VISION);
        }
    }
}
