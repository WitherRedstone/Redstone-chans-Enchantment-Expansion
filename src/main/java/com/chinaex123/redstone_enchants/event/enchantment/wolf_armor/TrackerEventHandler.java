package com.chinaex123.redstone_enchants.event.enchantment.wolf_armor;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 追踪者：狼攻击时，标记它攻击过的敌人
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
@SuppressWarnings("deprecation")
public class TrackerEventHandler {
    private static final ResourceLocation TRACKER_ID = RedstoneEnchants.id("tracker");

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        Entity directEntity = event.getSource().getDirectEntity();

        if (!(directEntity instanceof Wolf wolf)) return;

        LivingEntity target = event.getEntity();

        // 检查狼铠是否有追踪者附魔
        ItemStack armor = wolf.getItemBySlot(EquipmentSlot.BODY);
        if (armor.isEmpty()) return;

        Holder.Reference<Enchantment> trackerEnchant = wolf.level()
                .registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(TRACKER_ID)
                .orElse(null);

        if (trackerEnchant == null) return;

        int enchantLevel = armor.getEnchantments().getLevel(trackerEnchant);
        if (enchantLevel <= 0) return;

        // 给目标施加发光效果
        int duration = 80 + (enchantLevel * 20);
        target.addEffect(new MobEffectInstance(
                MobEffects.GLOWING, duration, 0, false, false
        ));
    }
}
