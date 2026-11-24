package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;

import com.chinaex123.redstone_enchants.init.REEnchantmentTags;
import com.chinaex123.redstone_enchants.init.REItemTags;
import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.*;

/**
 * [靴子]类型附魔
 * <p>
 * 只能附魔在靴子上
 */
public final class FootArmorEnchantments {
    /** 燃烧光环：移动过程中，自身的2格范围内产生火焰(同时获得火焰抗性) */
    public static final ResourceKey<Enchantment> AURA_BURNING = EnchantmentConstructorHelper.key("aura_burning");
    /** 发光光环：移动过程中，自身的4格范围内产生发光效果 */
    public static final ResourceKey<Enchantment> AURA_GLOWING = EnchantmentConstructorHelper.key("aura_glowing");
    /** 急迫光环：移动过程中，自身的4格范围内产生急迫效果 */
    public static final ResourceKey<Enchantment> AURA_HASTE = EnchantmentConstructorHelper.key("aura_haste");
    /** 寄生光环：移动过程中，自身的4格范围内产生寄生效果 */
    public static final ResourceKey<Enchantment> AURA_INFESTED = EnchantmentConstructorHelper.key("aura_infested");
    /** 跳跃光环：移动过程中，自身的4格范围内产生跳跃提升效果 */
    public static final ResourceKey<Enchantment> AURA_JUMP_BOOST = EnchantmentConstructorHelper.key("aura_jump_boost");
    /** 中毒光环：移动过程中，自身的4格范围内产生中毒效果 */
    public static final ResourceKey<Enchantment> AURA_POISON = EnchantmentConstructorHelper.key("aura_poison");
    /** 生命光环：移动过程中，自身的4格范围内产生生命恢复效果 */
    public static final ResourceKey<Enchantment> AURA_REGENERATION = EnchantmentConstructorHelper.key("aura_regeneration");
    /** 抗性光环：移动过程中，自身的4格范围内产生抗性提升效果 */
    public static final ResourceKey<Enchantment> AURA_RESISTANCE = EnchantmentConstructorHelper.key("aura_resistance");
    /** 缓慢光环：移动过程中，自身的4格范围内产生缓慢效果 */
    public static final ResourceKey<Enchantment> AURA_SLOWNESS = EnchantmentConstructorHelper.key("aura_slowness");
    /** 迅捷光环：移动过程中，自身的4格范围内产生迅捷效果 */
    public static final ResourceKey<Enchantment> AURA_SPEED = EnchantmentConstructorHelper.key("aura_speed");
    /** 力量光环：移动过程中，自身的4格范围内产生力量效果 */
    public static final ResourceKey<Enchantment> AURA_STRENGTH = EnchantmentConstructorHelper.key("aura_strength");
    /** 虚弱光环：移动过程中，自身的4格范围内产生虚弱效果 */
    public static final ResourceKey<Enchantment> AURA_WEAKNESS = EnchantmentConstructorHelper.key("aura_weakness");
    /** 凋零光环：移动过程中，自身的4格范围内产生凋零效果 */
    public static final ResourceKey<Enchantment> AURA_WITHER = EnchantmentConstructorHelper.key("aura_wither");
    /** 庄稼舞：蹲下时走过农田会加速周围作物生长 */
    public static final ResourceKey<Enchantment> CROP_DANCE = EnchantmentConstructorHelper.key("crop_dance");
    /** 踏焰者：可在岩浆上行走，潜行时下潜到岩浆下 */
    public static final ResourceKey<Enchantment> FLAME_WALKER = EnchantmentConstructorHelper.key("flame_walker");
    /** 疾驰：提升奔跑速度 */
    public static final ResourceKey<Enchantment> GALLOP = EnchantmentConstructorHelper.key("gallop");
    /** 末路疾行：末地维度获得速度加成 */
    public static final ResourceKey<Enchantment> GALLOP_END = EnchantmentConstructorHelper.key("gallop_end");
    /** 沙地疾行：提升沙地移动速度 */
    public static final ResourceKey<Enchantment> GALLOP_SAND = EnchantmentConstructorHelper.key("gallop_sand");
    /** 弹跳强化：增加跳跃高度 */
    public static final ResourceKey<Enchantment> JUMP_AMPLIFIER = EnchantmentConstructorHelper.key("jump_amplifier");
    /** 飞马座：骑乘时获得缓降效果 */
    public static final ResourceKey<Enchantment> PEGASUS = EnchantmentConstructorHelper.key("pegasus");
    /** 草皮行者：行走时将泥土变为草方块 */
    public static final ResourceKey<Enchantment> WALKER_GRASS = EnchantmentConstructorHelper.key("walker_grass");
    /** 熔岩行者：行走时生成将熔岩变为玄武岩 */
    public static final ResourceKey<Enchantment> WALKER_MAGMA = EnchantmentConstructorHelper.key("walker_magma");
    /** 融雪行者：行走时将雪融化 */
    public static final ResourceKey<Enchantment> WALKER_SNOWMELT = EnchantmentConstructorHelper.key("walker_snowmelt");
    /** 踏浪者：可在水面行走，潜行时下潜到水下 */
    public static final ResourceKey<Enchantment> WAVE_WALKER = EnchantmentConstructorHelper.key("wave_walker");

    private FootArmorEnchantments() {
    }

    /**
     * [靴子]类型附魔
     * <p>
     * 只能附魔在靴子上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        auraBurning(e);
        auraGlowing(e);
        auraHaste(e);
        auraInfested(e);
        auraJumpBoost(e);
        auraPoison(e);
        auraRegeneration(e);
        auraResistance(e);
        auraSlowness(e);
        auraSpeed(e);
        auraStrength(e);
        auraWeakness(e);
        auraWither(e);
        cropDance(e);
        flameWalker(e);
        gallop(e);
        gallopEnd(e);
        gallopSand(e);
        jumpAmplifier(e);
        pegasus(e);
        walkerGrass(e);
        walkerMagma(e);
        walkerSnowmelt(e);
        waveWalker(e);
    }

    /** 燃烧光环：移动过程中，自身的2格范围内产生火焰(同时获得火焰抗性) */
    private static void auraBurning(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 4, 1,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "feet");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_AURA));
        e.register(builder, AURA_BURNING, 0x55FFFF, "aura_burning");
    }

    /** 发光光环：移动过程中，自身的4格范围内产生发光效果 */
    private static void auraGlowing(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_AURA));
        e.register(builder, AURA_GLOWING, 0xFF55FF, "aura_glowing");
    }

    /** 急迫光环：移动过程中，自身的4格范围内产生急迫效果 */
    private static void auraHaste(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_AURA));
        e.register(builder, AURA_HASTE, 0xFF55FF, "aura_haste");
    }

    /** 寄生光环：移动过程中，自身的4格范围内产生寄生效果 */
    private static void auraInfested(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_AURA));
        e.register(builder, AURA_INFESTED, 0xFF55FF, "aura_infested");
    }

    /** 跳跃光环：移动过程中，自身的4格范围内产生跳跃提升效果 */
    private static void auraJumpBoost(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_AURA));
        e.register(builder, AURA_JUMP_BOOST, 0xFF55FF, "aura_jump_boost");
    }

    /** 中毒光环：移动过程中，自身的4格范围内产生中毒效果 */
    private static void auraPoison(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_AURA));
        e.register(builder, AURA_POISON, 0xFF55FF, "aura_poison");
    }

    /** 生命光环：移动过程中，自身的4格范围内产生生命恢复效果 */
    private static void auraRegeneration(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_AURA));
        e.register(builder, AURA_REGENERATION, 0xFF55FF, "aura_regeneration");
    }

    /** 抗性光环：移动过程中，自身的4格范围内产生抗性提升效果 */
    private static void auraResistance(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_AURA));
        e.register(builder, AURA_RESISTANCE, 0xFF55FF, "aura_resistance");
    }

    /** 缓慢光环：移动过程中，自身的4格范围内产生缓慢效果 */
    private static void auraSlowness(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_AURA));
        e.register(builder, AURA_SLOWNESS, 0xFF55FF, "aura_slowness");
    }

    /** 迅捷光环：移动过程中，自身的4格范围内产生迅捷效果 */
    private static void auraSpeed(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_AURA));
        e.register(builder, AURA_SPEED, 0xFF55FF, "aura_speed");
    }

    /** 力量光环：移动过程中，自身的4格范围内产生力量效果 */
    private static void auraStrength(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_AURA));
        e.register(builder, AURA_STRENGTH, 0xFF55FF, "aura_strength");
    }

    /** 虚弱光环：移动过程中，自身的4格范围内产生虚弱效果 */
    private static void auraWeakness(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_AURA));
        e.register(builder, AURA_WEAKNESS, 0xFF55FF, "aura_weakness");
    }

    /** 凋零光环：移动过程中，自身的4格范围内产生凋零效果 */
    private static void auraWither(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_AURA));
        e.register(builder, AURA_WITHER, 0xFF55FF, "aura_wither");
    }

    /** 庄稼舞：蹲下时走过农田会加速周围作物生长 */
    private static void cropDance(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 1, 1,
                Enchantment.dynamicCost(18, 8), Enchantment.dynamicCost(48, 18), 16, "feet");
        e.register(builder, CROP_DANCE, 0xFF00BB, "crop_dance");
    }

    /** 踏焰者：可在岩浆上行走，潜行时下潜到岩浆下 */
    private static void flameWalker(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        builder = builder.exclusiveWith(e.enchantments("minecraft:depth_strider"));
        e.register(builder, FLAME_WALKER, 0xFF55FF, "flame_walker");
    }

    /** 疾驰：提升奔跑速度 */
    private static void gallop(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_BOOTS_GALLOP));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.gallop_1", e.attribute("minecraft:generic.movement_speed"), EnchantmentConstructorHelper.operation("add_multiplied_base"), linear(0.2f, 0.2f)));
        e.register(builder, GALLOP, 0xFF55FF, "gallop");
    }

    /** 末路疾行：末地维度获得速度加成 */
    private static void gallopEnd(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_BOOTS_GALLOP));
        e.location(builder, EnchantmentEffectComponents.LOCATION_CHANGED, attributeEffect("minecraft:enchantment.soul_speed", e.attribute("minecraft:generic.movement_speed"), EnchantmentConstructorHelper.operation("add_value"), linear(0.0405f, 0.0105f)), allOf(inverted(entityProperties("this", entity().vehicle(entity()))), anyOf(allOf(enchantmentActive(true), entityProperties("this", entity().flags(flags().setIsFlying(false))), anyOf(entityProperties("this", entity().movementAffectedBy(location().setDimension(dimension("minecraft:the_end")))), entityProperties("this", entity().flags(flags().setOnGround(false))))), allOf(enchantmentActive(false), entityProperties("this", entity().flags(flags().setIsFlying(false)).movementAffectedBy(location().setDimension(dimension("minecraft:the_end"))))))));
        e.location(builder, EnchantmentEffectComponents.LOCATION_CHANGED, attributeEffect("minecraft:enchantment.soul_speed", e.attribute("minecraft:generic.movement_efficiency"), EnchantmentConstructorHelper.operation("add_value"), constant(1f)), entityProperties("this", entity().movementAffectedBy(location().setDimension(dimension("minecraft:the_end")))));
        e.location(builder, EnchantmentEffectComponents.LOCATION_CHANGED, damageItem(constant(1f)), allOf(randomChance(levelNumber(constant(0.04f))), entityProperties("this", entity().flags(flags().setOnGround(true)).movementAffectedBy(location().setDimension(dimension("minecraft:the_end"))))));
        e.entity(builder, EnchantmentEffectComponents.TICK, particles(particle("minecraft:soul"), position("in_bounding_box", 0f, 1f), position("entity_position", 0.1f, 1f), velocity(-0.2f, constantFloat(0f)), velocity(0f, constantFloat(0.1f)), constantFloat(1f)), entityProperties("this", entity().flags(flags().setOnGround(true).setIsFlying(false)).moving(allMovements(horizontalSpeed(atLeast(9.999999747378752e-06f)))).movementAffectedBy(location().setDimension(dimension("minecraft:the_end"))).periodicTick(5)));
        e.entity(builder, EnchantmentEffectComponents.TICK, playSound(e.sound("minecraft:particle.soul_escape"), constantFloat(0.6f), uniformFloat(0.6f, 1f)), allOf(randomChance(constantNumber(0.35f)), entityProperties("this", entity().flags(flags().setOnGround(true).setIsFlying(false)).moving(allMovements(horizontalSpeed(atLeast(9.999999747378752e-06f)))).movementAffectedBy(location().setDimension(dimension("minecraft:the_end"))).periodicTick(5))));
        e.register(builder, GALLOP_END, 0xFF55FF, "gallop_end");
    }

    /** 沙地疾行：提升沙地移动速度 */
    private static void gallopSand(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_BOOTS_GALLOP));
        e.location(builder, EnchantmentEffectComponents.LOCATION_CHANGED, attributeEffect("redstone_enchants:enchantment.gallop_sand_1", e.attribute("minecraft:generic.movement_speed"), EnchantmentConstructorHelper.operation("add_value"), linear(0.0405f, 0.0105f)), allOf(inverted(entityProperties("this", entity().vehicle(entity()))), anyOf(allOf(enchantmentActive(true), entityProperties("this", entity().flags(flags().setIsFlying(false))), anyOf(entityProperties("this", entity().movementAffectedBy(location().setBlock(locBlockBuilder("#minecraft:sand")))), entityProperties("this", entity().flags(flags().setOnGround(false))))), allOf(enchantmentActive(false), entityProperties("this", entity().flags(flags().setIsFlying(false)).movementAffectedBy(location().setBlock(locBlockBuilder("#minecraft:sand"))))))));
        e.location(builder, EnchantmentEffectComponents.LOCATION_CHANGED, attributeEffect("redstone_enchants:enchantment.gallop_sand_2", e.attribute("minecraft:generic.movement_efficiency"), EnchantmentConstructorHelper.operation("add_value"), constant(1f)), entityProperties("this", entity().movementAffectedBy(location().setBlock(locBlockBuilder("#minecraft:sand")))));
        e.location(builder, EnchantmentEffectComponents.LOCATION_CHANGED, damageItem(constant(1f)), allOf(randomChance(levelNumber(constant(0.04f))), entityProperties("this", entity().flags(flags().setOnGround(true)).movementAffectedBy(location().setBlock(locBlockBuilder("#minecraft:sand"))))));
        e.entity(builder, EnchantmentEffectComponents.TICK, particles(dust(0f, 0f, 0f, 0.01f), position("in_bounding_box", 0f, 1f), position("entity_position", 0.1f, 1f), velocity(-0.2f, constantFloat(0f)), velocity(0f, constantFloat(0.1f)), constantFloat(1f)), entityProperties("this", entity().flags(flags().setOnGround(true).setIsFlying(false)).moving(allMovements(horizontalSpeed(atLeast(1e-05f)))).movementAffectedBy(location().setBlock(locBlockBuilder("#minecraft:sand"))).periodicTick(5)));
        e.register(builder, GALLOP_SAND, 0xFF55FF, "gallop_sand");
    }

    /** 弹跳强化：增加跳跃高度 */
    private static void jumpAmplifier(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        e.attributes(builder, attributeEffect("minecraft:enchantment.leaping_jump_1", e.attribute("minecraft:generic.jump_strength"), EnchantmentConstructorHelper.operation("add_multiplied_total"), linear(0.15f, 0.25f)));
        e.attributes(builder, attributeEffect("minecraft:enchantment.leaping_jump_2", e.attribute("minecraft:generic.safe_fall_distance"), EnchantmentConstructorHelper.operation("add_multiplied_total"), linear(0.15f, 0.25f)));
        e.register(builder, JUMP_AMPLIFIER, 0xFF55FF, "jump_amplifier");
    }

    /** 飞马座：骑乘时获得缓降效果 */
    private static void pegasus(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        e.register(builder, PEGASUS, 0xFF55FF, "pegasus");
    }

    /** 草皮行者：行走时将泥土变为草方块 */
    private static void walkerGrass(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 2,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_WALKER));
        e.immunity(builder, damageSource(damageSourcePredicate().tag(damageTag("minecraft:burn_from_stepping", true)).tag(damageTag("minecraft:bypasses_invulnerability", false))));
        e.location(builder, EnchantmentEffectComponents.LOCATION_CHANGED, replaceDisk(clamped(linear(3f, 1f), 0f, 16f), constant(1f), vec3i(0, -1, 0), allBlockPredicates(e.blockPredicate("minecraft:air", 0, 1, 0), e.blockPredicate("minecraft:dirt", 0, 0, 0), unobstructed(0, 0, 0)), simpleState(state("minecraft:grass_block")), e.gameEvent("minecraft:block_place")), entityProperties("this", entity().flags(flags().setOnGround(true))));
        e.location(builder, EnchantmentEffectComponents.LOCATION_CHANGED, particles(dust(0.016f, 0.761f, 0.114f, 1f), position("entity_position", 0f, 1f), position("entity_position", 0f, 1f), velocity(0f, constantFloat(0f)), velocity(0f, constantFloat(0f)), constantFloat(1f)), null);
        e.register(builder, WALKER_GRASS, 0xFF55FF, "walker_grass");
    }

    /** 熔岩行者：行走时生成将熔岩变为玄武岩 */
    private static void walkerMagma(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 2,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_WALKER));
        e.immunity(builder, damageSource(damageSourcePredicate().tag(damageTag("minecraft:burn_from_stepping", true)).tag(damageTag("minecraft:bypasses_invulnerability", false))));
        e.location(builder, EnchantmentEffectComponents.LOCATION_CHANGED, replaceDisk(clamped(linear(3.0f, 1.0f), 0.0f, 16.0f), constant(1.0f), vec3i(0, -1, 0), allBlockPredicates(e.blockPredicate("minecraft:air", 0, 1, 0), e.fluidPredicate("minecraft:lava", 0, 0, 0), unobstructed(0, 0, 0)), weightedState(weightedStates().add(state("minecraft:basalt"), 9)), null), entityProperties("this", entity().flags(flags().setOnGround(true))));
        e.register(builder, WALKER_MAGMA, 0xFF55FF, "walker_magma");
    }

    /** 融雪行者：行走时将雪融化 */
    private static void walkerSnowmelt(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 2,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_WALKER));
        e.immunity(builder, damageSource(damageSourcePredicate().tag(damageTag("minecraft:burn_from_stepping", true)).tag(damageTag("minecraft:bypasses_invulnerability", false))));
        e.location(builder, EnchantmentEffectComponents.LOCATION_CHANGED, replaceDisk(clamped(linear(3f, 1f), 0f, 16f), constant(1f), vec3i(0, 0, 0), allBlockPredicates(e.blockPredicate("minecraft:air", 0, 1, 0), e.blockPredicate("minecraft:snow", 0, 0, 0), unobstructed(0, 0, 0)), simpleState(state("minecraft:air")), e.gameEvent("minecraft:block_place")), entityProperties("this", entity().flags(flags().setOnGround(true))));
        e.location(builder, EnchantmentEffectComponents.LOCATION_CHANGED, particles(dust(1.0f, 0.914f, 0.149f, 1f), position("entity_position", 0f, 1f), position("entity_position", 0f, 1f), velocity(0f, constantFloat(0f)), velocity(0f, constantFloat(0f)), constantFloat(1f)), null);
        e.register(builder, WALKER_SNOWMELT, 0xFF55FF, "walker_snowmelt");
    }

    /** 踏浪者：可在水面行走，潜行时下潜到水下 */
    private static void waveWalker(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "feet");
        builder = builder.exclusiveWith(e.enchantments("minecraft:depth_strider"));
        e.register(builder, WAVE_WALKER, 0xFF55FF, "wave_walker");
    }

}
