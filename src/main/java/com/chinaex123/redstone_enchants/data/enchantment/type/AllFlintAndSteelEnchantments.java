package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.init.REItemTags;
import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * [打火石]类型附魔
 * <p>
 * 只能附魔在打火石上
 */
public final class AllFlintAndSteelEnchantments {
    /** 灼烧：右键目标时使其燃烧 */
    public static final ResourceKey<Enchantment> SEARING = EnchantmentConstructorHelper.key("searing");

    private AllFlintAndSteelEnchantments() {
    }

    /**
     * [打火石]类型附魔
     * <p>
     * 只能附魔在打火石上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        searing(e);
    }

    /** 灼烧：右键目标时使其燃烧 */
    private static void searing(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ALL_FLINT_AND_STEEL, REItemTags.ALL_FLINT_AND_STEEL, 5, 3,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "mainhand");
        e.register(builder, SEARING, 0x55FFFF, "searing");
    }
}
