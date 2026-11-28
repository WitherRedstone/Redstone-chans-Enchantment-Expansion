package com.chinaex123.redstone_enchants.event.enchantment.head_armor;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class DesperateCounterEventHandler {
    private static final ResourceLocation DESPERATE_COUNTER_ID = RedstoneEnchants.id("desperate_counter");
    private static final ResourceLocation DESPERATE_COUNTER_MODIFIER_ID = RedstoneEnchants.id("desperate_counter_damage");

    /** 1 级时的伤害加成 */
    private static final double DAMAGE_BONUS_BASE = 0.25D;
    /** 每级额外增加的伤害加成 */
    private static final double DAMAGE_BONUS_PER_LEVEL = 0.25D;

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getEntity() instanceof Player player)) return;

        // 检查头盔是否有绝境逆袭附魔
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.isEmpty()) return;

        ResourceKey<Enchantment> desperateCounterEnchant = EnchantContext.key(DESPERATE_COUNTER_ID);

        int enchantLevel = EnchantContext.level(helmet, desperateCounterEnchant);
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
            double damageBonus = DAMAGE_BONUS_BASE + DAMAGE_BONUS_PER_LEVEL * (enchantLevel - 1);
            AttributeModifier modifier = new AttributeModifier(
                    DESPERATE_COUNTER_MODIFIER_ID,
                    damageBonus,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
            attackDamageAttribute.addPermanentModifier(modifier);
        }
    }
}
