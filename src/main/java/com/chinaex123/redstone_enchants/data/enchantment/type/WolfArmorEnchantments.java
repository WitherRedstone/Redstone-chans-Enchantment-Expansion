package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

import com.chinaex123.redstone_enchants.init.REItemTags;
import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.*;

/**
 * [狼铠]类型附魔
 * <p>
 * 只能附魔在狼铠上
 */
public final class WolfArmorEnchantments {
    /** 尸体回收：狼击杀生物后回复最大生命值 */
    public static final ResourceKey<Enchantment> CARRION_EATER = EnchantmentConstructorHelper.key("carrion_eater");
    /** 狼群领袖：附近每多一只狼，增加你狼的伤害 */
    public static final ResourceKey<Enchantment> PACK_LEADER = EnchantmentConstructorHelper.key("pack_leader");
    /** 追踪者：狼攻击时，标记它攻击过的敌人 */
    public static final ResourceKey<Enchantment> TRACKER = EnchantmentConstructorHelper.key("tracker");
    /** 狼魂守护：提升狼铠所有属性 */
    public static final ResourceKey<Enchantment> WOLF_SPIRIT_SHIELD = EnchantmentConstructorHelper.key("wolf_spirit_shield");

    private WolfArmorEnchantments() {
    }

    /**
     * [狼铠]类型附魔
     * <p>
     * 只能附魔在狼铠上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        carrionEater(e);
        packLeader(e);
        tracker(e);
        wolfSpiritShield(e);
    }

    /** 尸体回收：狼击杀生物后回复最大生命值 */
    private static void carrionEater(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.WOLF_ARMOR, REItemTags.WOLF_ARMOR, 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "body");
        e.register(builder, CARRION_EATER, 0xFF55FF, "carrion_eater");
    }

    /** 狼群领袖：附近每多一只狼，增加你狼的伤害 */
    private static void packLeader(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.WOLF_ARMOR, REItemTags.WOLF_ARMOR, 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "body");
        e.register(builder, PACK_LEADER, 0xFF55FF, "pack_leader");
    }

    /** 追踪者：狼攻击时，标记它攻击过的敌人 */
    private static void tracker(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.WOLF_ARMOR, REItemTags.WOLF_ARMOR, 4, 1,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "body");
        e.register(builder, TRACKER, 0x55FFFF, "tracker");
    }

    /** 狼魂守护：提升狼铠所有属性 */
    private static void wolfSpiritShield(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.WOLF_ARMOR, REItemTags.WOLF_ARMOR, 2, 5,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "body");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.wolf_spirit_shield_1", e.attribute("minecraft:generic.attack_damage"),
                EnchantmentConstructorHelper.operation("add_value"), linear(5.0f, 5.0f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.wolf_spirit_shield_2", e.attribute("minecraft:generic.armor"),
                EnchantmentConstructorHelper.operation("add_value"), linear(5f, 5f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.wolf_spirit_shield_3", e.attribute("minecraft:generic.max_health"),
                EnchantmentConstructorHelper.operation("add_value"), linear(10f, 10f))
        );
        e.register(builder, WOLF_SPIRIT_SHIELD, 0xFFAA00, "wolf_spirit_shield");
    }
}
