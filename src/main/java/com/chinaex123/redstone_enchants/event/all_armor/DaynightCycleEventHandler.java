package com.chinaex123.redstone_enchants.event.all_armor;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
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
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class DaynightCycleEventHandler {
    private static final Identifier DAYNIGHT_CYCLE_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "daynight_cycle");
    private static final Identifier DAMAGE_MODIFIER_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "daynight_cycle_damage");
    private static final Identifier SPEED_MODIFIER_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "daynight_cycle_speed");

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

            Holder.Reference<Enchantment> daynightCycleEnchant = player.level()
                    .registryAccess()
                    .lookupOrThrow(Registries.ENCHANTMENT)
                    .get(DAYNIGHT_CYCLE_ID)
                    .orElse(null);

            if (daynightCycleEnchant == null) continue;

            @SuppressWarnings("deprecation")
            int enchantLevel = armor.getEnchantments().getLevel(daynightCycleEnchant);
            totalLevel += enchantLevel;
        }

        if (totalLevel <= 0) {
            // 没有附魔时移除所有修饰符
            removeModifiers(player);
            return;
        }

        // 判断是白天还是夜晚
        long dayTime = player.level().getGameTime() % 24000;
        boolean isDay = dayTime >= 0 && dayTime < 12000;

        // 获取属性
        var attackDamageAttribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
        var speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (attackDamageAttribute != null && speedAttribute != null) {
            // 移除旧的修饰符
            removeModifiers(player);

            // 每级提供5%加成
            double bonus = totalLevel * 0.05;

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
