package com.chinaex123.redstone_enchants.event.enchantment.horse_armor;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

/**
 * 精神：马在夜晚时增加速度
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class SpiritEventHandler {
    private static final ResourceLocation SPIRIT_ID = RedstoneEnchants.id("spirit");
    private static final ResourceLocation SPIRIT_SPEED_ID = RedstoneEnchants.id("spirit_speed");

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof AbstractHorse horse)) return;

        // 检查马是否有鞍（被骑乘）
        if (!horse.isSaddled()) return;

        // 检查马铠是否有精神附魔
        ItemStack armor = horse.getItemBySlot(EquipmentSlot.BODY);
        if (armor.isEmpty()) return;

        ResourceKey<Enchantment> spiritEnchant = EnchantContext.key(SPIRIT_ID);

        int enchantLevel = EnchantContext.level(armor, spiritEnchant);
        if (enchantLevel <= 0) return;

        // 检查是否是夜晚
        long dayTime = horse.level().getDayTime() % 24000;
        boolean isNight = dayTime >= 13000 && dayTime < 23000;

        // 获取移动速度属性
        var speedAttribute = horse.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttribute == null) return;

        // 移除旧的修饰符
        speedAttribute.removeModifier(SPIRIT_SPEED_ID);

        // 如果是夜晚，添加速度加成
        if (isNight) {
            double speedBonus = 0.15 * enchantLevel;
            AttributeModifier modifier = new AttributeModifier(
                    SPIRIT_SPEED_ID,
                    speedBonus,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
            speedAttribute.addPermanentModifier(modifier);
        }
    }

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (!(event.getEntity() instanceof AbstractHorse horse)) return;

        // 只处理BODY槽位的变化
        if (event.getSlot() != EquipmentSlot.BODY) return;

        // 移除速度加成
        var speedAttribute = horse.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttribute != null) {
            speedAttribute.removeModifier(SPIRIT_SPEED_ID);
        }
    }
}
