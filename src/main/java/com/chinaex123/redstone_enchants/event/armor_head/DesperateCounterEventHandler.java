package com.chinaex123.redstone_enchants.event.armor_head;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffects;
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
 * 绝境逆袭：自身拥有失明或黑暗时增加攻击伤害
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class DesperateCounterEventHandler {
    private static final Identifier DESPERATE_COUNTER_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "desperate_counter");
    private static final Identifier DESPERATE_COUNTER_MODIFIER_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "desperate_counter_damage");

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;

        // 检查头盔是否有绝境逆袭附魔
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.isEmpty()) return;

        Holder.Reference<Enchantment> desperateCounterEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(DESPERATE_COUNTER_ID)
                .orElse(null);

        if (desperateCounterEnchant == null) return;

        @SuppressWarnings("deprecation")
        int enchantLevel = helmet.getEnchantments().getLevel(desperateCounterEnchant);
        if (enchantLevel <= 0) return;

        // 检查是否有失明或黑暗效果
        boolean hasBlindness = player.hasEffect(MobEffects.BLINDNESS);
        boolean hasDarkness = player.hasEffect(MobEffects.DARKNESS);

        // 获取攻击伤害属性
        var attackDamageAttribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attackDamageAttribute == null) return;

        // 移除旧的修饰符
        attackDamageAttribute.removeModifier(DESPERATE_COUNTER_MODIFIER_ID);

        // 如果有失明或黑暗，添加伤害加成
        if (hasBlindness || hasDarkness) {
            double damageBonus = 0.25 * enchantLevel;
            AttributeModifier modifier = new AttributeModifier(
                    DESPERATE_COUNTER_MODIFIER_ID,
                    damageBonus,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
            attackDamageAttribute.addPermanentModifier(modifier);
        }
    }
}
