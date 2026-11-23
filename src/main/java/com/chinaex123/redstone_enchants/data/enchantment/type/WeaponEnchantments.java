package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;

import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.*;

/**
 * [武器]类型附魔
 * <p>
 * 只能附魔在剑/斧/弓/弩上
 */
public final class WeaponEnchantments {
    /** 背刺：从背后攻击时造成额外伤害，正面攻击则减少伤害 */
    public static final ResourceKey<Enchantment> BACKSTAB = EnchantmentConstructorHelper.key("backstab");
    /** 屠夫：击杀动物时增加其掉落物 */
    public static final ResourceKey<Enchantment> BUTCHER = EnchantmentConstructorHelper.key("butcher");
    /** 承诺：击中已经受伤的生物会造成更高的伤害 */
    public static final ResourceKey<Enchantment> COMMITTED = EnchantmentConstructorHelper.key("committed");
    /** 处决：对生命值低于25%的目标直接秒杀 */
    public static final ResourceKey<Enchantment> EXECUTION = EnchantmentConstructorHelper.key("execution");
    /** 驱魔：对悦灵/恼鬼/烈焰人/旋风人/女巫/唤魔者造成高额伤害 */
    public static final ResourceKey<Enchantment> EXORCISM = EnchantmentConstructorHelper.key("exorcism");
    /** 第一印象：对未攻击过的目标(包括横扫击中)造成额外伤害 */
    public static final ResourceKey<Enchantment> FIRST_IMPRESSION = EnchantmentConstructorHelper.key("first_impression");
    /** 影袭：攻击时有概率造成双倍伤害 */
    public static final ResourceKey<Enchantment> SHADOW_ASSAULT = EnchantmentConstructorHelper.key("shadow_assault");
    /** 沉重：减慢攻击速度同时增加攻击伤害 */
    public static final ResourceKey<Enchantment> WEIGHTED = EnchantmentConstructorHelper.key("weighted");
    /** 经验利刃：消耗15点经验值提升攻击伤害(需10级启动) */
    public static final ResourceKey<Enchantment> XP_BLADE = EnchantmentConstructorHelper.key("xp_blade");

    private WeaponEnchantments() {
    }

    /**
     * [武器]类型附魔
     * <p>
     * 只能附魔在剑上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        backstab(e);
        butcher(e);
        committed(e);
        execution(e);
        exorcism(e);
        firstImpression(e);
        shadowAssault(e);
        weighted(e);
        xpBlade(e);
    }

    /** 背刺：从背后攻击时造成额外伤害，正面攻击则减少伤害 */
    private static void backstab(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:swords_and_axes", "#redstone_enchants:swords", 2, 5,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand");
        e.register(builder, BACKSTAB, 0xFFAA00, "backstab");
    }

    /** 屠夫：击杀动物时增加其掉落物 */
    private static void butcher(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:swords_and_axes", "#redstone_enchants:swords", 4, 5,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "mainhand");
        e.register(builder, BUTCHER, 0x55FFFF, "butcher");
    }

    /** 承诺：击中已经受伤的生物会造成更高的伤害 */
    private static void committed(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:weapon", "#redstone_enchants:weapon", 2, 3,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/damage"));
        e.value(builder, EnchantmentEffectComponents.DAMAGE, multiply(linear(1.5f, 0.25f)), entityProperties("this", entity().nbt(nbt("{Tags:[\"harmed_by_committed\"]}"))));
        e.register(builder, COMMITTED, 0xFFAA00, "committed");
    }

    /** 处决：对生命值低于25%的目标直接秒杀 */
    private static void execution(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:swords_and_axes", "#redstone_enchants:swords", 2, 1,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand");
        e.register(builder, EXECUTION, 0xFFAA00, "execution");
    }

    /** 驱魔：对悦灵/恼鬼/烈焰人/旋风人/女巫/唤魔者造成高额伤害 */
    private static void exorcism(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:weapon", "#redstone_enchants:weapon", 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/damage_bane"));
        e.value(builder, EnchantmentEffectComponents.DAMAGE, add(linear(2.5f, 2.5f)), entityProperties("this", e.entityOf("#redstone_enchants:exorcism")));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:weakness,minecraft:slowness"), constant(1.5f), linear(3f, 0.5f), constant(1f), constant(1f)), allOf(entityProperties("this", e.entityOf("#redstone_enchants:exorcism")), damageSource(damageSourcePredicate().isDirect(true))));
        e.register(builder, EXORCISM, 0xFF55FF, "exorcism");
    }

    /** 第一印象：对未攻击过的目标(包括横扫击中)造成额外伤害 */
    private static void firstImpression(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:weapon", "#redstone_enchants:weapon", 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        e.value(builder, EnchantmentEffectComponents.DAMAGE, add(linear(5.0f, 2.5f)), inverted(entityProperties("this", entity().nbt(nbt("{Tags:['redstone_enchants.first_impression']}")))));
        e.register(builder, FIRST_IMPRESSION, 0xFF55FF, "first_impression");
    }

    /** 影袭：攻击时有概率造成双倍伤害 */
    private static void shadowAssault(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:weapon", "#redstone_enchants:weapon", 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/damage"));
        e.value(builder, EnchantmentEffectComponents.DAMAGE, multiply(constant(2f)), randomChance(constantNumber(0.33f)));
        e.value(builder, EnchantmentEffectComponents.DAMAGE, multiply(constant(2f)), allOf(randomChance(constantNumber(0.33f)), entityProperties("direct_attacker", e.entityOf("#redstone_enchants:projectiles"))));
        e.register(builder, SHADOW_ASSAULT, 0xFF55FF, "shadow_assault");
    }

    /** 沉重：减慢攻击速度同时增加攻击伤害 */
    private static void weighted(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:swords_and_axes", "#redstone_enchants:swords", 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.weighted_damage", e.attribute("minecraft:generic.attack_damage"), EnchantmentConstructorHelper.operation("add_multiplied_total"), linear(0.1f, 0.1f)));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.weighted_speed", e.attribute("minecraft:generic.attack_speed"), EnchantmentConstructorHelper.operation("add_multiplied_total"), linear(-0.05f, -0.05f)));
        e.register(builder, WEIGHTED, 0xFF55FF, "weighted");
    }

    /** 经验利刃：消耗15点经验值提升攻击伤害(需10级启动) */
    private static void xpBlade(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:weapon", "#redstone_enchants:weapon", 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/damage"));
        e.value(builder, EnchantmentEffectComponents.DAMAGE, add(linear(1f, 1.25f)), entityProperties("attacker", entity().subPredicate(player().setLevel(intsAtLeast(10)).build())));
        e.register(builder, XP_BLADE, 0xFF55FF, "xp_blade");
    }

}
