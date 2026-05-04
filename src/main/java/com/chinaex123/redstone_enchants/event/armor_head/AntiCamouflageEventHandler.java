package com.chinaex123.redstone_enchants.event.armor_head;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
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
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class AntiCamouflageEventHandler {
    private static final Identifier ANTI_CAMOUFLAGE_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "anti_camouflage");
    private static final double DETECTION_RANGE = 16.0; // 检测范围 16 格

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;

        // 检查头盔是否有反伪装附魔
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.isEmpty()) return;

        Holder.Reference<Enchantment> antiCamouflageEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(ANTI_CAMOUFLAGE_ID)
                .orElse(null);

        if (antiCamouflageEnchant == null) return;

        @SuppressWarnings("deprecation")
        int enchantLevel = helmet.getEnchantments().getLevel(antiCamouflageEnchant);
        if (enchantLevel <= 0) return;

        // 只在潜行时生效
        if (!player.isShiftKeyDown()) return;

        // 获取范围内的所有敌对生物
        List<Monster> nearbyMonsters = player.level().getEntitiesOfClass(
                Monster.class, player.getBoundingBox().inflate(DETECTION_RANGE)
        );

        // 给每个敌对生物施加发光效果
        int duration = 40 + (enchantLevel * 10);
        for (Monster monster : nearbyMonsters) {
            monster.addEffect(new MobEffectInstance(MobEffects.GLOWING, duration, 0, false, false));
        }
    }
}
