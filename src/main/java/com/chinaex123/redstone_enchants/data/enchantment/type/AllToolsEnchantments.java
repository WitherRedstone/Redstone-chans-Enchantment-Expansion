package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;

import com.chinaex123.redstone_enchants.init.REBlockTags;
import com.chinaex123.redstone_enchants.init.REEnchantmentTags;
import com.chinaex123.redstone_enchants.init.REItemTags;
import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.*;

/**
 * [工具]类型附魔
 * <p>
 * 只能附魔在剑/镐/斧/锄/锹/弓/三叉戟/重锤/剪刀/打火石/刷子上
 */
public final class AllToolsEnchantments {
    /** 自动熔炼：挖掘的方块自动熔炼成对应成品 */
    public static final ResourceKey<Enchantment> AUTO_SMELT = EnchantmentConstructorHelper.key("auto_smelt");
    /** 基岩破坏者：允许破坏基岩(不产生掉落，消耗1.5K耐久) */
    public static final ResourceKey<Enchantment> BEDROCK_BREAKER = EnchantmentConstructorHelper.key("bedrock_breaker");
    /** 连锁效率：挖掘方块时挖掘周围同类型方块 */
    public static final ResourceKey<Enchantment> CHAIN_HASTE = EnchantmentConstructorHelper.key("chain_haste");
    /** 挖掘机：可以挖掘3x3/5x5/7x7区域的方块 */
    public static final ResourceKey<Enchantment> EXCAVATOR = EnchantmentConstructorHelper.key("excavator");
    /** 地质学：挖掘石头有概率掉落矿石 */
    public static final ResourceKey<Enchantment> GEOLOGY = EnchantmentConstructorHelper.key("geology");
    /** 点石成金：挖掘石头类方块有概率掉落金粒 */
    public static final ResourceKey<Enchantment> GOLDFINGER = EnchantmentConstructorHelper.key("goldfinger");
    /** 急速：手持时获得急迫效果 */
    public static final ResourceKey<Enchantment> HASTE = EnchantmentConstructorHelper.key("haste");
    /** 磁力：吸引附近的掉落物到身边 */
    public static final ResourceKey<Enchantment> MAGNET = EnchantmentConstructorHelper.key("magnet");
    /** 精通采集：挖掘矿石时有概率触发双倍掉落 */
    public static final ResourceKey<Enchantment> MASTER_GATHERER = EnchantmentConstructorHelper.key("master_gatherer");
    /** 湿润：犁过的地会湿润一会 */
    public static final ResourceKey<Enchantment> MOIST = EnchantmentConstructorHelper.key("moist");
    /** 幻岩转化：将基岩转化为强化深板岩(消耗1K耐久) */
    public static final ResourceKey<Enchantment> ROCK_ILLUSION = EnchantmentConstructorHelper.key("rock_illusion");
    /** 伐木：砍伐树木时，同时破坏其上方所有同种原木 */
    public static final ResourceKey<Enchantment> TIMBER = EnchantmentConstructorHelper.key("timber");
    /** 经验源泉：挖掘时获得额外经验 */
    public static final ResourceKey<Enchantment> XP_SPRING_BLOCK = EnchantmentConstructorHelper.key("xp_spring_block");

    private AllToolsEnchantments() {
    }

    /**
     * [工具]类型附魔
     * <p>
     * 只能附魔在镐/斧/锄/锹上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        autoSmelt(e);
        bedrockBreaker(e);
        chainHaste(e);
        excavator(e);
        geology(e);
        goldfinger(e);
        haste(e);
        magnet(e);
        masterGatherer(e);
        moist(e);
        rockIllusion(e);
        timber(e);
        xpSpringBlock(e);
    }

    /** 自动熔炼：挖掘的方块自动熔炼成对应成品 */
    private static void autoSmelt(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.TOOLS, REItemTags.TOOLS, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.register(builder, AUTO_SMELT, 0xFF55FF, "auto_smelt");
    }

    /** 基岩破坏者：允许破坏基岩(不产生掉落，消耗1.5K耐久) */
    private static void bedrockBreaker(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#minecraft:pickaxes", "#minecraft:pickaxes", 1, 1,
                Enchantment.dynamicCost(80, 50), Enchantment.dynamicCost(100, 50), 200, "mainhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_BEDROCK));
        e.entity(builder, EnchantmentEffectComponents.HIT_BLOCK, playSound(e.sound("minecraft:entity.generic.explode"), constantFloat(1f), constantFloat(2f)), null);
        e.entity(builder, EnchantmentEffectComponents.HIT_BLOCK, allEntities(particles(particle("minecraft:explosion_emitter"), position("in_bounding_box", 0f, 1f),
                position("in_bounding_box", 0f, 1f), velocity(0.1f, constantFloat(0.1f)), velocity(0.1f, constantFloat(0.1f)),
                constantFloat(0f)), playSound(e.sound("minecraft:entity.breeze.death"), constantFloat(0.1f), constantFloat(0.75f)), replaceBlock(vec3i(0, 0, 0), null,
                simpleState(state("minecraft:air")), e.gameEvent("minecraft:block_destroy")), damageItem(constant(1000f))), req(locationCheck(location().setBlock(locBlockBuilder(REBlockTags.BEDROCK_BREAKER)))));
        e.register(builder, BEDROCK_BREAKER, 0xFF00BB, "bedrock_breaker");
    }

    /** 连锁效率：挖掘方块时挖掘周围同类型方块 */
    private static void chainHaste(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.TOOLS, REItemTags.TOOLS, 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.register(builder, CHAIN_HASTE, 0xFF55FF, "chain_haste");
    }

    /** 挖掘机：可以挖掘3x3/5x5/7x7区域的方块 */
    private static void excavator(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.TOOLS, REItemTags.TOOLS, 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.register(builder, EXCAVATOR, 0xFF55FF, "excavator");
    }

    /** 地质学：挖掘石头有概率掉落矿石 */
    private static void geology(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#minecraft:pickaxes", "#minecraft:pickaxes", 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_STONE_TRANSMUTATION));
        e.register(builder, GEOLOGY, 0xFF55FF, "geology");
    }

    /** 点石成金：挖掘石头类方块有概率掉落金粒 */
    private static void goldfinger(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#minecraft:pickaxes", "#minecraft:pickaxes", 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_STONE_TRANSMUTATION));
        e.register(builder, GOLDFINGER, 0xFF55FF, "goldfinger");
    }

    /** 急速：手持时获得急迫效果 */
    private static void haste(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.TOOLS, REItemTags.TOOLS, 2, 3,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand");
        e.register(builder, HASTE, 0xFFAA00, "haste");
    }

    /** 磁力：吸引附近的掉落物到身边 */
    private static void magnet(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#minecraft:pickaxes", "#minecraft:pickaxes", 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.register(builder, MAGNET, 0xFF55FF, "magnet");
    }

    /** 精通采集：挖掘矿石时有概率触发双倍掉落 */
    private static void masterGatherer(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.TOOLS, REItemTags.TOOLS, 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.register(builder, MASTER_GATHERER, 0xFF55FF, "master_gatherer");
    }

    /** 湿润：犁过的地会湿润一会 */
    private static void moist(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(ItemTags.HOES, ItemTags.HOES, 3, 1,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "mainhand");
        e.register(builder, MOIST, 0xFF55FF, "moist");
    }

    /** 幻岩转化：将基岩转化为强化深板岩(消耗1K耐久) */
    private static void rockIllusion(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#minecraft:pickaxes", "#minecraft:pickaxes", 1, 1,
                Enchantment.dynamicCost(80, 50), Enchantment.dynamicCost(100, 50), 200, "mainhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_BEDROCK));
        e.entity(builder, EnchantmentEffectComponents.HIT_BLOCK, damageItem(constant(1000f)), blockStateProperty(e.block("minecraft:bedrock").value()));
        e.entity(builder, EnchantmentEffectComponents.HIT_BLOCK, replaceBlock(vec3i(0, 0, 0), e.blockPredicate("minecraft:bedrock", 0, 0, 0), simpleState(state("minecraft:reinforced_deepslate")), e.gameEvent("minecraft:block_destroy")), null);
        e.entity(builder, EnchantmentEffectComponents.HIT_BLOCK, particles(particle("minecraft:poof"), position("in_bounding_box", 0f, 1f), position("in_bounding_box", 0f, 1f), velocity(0f, constantFloat(0f)), velocity(0f, constantFloat(0f)), constantFloat(0.5f)), blockStateProperty(e.block("minecraft:bedrock").value()));
        e.register(builder, ROCK_ILLUSION, 0xFF00BB, "rock_illusion");
    }

    /** 伐木：砍伐树木时，同时破坏其上方所有同种原木 */
    private static void timber(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#minecraft:axes", "#minecraft:axes", 4, 1,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "mainhand");
        e.register(builder, TIMBER, 0x55FFFF, "timber");
    }

    /** 经验源泉：挖掘时获得额外经验 */
    private static void xpSpringBlock(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.TOOLS, REItemTags.TOOLS, 4, 3,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "mainhand");
        e.value(builder, EnchantmentEffectComponents.BLOCK_EXPERIENCE, multiply(linear(1.5f, 1f)), null);
        e.register(builder, XP_SPRING_BLOCK, 0x55FFFF, "xp_spring_block");
    }

}
