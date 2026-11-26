package com.chinaex123.redstone_enchants.event.enchantment.wolf_armor;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
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
public class TrackerEventHandler {
    private static final ResourceLocation TRACKER_ID = RedstoneEnchants.id("tracker");

    /** 发光效果持续时间（tick）：基础 80 加每级 20 */
    private static final int DURATION_TICKS = 80 + 20;
    /** 发光效果等级 */
    private static final int GLOWING_AMPLIFIER = 0;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        Entity directEntity = event.getSource().getDirectEntity();

        if (!(directEntity instanceof Wolf wolf)) return;

        LivingEntity target = event.getEntity();

        // 检查狼铠是否有追踪者附魔
        ItemStack armor = wolf.getItemBySlot(EquipmentSlot.BODY);
        if (armor.isEmpty()) return;

        ResourceKey<Enchantment> trackerEnchant = EnchantContext.key(TRACKER_ID);

        int enchantLevel = EnchantContext.level(armor, trackerEnchant);
        if (enchantLevel <= 0) return;

        // 给目标施加发光效果
        target.addEffect(new MobEffectInstance(
                MobEffects.GLOWING, DURATION_TICKS, GLOWING_AMPLIFIER, false, false
        ));
    }
}
