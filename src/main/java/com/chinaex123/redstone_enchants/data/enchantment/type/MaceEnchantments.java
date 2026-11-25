package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;

import com.chinaex123.redstone_enchants.init.REEnchantmentTags;
import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.*;

/**
 * [重锤]类型附魔
 * <p>
 * 只能附魔在重锤上
 */
public final class MaceEnchantments {
    /** 闪电使者：从高处(≥8格)攻击时召唤闪电 */
    public static final ResourceKey<Enchantment> BOLTBRINGER = EnchantmentConstructorHelper.key("boltbringer");
    /** 俯冲炸弹：提供跳跃加成，从高处(≥4格)攻击时引发爆炸 */
    public static final ResourceKey<Enchantment> DIVE_BOMB = EnchantmentConstructorHelper.key("dive_bomb");
    /** 轻重两仪：增加攻击范围并降低重力 */
    public static final ResourceKey<Enchantment> LIGHTNESS_HEAVY_DUALISM = EnchantmentConstructorHelper.key("lightness-heavy_dualism");
    /** 势能转化：下落攻击时，每格高度都增加伤害，并对有护甲的目标造成额外伤害 */
    public static final ResourceKey<Enchantment> POTENTIAL_CONVERSION = EnchantmentConstructorHelper.key("potential_conversion");
    /** 重击手：攻击有几率对生物造成强力减速 */
    public static final ResourceKey<Enchantment> SLUGGER = EnchantmentConstructorHelper.key("slugger");
    /** 风力推进：攻击地面可将自己弹向空中 */
    public static final ResourceKey<Enchantment> WIND_PROPULSION = EnchantmentConstructorHelper.key("wind_propulsion");

    private MaceEnchantments() {
    }

    /**
     * [重锤]类型附魔
     * <p>
     * 只能附魔在重锤上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        boltbringer(e);
        diveBomb(e);
        lightnessHeavyDualism(e);
        potentialConversion(e);
        slugger(e);
        windPropulsion(e);
    }

    /** 闪电使者：从高处(≥8格)攻击时召唤闪电 */
    private static void boltbringer(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#c:tools/mace", "#c:tools/mace", 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_MACE));
        e.register(builder, BOLTBRINGER, 0xFF55FF, "boltbringer");
    }

    /** 俯冲炸弹：提供跳跃加成，从高处(≥4格)攻击时引发爆炸 */
    private static void diveBomb(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#c:tools/mace", "#c:tools/mace", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_MACE));
        e.postAttack(builder, target("attacker"), target("victim"),
                explode(false, e.damageType("minecraft:explosion"), null, null, vec(0.0, 0.0, 0.0),
                        linear(2f, 1f), true, interaction("mob"), particle("minecraft:explosion_emitter"),
                        particle("minecraft:explosion"), e.sound("minecraft:entity.generic.explode")),
                entityProperties("attacker", entity().flags(flags().setIsFlying(false)).moving(allMovements(fallDistance(atLeast(4f)))))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.jump_strength_1", e.attribute("minecraft:generic.jump_strength"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.2f, 0.2f))
        );
        e.register(builder, DIVE_BOMB, 0xFF55FF, "dive_bomb");
    }

    /** 轻重两仪：增加攻击范围并降低重力 */
    private static void lightnessHeavyDualism(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#c:tools/mace", "#c:tools/mace", 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.lightness-heavy_dualism_1", e.attribute("minecraft:generic.gravity"),
                EnchantmentConstructorHelper.operation("add_multiplied_base"), linear(-0.12f, -0.02f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.lightness-heavy_dualism_2", e.attribute("minecraft:player.entity_interaction_range"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.8f, 0.05f))
        );
        e.register(builder, LIGHTNESS_HEAVY_DUALISM, 0xFF55FF, "lightness-heavy_dualism");
    }

    /** 势能转化：下落攻击时，每格高度都增加伤害，并对有护甲的目标造成额外伤害 */
    private static void potentialConversion(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#c:tools/mace", "#c:tools/mace", 3, 4,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand");
        e.register(builder, POTENTIAL_CONVERSION, 0xFFAA00, "potential_conversion");
    }

    /** 重击手：攻击有几率对生物造成强力减速 */
    private static void slugger(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#c:tools/mace", "#c:tools/mace", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.postAttack(builder, target("attacker"), target("victim"),
                mobEffect(e.mobEffects(MobEffects.MOVEMENT_SLOWDOWN), constant(3f), constant(3f), constant(5f), constant(5f)),
                randomChance(levelNumber(linear(0.2f, 0.1f)))
        );
        e.register(builder, SLUGGER, 0xFF55FF, "slugger");
    }

    /** 风力推进：攻击地面可将自己弹向空中 */
    private static void windPropulsion(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#c:tools/mace", "#c:tools/mace", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.entity(builder, EnchantmentEffectComponents.HIT_BLOCK, explode(false, null, lookup(new float[] { 1.25f, 1.65f, 2.25f }, linear(1.25f, 0.35f)),
                e.blocks("#minecraft:blocks_wind_charge_explosions"), vec(0.0, 0.5, 0.0), constant(3.5f), false, interaction("trigger"),
                particle("minecraft:gust_emitter_small"), particle("minecraft:gust_emitter_large"), e.sound("minecraft:entity.wind_charge.wind_burst")),
                entityProperties("this", entity().flags(flags().setOnGround(true))));
        e.register(builder, WIND_PROPULSION, 0xFF55FF, "wind_propulsion");
    }

}
