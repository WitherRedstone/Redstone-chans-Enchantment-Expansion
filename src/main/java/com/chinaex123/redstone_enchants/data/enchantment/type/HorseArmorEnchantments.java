package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;

import com.chinaex123.redstone_enchants.init.REItemTags;
import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.*;

/**
 * [马铠]类型附魔
 * <p>
 * 只能附魔在马铠上
 */
public final class HorseArmorEnchantments {
    /** 寒霜蹄：马匹移动时冻结周围的水为霜冰 */
    public static final ResourceKey<Enchantment> FROST_HOOVES = EnchantmentConstructorHelper.key("frost_hooves");
    /** 我的小马驹：缩小马匹体型并提高跳跃能力 */
    public static final ResourceKey<Enchantment> MY_LITTLE_PONY = EnchantmentConstructorHelper.key("my_little_pony");
    /** 牧场：马在草方块上时会自然恢复生命 */
    public static final ResourceKey<Enchantment> PASTURE = EnchantmentConstructorHelper.key("pasture");
    /** 精神：马在夜晚时增加速度 */
    public static final ResourceKey<Enchantment> SPIRIT = EnchantmentConstructorHelper.key("spirit");

    private HorseArmorEnchantments() {
    }

    /**
     * [马铠]类型附魔
     * <p>
     * 只能附魔在马铠上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        frostHooves(e);
        myLittlePony(e);
        pasture(e);
        spirit(e);
    }

    /** 寒霜蹄：马匹移动时冻结周围的水为霜冰 */
    private static void frostHooves(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.HORSE_ARMOR, REItemTags.HORSE_ARMOR, 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "body");
        e.immunity(builder, damageSource(damageSourcePredicate().tag(damageTag("minecraft:burn_from_stepping", true)).tag(damageTag("minecraft:bypasses_invulnerability", false))));
        e.location(builder, EnchantmentEffectComponents.LOCATION_CHANGED, replaceDisk(clamped(linear(3.0f, 1.0f), 0.0f, 16.0f), constant(1.0f), vec3i(0, -1, 0), allBlockPredicates(e.blockPredicate("#minecraft:air", 0, 1, 0), e.blockPredicate("minecraft:water", 0, 0, 0), e.fluidPredicate("minecraft:water", 0, 0, 0), unobstructed(0, 0, 0)), simpleState(e.state("minecraft:frosted_ice", "age", "0")), e.gameEvent("minecraft:block_place")), entityProperties("this", entity().flags(flags().setOnGround(true))));
        e.register(builder, FROST_HOOVES, 0xFF55FF, "frost_hooves");
    }

    /** 我的小马驹：缩小马匹体型并提高跳跃能力 */
    private static void myLittlePony(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.HORSE_ARMOR, REItemTags.HORSE_ARMOR, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "body");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.my_little_pony_1", e.attribute("minecraft:generic.scale"), EnchantmentConstructorHelper.operation("add_multiplied_base"), linear(-0.8f, -0.2f)));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.my_little_pony_2", e.attribute("minecraft:generic.jump_strength"), EnchantmentConstructorHelper.operation("add_multiplied_base"), linear(2f, 1f)));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.my_little_pony_3", e.attribute("minecraft:generic.safe_fall_distance"), EnchantmentConstructorHelper.operation("add_multiplied_base"), linear(10f, 10f)));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.my_little_pony_4", e.attribute("minecraft:generic.fall_damage_multiplier"), EnchantmentConstructorHelper.operation("add_multiplied_base"), linear(0.01f, 0.01f)));
        e.register(builder, MY_LITTLE_PONY, 0xFF55FF, "my_little_pony");
    }

    /** 牧场：马在草方块上时会自然恢复生命 */
    private static void pasture(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.HORSE_ARMOR, REItemTags.HORSE_ARMOR, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "body");
        e.register(builder, PASTURE, 0xFF55FF, "pasture");
    }

    /** 精神：马在夜晚时增加速度 */
    private static void spirit(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.HORSE_ARMOR, REItemTags.HORSE_ARMOR, 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "body");
        e.register(builder, SPIRIT, 0xFF55FF, "spirit");
    }

}
