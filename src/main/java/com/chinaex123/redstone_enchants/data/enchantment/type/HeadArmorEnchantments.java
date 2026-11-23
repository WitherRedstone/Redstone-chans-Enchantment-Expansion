package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;

import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.*;

/**
 * [头盔]类型附魔
 * <p>
 * 只能附魔在头盔上
 */
public final class HeadArmorEnchantments {
    /** 矿工：在低于Y0时获得夜视效果 */
    public static final ResourceKey<Enchantment> ADAPTIVE = EnchantmentConstructorHelper.key("adaptive");
    /** 以寡敌众：根据周围敌人数目，每多一个敌人伤害/护甲+2% */
    public static final ResourceKey<Enchantment> AGAINST_ALL_ODDS = EnchantmentConstructorHelper.key("against_all_odds");
    /** 反伪装：潜行时显示周围的敌对生物 */
    public static final ResourceKey<Enchantment> ANTI_CAMOUFLAGE = EnchantmentConstructorHelper.key("anti_camouflage");
    /** 绝境逆袭：自身拥有失明或黑暗时增加攻击伤害 */
    public static final ResourceKey<Enchantment> DESPERATE_COUNTER = EnchantmentConstructorHelper.key("desperate_counter");
    /** 幸运增幅：提升幸运值 */
    public static final ResourceKey<Enchantment> LUCKY_BOOST = EnchantmentConstructorHelper.key("lucky_boost");
    /** 幸运之光：获得幸运效果 */
    public static final ResourceKey<Enchantment> LUCKY_LIGHT = EnchantmentConstructorHelper.key("lucky_light");
    /** 巨大化：增大玩家体型 */
    public static final ResourceKey<Enchantment> MAXIMIZATION = EnchantmentConstructorHelper.key("maximization");
    /** 迷你化：缩小玩家体型 */
    public static final ResourceKey<Enchantment> MINIFY = EnchantmentConstructorHelper.key("minify");
    /** [移动轨迹]樱花：移动时留下樱花花瓣粒子效果 */
    public static final ResourceKey<Enchantment> TRAIL_CHERRY_LEAVES = EnchantmentConstructorHelper.key("trail_cherry_leaves");
    /** [移动轨迹]龙息：移动时留下龙息粒子效果 */
    public static final ResourceKey<Enchantment> TRAIL_DRAGON_BREATH = EnchantmentConstructorHelper.key("trail_dragon_breath");
    /** [移动轨迹]烟花火箭：移动时留下烟花火箭尾迹粒子效果 */
    public static final ResourceKey<Enchantment> TRAIL_FIREWORK = EnchantmentConstructorHelper.key("trail_firework");
    /** [移动轨迹]荧光：移动时留下荧光粒子效果 */
    public static final ResourceKey<Enchantment> TRAIL_GLOW = EnchantmentConstructorHelper.key("trail_glow");
    /** [移动轨迹]幽匿：移动时留下幽匿粒子效果 */
    public static final ResourceKey<Enchantment> TRAIL_SCULK_SOUL = EnchantmentConstructorHelper.key("trail_sculk_soul");
    /** [移动轨迹]雪花：移动时留下雪花粒子效果 */
    public static final ResourceKey<Enchantment> TRAIL_SNOWFLAKE = EnchantmentConstructorHelper.key("trail_snowflake");
    /** [移动轨迹]试炼之兆：移动时留下试炼之兆粒子效果 */
    public static final ResourceKey<Enchantment> TRAIL_TRIAL_OMEN = EnchantmentConstructorHelper.key("trail_trial_omen");
    /** [移动轨迹]白色星星：移动时留下白色星星粒子效果 */
    public static final ResourceKey<Enchantment> TRAIL_WAX_OFF = EnchantmentConstructorHelper.key("trail_wax_off");
    /** [移动轨迹]橙色星星：移动时留下橙色星星粒子效果 */
    public static final ResourceKey<Enchantment> TRAIL_WAX_ON = EnchantmentConstructorHelper.key("trail_wax_on");

    private HeadArmorEnchantments() {
    }

    /**
     * [头盔]类型附魔
     * <p>
     * 只能附魔在头盔上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        adaptive(e);
        againstAllOdds(e);
        antiCamouflage(e);
        desperateCounter(e);
        luckyBoost(e);
        luckyLight(e);
        maximization(e);
        minify(e);
        trailCherryLeaves(e);
        trailDragonBreath(e);
        trailFirework(e);
        trailGlow(e);
        trailSculkSoul(e);
        trailSnowflake(e);
        trailTrialOmen(e);
        trailWaxOff(e);
        trailWaxOn(e);
    }

    /** 矿工：在低于Y0时获得夜视效果 */
    private static void adaptive(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:armors_head", "#redstone_enchants:armors_head", 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "head");
        e.register(builder, ADAPTIVE, 0xFFAA00, "adaptive");
    }

    /** 以寡敌众：根据周围敌人数目，每多一个敌人伤害/护甲+2% */
    private static void againstAllOdds(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:armors_head", "#redstone_enchants:armors_head", 2, 5,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "head");
        e.register(builder, AGAINST_ALL_ODDS, 0xFFAA00, "against_all_odds");
    }

    /** 反伪装：潜行时显示周围的敌对生物 */
    private static void antiCamouflage(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:armors_head", "#redstone_enchants:armors_head", 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "head");
        e.register(builder, ANTI_CAMOUFLAGE, 0xFF55FF, "anti_camouflage");
    }

    /** 绝境逆袭：自身拥有失明或黑暗时增加攻击伤害 */
    private static void desperateCounter(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:armors_head", "#redstone_enchants:armors_head", 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "head");
        e.register(builder, DESPERATE_COUNTER, 0xFF55FF, "desperate_counter");
    }

    /** 幸运增幅：提升幸运值 */
    private static void luckyBoost(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:armors_head", "#redstone_enchants:armors_head", 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "head");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/head_lucky"));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantencore.lucky_boost_1", e.attribute("minecraft:generic.luck"), EnchantmentConstructorHelper.operation("add_value"), linear(2f, 2.5f)));
        e.register(builder, LUCKY_BOOST, 0xFF55FF, "lucky_boost");
    }

    /** 幸运之光：获得幸运效果 */
    private static void luckyLight(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:armors_head", "#redstone_enchants:armors_head", 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "head");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/head_lucky"));
        e.entity(builder, EnchantmentEffectComponents.TICK, mobEffect(e.mobEffects("minecraft:luck"), constant(0.2f), constant(0.2f), linear(0f, 1f), linear(0f, 1f)), null);
        e.register(builder, LUCKY_LIGHT, 0xFF55FF, "lucky_light");
    }

    /** 巨大化：增大玩家体型 */
    private static void maximization(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:armors_head", "#redstone_enchants:armors_head", 1, 4,
                Enchantment.dynamicCost(18, 8), Enchantment.dynamicCost(48, 18), 16, "head");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/armors_head"));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.maximization_1", e.attribute("minecraft:generic.scale"), EnchantmentConstructorHelper.operation("add_multiplied_base"), lookup(new float[] { 0.15f, 0.25f, 0.35f, 0.5f }, linear(0.25f, 0.25f))));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.maximization_2", e.attribute("minecraft:generic.step_height"), EnchantmentConstructorHelper.operation("add_value"), linear(0.25f, 0.25f)));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.maximization_3", e.attribute("minecraft:generic.attack_damage"), EnchantmentConstructorHelper.operation("add_value"), linear(1f, 0.5f)));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.maximization_4", e.attribute("minecraft:generic.max_health"), EnchantmentConstructorHelper.operation("add_value"), linear(2.5f, 2.5f)));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.maximization_5", e.attribute("minecraft:player.entity_interaction_range"), EnchantmentConstructorHelper.operation("add_multiplied_total"), linear(0.15f, 0.1f)));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.maximization_6", e.attribute("minecraft:player.block_interaction_range"), EnchantmentConstructorHelper.operation("add_multiplied_total"), linear(0.15f, 0.1f)));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.maximization_7", e.attribute("minecraft:generic.movement_speed"), EnchantmentConstructorHelper.operation("add_value"), linear(-0.015f, -0.01f)));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.maximization_8", e.attribute("minecraft:generic.jump_strength"), EnchantmentConstructorHelper.operation("add_value"), linear(-0.04f, -0.02f)));
        e.register(builder, MAXIMIZATION, 0xFF00BB, "maximization");
    }

    /** 迷你化：缩小玩家体型 */
    private static void minify(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:armors_head", "#redstone_enchants:armors_head", 1, 4,
                Enchantment.dynamicCost(18, 8), Enchantment.dynamicCost(48, 18), 16, "head");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/armors_head"));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.minify_1", e.attribute("minecraft:generic.scale"), EnchantmentConstructorHelper.operation("add_multiplied_base"), lookup(new float[] { -0.15f, -0.25f, -0.35f, -0.5f }, linear(-0.15f, -0.15f))));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.minify_2", e.attribute("minecraft:generic.step_height"), EnchantmentConstructorHelper.operation("add_value"), linear(-0.25f, -0.25f)));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.minify_3", e.attribute("minecraft:generic.attack_damage"), EnchantmentConstructorHelper.operation("add_value"), linear(-1f, -0.5f)));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.minify_4", e.attribute("minecraft:generic.max_health"), EnchantmentConstructorHelper.operation("add_value"), linear(-2.5f, -2.5f)));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.minify_5", e.attribute("minecraft:player.entity_interaction_range"), EnchantmentConstructorHelper.operation("add_multiplied_total"), linear(-0.15f, -0.1f)));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.minify_6", e.attribute("minecraft:player.block_interaction_range"), EnchantmentConstructorHelper.operation("add_multiplied_total"), linear(-0.15f, -0.1f)));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.minify_7", e.attribute("minecraft:generic.movement_speed"), EnchantmentConstructorHelper.operation("add_value"), linear(0.015f, 0.01f)));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.minify_8", e.attribute("minecraft:generic.jump_strength"), EnchantmentConstructorHelper.operation("add_value"), linear(0.04f, 0.02f)));
        e.register(builder, MINIFY, 0xFF00BB, "minify");
    }

    /** [移动轨迹]樱花：移动时留下樱花花瓣粒子效果 */
    private static void trailCherryLeaves(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:armors_head", "#redstone_enchants:armors_head", 6, 1,
                Enchantment.dynamicCost(8, 4), Enchantment.dynamicCost(16, 8), 4, "head");
        e.register(builder, TRAIL_CHERRY_LEAVES, 0xFFFF55, "trail_cherry_leaves");
    }

    /** [移动轨迹]龙息：移动时留下龙息粒子效果 */
    private static void trailDragonBreath(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:armors_head", "#redstone_enchants:armors_head", 6, 1,
                Enchantment.dynamicCost(8, 4), Enchantment.dynamicCost(16, 8), 4, "head");
        e.register(builder, TRAIL_DRAGON_BREATH, 0xFFFF55, "trail_dragon_breath");
    }

    /** [移动轨迹]烟花火箭：移动时留下烟花火箭尾迹粒子效果 */
    private static void trailFirework(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:armors_head", "#redstone_enchants:armors_head", 6, 1,
                Enchantment.dynamicCost(8, 4), Enchantment.dynamicCost(16, 8), 4, "head");
        e.register(builder, TRAIL_FIREWORK, 0xFFFF55, "trail_firework");
    }

    /** [移动轨迹]荧光：移动时留下荧光粒子效果 */
    private static void trailGlow(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:armors_head", "#redstone_enchants:armors_head", 6, 1,
                Enchantment.dynamicCost(8, 4), Enchantment.dynamicCost(16, 8), 4, "head");
        e.register(builder, TRAIL_GLOW, 0xFFFF55, "trail_glow");
    }

    /** [移动轨迹]幽匿：移动时留下幽匿粒子效果 */
    private static void trailSculkSoul(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:armors_head", "#redstone_enchants:armors_head", 6, 1,
                Enchantment.dynamicCost(8, 4), Enchantment.dynamicCost(16, 8), 4, "head");
        e.register(builder, TRAIL_SCULK_SOUL, 0xFFFF55, "trail_sculk_soul");
    }

    /** [移动轨迹]雪花：移动时留下雪花粒子效果 */
    private static void trailSnowflake(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:armors_head", "#redstone_enchants:armors_head", 6, 1,
                Enchantment.dynamicCost(8, 4), Enchantment.dynamicCost(16, 8), 4, "head");
        e.register(builder, TRAIL_SNOWFLAKE, 0xFFFF55, "trail_snowflake");
    }

    /** [移动轨迹]试炼之兆：移动时留下试炼之兆粒子效果 */
    private static void trailTrialOmen(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:armors_head", "#redstone_enchants:armors_head", 6, 1,
                Enchantment.dynamicCost(8, 4), Enchantment.dynamicCost(16, 8), 4, "head");
        e.register(builder, TRAIL_TRIAL_OMEN, 0xFFFF55, "trail_trial_omen");
    }

    /** [移动轨迹]白色星星：移动时留下白色星星粒子效果 */
    private static void trailWaxOff(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:armors_head", "#redstone_enchants:armors_head", 6, 1,
                Enchantment.dynamicCost(8, 4), Enchantment.dynamicCost(16, 8), 4, "head");
        e.register(builder, TRAIL_WAX_OFF, 0xFFFF55, "trail_wax_off");
    }

    /** [移动轨迹]橙色星星：移动时留下橙色星星粒子效果 */
    private static void trailWaxOn(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:armors_head", "#redstone_enchants:armors_head", 6, 1,
                Enchantment.dynamicCost(8, 4), Enchantment.dynamicCost(16, 8), 4, "head");
        e.register(builder, TRAIL_WAX_ON, 0xFFFF55, "trail_wax_on");
    }

}
