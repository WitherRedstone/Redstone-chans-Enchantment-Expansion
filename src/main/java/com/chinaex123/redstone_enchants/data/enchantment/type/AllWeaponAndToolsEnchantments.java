package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import com.chinaex123.redstone_enchants.init.REItemTags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.attributeEffect;
import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.linear;

/**
 * [武器+工具]类型附魔
 * <p>
 * 只能附魔在剑/斧/镐/锹/锄/三叉戟/重锤/弓/弩/上
 */
public final class AllWeaponAndToolsEnchantments {
    /** 延伸：增加攻击/交互距离 */
    public static final ResourceKey<Enchantment> EXTEND = EnchantmentConstructorHelper.key("extend");
    /** 轻便：手持时增加移动速度 */
    public static final ResourceKey<Enchantment> LIGHTWEIGHT = EnchantmentConstructorHelper.key("lightweight");

    private AllWeaponAndToolsEnchantments() {
    }

    /**
     * [武器+工具]类型附魔
     * <p>
     * 只能附魔在剑/斧/镐/锹/锄上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        extend(e);
        lightweight(e);
    }

    /** 延伸：增加攻击/交互距离 */
    private static void extend(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ALL_TOOLS, REItemTags.ALL_TOOLS, 2, 3,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand", "offhand");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.extend_1", e.attribute("minecraft:player.block_interaction_range"),
                EnchantmentConstructorHelper.operation("add_value"), linear(1f, 1f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.extend_2", e.attribute("minecraft:player.entity_interaction_range"),
                EnchantmentConstructorHelper.operation("add_value"), linear(1f, 1f))
        );
        e.register(builder, EXTEND, 0xFFAA00, "extend");
    }

    /** 轻便：手持时增加移动速度 */
    private static void lightweight(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ALL_TOOLS, REItemTags.ALL_TOOLS, 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.attributes(builder, attributeEffect("redstone_enchants:lightweight", e.attribute("minecraft:generic.movement_speed"),
                EnchantmentConstructorHelper.operation("add_multiplied_total"), linear(0.05f, 0.05f))
        );
        e.register(builder, LIGHTWEIGHT, 0xFF55FF, "lightweight");
    }

}
