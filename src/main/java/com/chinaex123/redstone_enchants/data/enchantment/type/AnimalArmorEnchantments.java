package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

import com.chinaex123.redstone_enchants.init.REItemTags;
import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.*;

/**
 * [马铠+狼铠]类型附魔
 * <p>
 * 只能附魔在马铠/狼铠上
 */
public final class AnimalArmorEnchantments {
    /** 强化铠甲：提升防御力但降低跳跃高度 */
    public static final ResourceKey<Enchantment> ENHANCED_ARMOR = EnchantmentConstructorHelper.key("enhanced_armor");
    /** 信使：提升坐骑移动灵活性 */
    public static final ResourceKey<Enchantment> MESSENGER = EnchantmentConstructorHelper.key("messenger");

    private AnimalArmorEnchantments() {
    }

    /**
     * [马铠+狼铠]类型附魔
     * <p>
     * 只能附魔在马铠/狼铠上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        enhancedArmor(e);
        messenger(e);
    }

    /** 强化铠甲：提升防御力但降低跳跃高度 */
    private static void enhancedArmor(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.HORSE_ANIMAL_ARMOR, REItemTags.HORSE_ANIMAL_ARMOR, 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "body");
        e.attributes(builder, attributeEffect("redstone_enchants:enhanced_armor_bonus", e.attribute("minecraft:generic.armor"),
                EnchantmentConstructorHelper.operation("add_value"), linear(2f, 2f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enhanced_armor_penalty", e.attribute("minecraft:generic.jump_strength"),
                EnchantmentConstructorHelper.operation("add_multiplied_base"), linear(-0.05f, -0.05f))
        );
        e.register(builder, ENHANCED_ARMOR, 0xFF55FF, "enhanced_armor");
    }

    /** 信使：提升坐骑移动灵活性 */
    private static void messenger(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.HORSE_ANIMAL_ARMOR, REItemTags.HORSE_ANIMAL_ARMOR, 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "body");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.messenger_1", e.attribute("minecraft:generic.movement_speed"),
                EnchantmentConstructorHelper.operation("add_multiplied_base"), linear(0.25f, 0.125f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.messenger_2", e.attribute("minecraft:generic.movement_efficiency"),
                EnchantmentConstructorHelper.operation("add_value"), linear(1f, 0.5f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.messenger_3", e.attribute("minecraft:generic.water_movement_efficiency"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.6f, 0.1f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.messenger_4", e.attribute("minecraft:generic.jump_strength"),
                EnchantmentConstructorHelper.operation("add_multiplied_base"), linear(0.2f, 0.1f))
        );
        e.register(builder, MESSENGER, 0xFF55FF, "messenger");
    }

}
