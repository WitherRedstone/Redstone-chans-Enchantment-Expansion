package com.chinaex123.redstone_enchants.event.enchantment.all_armor;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

/**
 * 昼夜流转：白天增加伤害，夜晚增加移动速度
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class DaynightCycleEventHandler {
    private static final ResourceLocation DAYNIGHT_CYCLE_ID = RedstoneEnchants.id("daynight_cycle");
    private static final ResourceLocation DAMAGE_MODIFIER_ID = RedstoneEnchants.id("daynight_cycle_damage");
    private static final ResourceLocation SPEED_MODIFIER_ID = RedstoneEnchants.id("daynight_cycle_speed");

    /** 一天的刻数，用于昼夜循环计算 */
    private static final long DAY_LENGTH_TICKS = 24000L;
    /** 白天的刻数上限，达到后视为夜晚 */
    private static final long DAY_END_TICK = 12000L;
    /** 每级附魔提供的伤害或移动速度加成 */
    private static final double BONUS_PER_LEVEL = 0.05D;

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;

        // 计算所有盔甲槽位的附魔等级总和
        int totalLevel = 0;

        EquipmentSlot[] armorSlots = {
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET
        };

        for (EquipmentSlot slot : armorSlots) {
            ItemStack armor = player.getItemBySlot(slot);
            if (armor.isEmpty()) continue;

            ResourceKey<Enchantment> daynightCycleEnchant = EnchantContext.key(DAYNIGHT_CYCLE_ID);

            int enchantLevel = EnchantContext.level(armor, daynightCycleEnchant);
            totalLevel += enchantLevel;
        }

        if (totalLevel <= 0) {
            // 没有附魔时移除所有修饰符
            removeModifiers(player);
            return;
        }

        // 判断是白天还是夜晚
        long dayTime = player.level().getDayTime() % DAY_LENGTH_TICKS;
        boolean isDay = dayTime >= 0 && dayTime < DAY_END_TICK;

        // 获取属性
        var attackDamageAttribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
        var speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (attackDamageAttribute != null && speedAttribute != null) {
            removeModifiers(player);

            // 每级提供5%加成
            double bonus = totalLevel * BONUS_PER_LEVEL;

            if (isDay) {
                // 白天：增加伤害
                AttributeModifier damageModifier = new AttributeModifier(
                        DAMAGE_MODIFIER_ID,
                        bonus,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                );
                attackDamageAttribute.addPermanentModifier(damageModifier);
            } else {
                // 夜晚：增加移动速度
                AttributeModifier speedModifier = new AttributeModifier(
                        SPEED_MODIFIER_ID,
                        bonus,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                );
                speedAttribute.addPermanentModifier(speedModifier);
            }
        }
    }

    private static void removeModifiers(Player player) {
        var attackDamageAttribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
        var speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (attackDamageAttribute != null) {
            attackDamageAttribute.removeModifier(DAMAGE_MODIFIER_ID);
        }
        if (speedAttribute != null) {
            speedAttribute.removeModifier(SPEED_MODIFIER_ID);
        }
    }
}
