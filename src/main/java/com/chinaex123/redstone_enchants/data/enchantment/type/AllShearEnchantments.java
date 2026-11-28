package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import com.chinaex123.redstone_enchants.init.REItemTags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * [剪刀]类型附魔
 * <p>
 * 只能附魔在剪刀上
 */
public final class AllShearEnchantments {
    /** 绵延不绝：剪羊毛时，有概率让羊立刻重新长出羊毛 */
    public static final ResourceKey<Enchantment> ENDLESS_WOOL = EnchantmentConstructorHelper.key("endless_wool");
    /** 经验修剪：剪下的羊毛改为掉落随机的经验球 */
    public static final ResourceKey<Enchantment> EXPERIENCE_SHEAR = EnchantmentConstructorHelper.key("experience_shear");
    /** 收获回响：剪刀右键使用时，获得生命恢复 */
    public static final ResourceKey<Enchantment> HARVEST_ECHO = EnchantmentConstructorHelper.key("harvest_echo");
    /** 牧羊人：修剪羊毛或采集蜜脾时有概率获得多个 */
    public static final ResourceKey<Enchantment> SHEPHERD = EnchantmentConstructorHelper.key("shepherd");

    private AllShearEnchantments() {
    }

    /**
     * [剪刀]类型附魔
     * <p>
     * 只能附魔在剪刀上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        endlessWool(e);
        experienceShear(e);
        harvestEcho(e);
        shepherd(e);
    }

    /** 绵延不绝：剪羊毛时，有概率让羊立刻重新长出羊毛 */
    private static void endlessWool(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ALL_SHEAR, REItemTags.ALL_SHEAR, 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.register(builder, ENDLESS_WOOL, 0xFF55FF, "endless_wool");
    }

    /** 经验修剪：剪下的羊毛改为掉落随机的经验球 */
    private static void experienceShear(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ALL_SHEAR, REItemTags.ALL_SHEAR, 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.register(builder, EXPERIENCE_SHEAR, 0xFF55FF, "experience_shear");
    }

    /** 收获回响：剪刀右键使用时，获得生命恢复 */
    private static void harvestEcho(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ALL_SHEAR, REItemTags.ALL_SHEAR, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.register(builder, HARVEST_ECHO, 0xFF55FF, "harvest_echo");
    }

    /** 牧羊人：修剪羊毛或采集蜜脾时有概率获得多个 */
    private static void shepherd(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ALL_SHEAR, REItemTags.ALL_SHEAR, 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.register(builder, SHEPHERD, 0xFF55FF, "shepherd");
    }

}
