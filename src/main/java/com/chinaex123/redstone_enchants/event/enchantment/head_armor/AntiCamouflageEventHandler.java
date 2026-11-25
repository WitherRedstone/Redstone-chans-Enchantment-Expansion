package com.chinaex123.redstone_enchants.event.enchantment.head_armor;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.List;

/**
 * 反伪装：潜行时显示周围的敌对生物
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class AntiCamouflageEventHandler {
    private static final ResourceLocation ANTI_CAMOUFLAGE_ID = RedstoneEnchants.id("anti_camouflage");

    /** 检测范围 */
    private static final double DETECTION_RANGE = 16.0;

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;

        // 检查头盔是否有反伪装附魔
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.isEmpty()) return;

        ResourceKey<Enchantment> antiCamouflageEnchant = EnchantContext.key(ANTI_CAMOUFLAGE_ID);

        int enchantLevel = EnchantContext.level(helmet, antiCamouflageEnchant);
        if (enchantLevel <= 0) return;

        // 只在潜行时生效
        if (!player.isShiftKeyDown()) return;

        // 获取范围内的所有敌对生物
        List<Monster> nearbyMonsters = player.level().getEntitiesOfClass(
                Monster.class, player.getBoundingBox().inflate(DETECTION_RANGE)
        );

        // 给每个敌对生物施加发光效果
        int duration = 20 + (enchantLevel * 10);
        for (Monster monster : nearbyMonsters) {
            monster.addEffect(new MobEffectInstance(MobEffects.GLOWING, duration, 0, false, false));
        }
    }
}
