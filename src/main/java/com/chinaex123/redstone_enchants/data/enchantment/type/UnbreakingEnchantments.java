package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;

import com.chinaex123.redstone_enchants.init.REEnchantmentTags;
import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.*;

/**
 * [通用]类型附魔
 * <p>
 * 只能附魔在任何物品上
 */
public final class UnbreakingEnchantments {
    /** 高级耐久：应用附魔到物品时，有概率不消耗耐久 */
    public static final ResourceKey<Enchantment> ADVANCED_UNBREAKING = EnchantmentConstructorHelper.key("advanced_unbreaking");
    /** 坚不可摧：物品不再消耗耐久 */
    public static final ResourceKey<Enchantment> INDESTRUCTIBLE = EnchantmentConstructorHelper.key("indestructible");
    /** 保全：装备不会因无耐久而损坏消失 */
    public static final ResourceKey<Enchantment> PRESERVATION = EnchantmentConstructorHelper.key("preservation");
    /** 牺牲：受到攻击时自动修复手持物品 */
    public static final ResourceKey<Enchantment> SACRIFICE = EnchantmentConstructorHelper.key("sacrifice");
    /** 坚固：装备免疫爆炸、闪电和岩浆 */
    public static final ResourceKey<Enchantment> STURDY = EnchantmentConstructorHelper.key("sturdy");

    private UnbreakingEnchantments() {
    }

    /**
     * [通用]类型附魔
     * <p>
     * 只能附魔在任何物品上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        advancedUnbreaking(e);
        indestructible(e);
        preservation(e);
        sacrifice(e);
        sturdy(e);
    }

    /** 高级耐久：应用附魔到物品时，有概率不消耗耐久 */
    private static void advancedUnbreaking(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#c:enchantables", "#c:enchantables", 1, 1,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "any");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_UNBREAKING));
        e.value(builder, EnchantmentEffectComponents.ITEM_DAMAGE, removeBinomial(fraction(constant(4f), constant(5f))), null);
        e.register(builder, ADVANCED_UNBREAKING, 0xFFAA00, "advanced_unbreaking");
    }

    /** 坚不可摧：物品不再消耗耐久 */
    private static void indestructible(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#c:enchantables", "#c:enchantables", 1, 1,
                Enchantment.constantCost(0), Enchantment.constantCost(0), 50, "any");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_INDESTRUCTIBLE));
        e.register(builder, INDESTRUCTIBLE, 0xFF00BB, "indestructible");
    }

    /** 保全：装备不会因无耐久而损坏消失 */
    private static void preservation(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#c:enchantables", "#c:enchantables", 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "any");
        e.register(builder, PRESERVATION, 0xFF55FF, "preservation");
    }

    /** 牺牲：受到攻击时自动修复手持物品 */
    private static void sacrifice(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#c:enchantables", "#c:enchantables", 2, 5,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "any");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_UNBREAKING));
        e.register(builder, SACRIFICE, 0xFFAA00, "sacrifice");
    }

    /** 坚固：装备免疫爆炸、闪电和岩浆 */
    private static void sturdy(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#c:enchantables", "#c:enchantables", 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "any");
        e.register(builder, STURDY, 0xFF55FF, "sturdy");
    }

}
