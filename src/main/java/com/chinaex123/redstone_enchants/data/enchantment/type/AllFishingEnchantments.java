package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;

import com.chinaex123.redstone_enchants.init.REItemTags;
import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.*;

/**
 * [鱼竿]类型附魔
 * <p>
 * 只能附魔在鱼竿上
 */
public final class AllFishingEnchantments {
    /** 渔夫：钓鱼后收获鱼的数量有概率翻倍 */
    public static final ResourceKey<Enchantment> ANGLER = EnchantmentConstructorHelper.key("angler");
    /** 导电鱼线：雷雨天时，勾住生物产生闪电 */
    public static final ResourceKey<Enchantment> CONDUCTIVE_LINE = EnchantmentConstructorHelper.key("conductive_line");
    /** 潮汐感应：雷雨天时，钓鱼等待时间大幅缩短且鱼的几率大幅提高 */
    public static final ResourceKey<Enchantment> TIDE_SENSE = EnchantmentConstructorHelper.key("tide_sense");

    private AllFishingEnchantments() {
    }

    /**
     * [鱼竿]类型附魔
     * <p>
     * 只能附魔在鱼竿上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        angler(e);
        conductiveLine(e);
        tideSense(e);
    }

    /** 渔夫：钓鱼后收获鱼的数量有概率翻倍 */
    private static void angler(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ALL_FISHING, REItemTags.ALL_FISHING, 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.register(builder, ANGLER, 0xFF55FF, "angler");
    }

    /** 导电鱼线：雷雨天时，勾住生物产生闪电 */
    private static void conductiveLine(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ALL_FISHING, REItemTags.ALL_FISHING, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.register(builder, CONDUCTIVE_LINE, 0xFF55FF, "conductive_line");
    }

    /** 潮汐感应：雷雨天时，钓鱼等待时间大幅缩短且鱼的几率大幅提高 */
    private static void tideSense(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ALL_FISHING, REItemTags.ALL_FISHING, 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.value(builder, EnchantmentEffectComponents.FISHING_TIME_REDUCTION, add(linear(10.0f, 10.0f)), null);
        e.register(builder, TIDE_SENSE, 0xFF55FF, "tide_sense");
    }
}
