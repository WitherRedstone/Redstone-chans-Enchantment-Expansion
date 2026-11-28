package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import com.chinaex123.redstone_enchants.init.REEnchantmentTags;
import com.chinaex123.redstone_enchants.init.REItemTags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * [剑+弓]类型附魔
 * <p>
 * 只能附魔在剑/弓上
 */
public final class SwordsAndBowEnchantments {
    /** 空中抛掷：攻击时将被击中的生物抛向空中 */
    public static final ResourceKey<Enchantment> AIR_TOSS = EnchantmentConstructorHelper.key("air_toss");
    /** 赌徒：50%概率伤害+40%，50%概率伤害-20% */
    public static final ResourceKey<Enchantment> GAMBLER = EnchantmentConstructorHelper.key("gambler");

    private SwordsAndBowEnchantments() {
    }

    /**
     * [剑+弓]类型附魔
     * <p>
     * 只能附魔在剑/弓上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        airToss(e);
        gambler(e);
    }

    /** 空中抛掷：攻击时将被击中的生物抛向空中 */
    private static void airToss(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_BOW, REItemTags.SWORDS_AND_BOW, 3, 2,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_SWORDS_AND_BOW));
        e.register(builder, AIR_TOSS, 0xFF55FF, "air_toss");
    }

    /** 赌徒：50%概率伤害+40%，50%概率伤害-20% */
    private static void gambler(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_BOW, REItemTags.SWORDS_AND_BOW, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        e.register(builder, GAMBLER, 0xFF55FF, "gambler");
    }

}
