package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;

import com.chinaex123.redstone_enchants.init.REEnchantmentTags;
import com.chinaex123.redstone_enchants.init.REEntityTypeTags;
import com.chinaex123.redstone_enchants.init.REItemTags;
import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.*;

/**
 * [剑]类型附魔
 * <p>
 * 只能附魔在剑和斧子上
 */
public final class SwordsEnchantments {
    /** 伏击：潜行时首次攻击增加伤害 */
    public static final ResourceKey<Enchantment> AMBUSH = EnchantmentConstructorHelper.key("ambush");
    /** 微小克星：对小型生物造成多倍伤害 */
    public static final ResourceKey<Enchantment> BANE_BADY = EnchantmentConstructorHelper.key("bane_bady");
    /** Boss克星：对Boss生物造成额外伤害 */
    public static final ResourceKey<Enchantment> BANE_BOSS = EnchantmentConstructorHelper.key("bane_boss");
    /** 末地克星：对末地生物造成额外伤害(Boss除外) */
    public static final ResourceKey<Enchantment> BANE_END = EnchantmentConstructorHelper.key("bane_end");
    /** 灾厄克星：对袭击生物造成额外伤害 */
    public static final ResourceKey<Enchantment> BANE_ILLAGER = EnchantmentConstructorHelper.key("bane_illager");
    /** 下界克星：对下界生物造成额外伤害(Boss除外) */
    public static final ResourceKey<Enchantment> BANE_NETHER = EnchantmentConstructorHelper.key("bane_nether");
    /** 幻翼克星：对幻翼造成额外伤害 */
    public static final ResourceKey<Enchantment> BANE_PHANTOM = EnchantmentConstructorHelper.key("bane_phantom");
    /** 福佑：攻击时有概率获得随机一个正面效果 */
    public static final ResourceKey<Enchantment> BOONS = EnchantmentConstructorHelper.key("boons");
    /** 灾厄：攻击时有概率让目标获得随机一个负面效果 */
    public static final ResourceKey<Enchantment> CALAMITY = EnchantmentConstructorHelper.key("calamity");
    /** 锁链：击中生物会导致自身和附近的多个生物在短时间内被眩晕 */
    public static final ResourceKey<Enchantment> CHAINS = EnchantmentConstructorHelper.key("chains");
    /** 斩首：增加生物头颅的掉落概率 */
    public static final ResourceKey<Enchantment> DECAPITATION = EnchantmentConstructorHelper.key("decapitation");
    /** 吞噬：攻击生物时恢复饥饿值，满饥饿时转为生命恢复 */
    public static final ResourceKey<Enchantment> DEVOURING = EnchantmentConstructorHelper.key("devouring");
    /** 发电机：冲刺时增加攻击力 */
    public static final ResourceKey<Enchantment> DYNAMO = EnchantmentConstructorHelper.key("dynamo");
    /** 均衡器：伤害根据目标血量百分比变化；目标血量越高，伤害越高 */
    public static final ResourceKey<Enchantment> EQUALIZER = EnchantmentConstructorHelper.key("equalizer");
    /** 最后的希望：消耗武器对目标造成一击必杀效果(使用后武器消失) */
    public static final ResourceKey<Enchantment> LAST_HOPE = EnchantmentConstructorHelper.key("last_hope");
    /** 生命吸取：攻击时恢复造成伤害10%的生命值 */
    public static final ResourceKey<Enchantment> LIFE_STEAL = EnchantmentConstructorHelper.key("life_steal");
    /** 消解：攻击时有概率移除目标身上的一个正面效果 */
    public static final ResourceKey<Enchantment> NULLIFY = EnchantmentConstructorHelper.key("nullify");
    /** 抗击之盾：提升击退抗性 */
    public static final ResourceKey<Enchantment> RESILIENCE_SHIELD = EnchantmentConstructorHelper.key("resilience_shield");
    /** 影刺：攻击处于隐身或黑暗效果下的目标时，会造成多倍伤害 */
    public static final ResourceKey<Enchantment> SHADOW_PIERCE = EnchantmentConstructorHelper.key("shadow_pierce");
    /** 迅捷斩影：提升攻击速度 */
    public static final ResourceKey<Enchantment> SWIFT_SHADOWCUTTER = EnchantmentConstructorHelper.key("swift_shadowcutter");
    /** 流血之触：攻击时使目标流血 */
    public static final ResourceKey<Enchantment> TOUCH_BLEEDING = EnchantmentConstructorHelper.key("touch_bleeding");
    /** 毒触之刃：攻击时使目标中毒 */
    public static final ResourceKey<Enchantment> TOUCH_POISON = EnchantmentConstructorHelper.key("touch_poison");
    /** 凋零之触：攻击时使目标凋零 */
    public static final ResourceKey<Enchantment> TOUCH_WITHER = EnchantmentConstructorHelper.key("touch_wither");
    /** 越战越勇：攻击时获得急迫、力量和迅捷效果 */
    public static final ResourceKey<Enchantment> UNDER_PRESSURE = EnchantmentConstructorHelper.key("under_pressure");
    /** 暗流涌动：攻击时造成冰霜伤害并附加霜冻、凋零和虚弱效果 */
    public static final ResourceKey<Enchantment> UNDERCURRENT = EnchantmentConstructorHelper.key("undercurrent");
    /** 激发潜能：饥饿值低于3鸡腿时获得急迫、速度、力量(随饥饿值降低而增强) */
    public static final ResourceKey<Enchantment> UNLEASH_POTENTIAL = EnchantmentConstructorHelper.key("unleash_potential");
    /** 水瓶投射：攻击时向目标投掷水瓶 */
    public static final ResourceKey<Enchantment> WATER_BOTTLE_PROJECTION = EnchantmentConstructorHelper.key("water_bottle_projection");
    /** 经验收割者：击杀生物时获得额外经验(只支持直接击杀) */
    public static final ResourceKey<Enchantment> XP_REAPER_MOBS = EnchantmentConstructorHelper.key("xp_reaper_mobs");

    private SwordsEnchantments() {
    }

    /**
     * [剑]类型附魔
     * <p>
     * 只能附魔在剑和斧子上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        ambush(e);
        baneBady(e);
        baneBoss(e);
        baneEnd(e);
        baneIllager(e);
        baneNether(e);
        banePhantom(e);
        boons(e);
        calamity(e);
        chains(e);
        decapitation(e);
        devouring(e);
        dynamo(e);
        equalizer(e);
        lastHope(e);
        lifeSteal(e);
        nullify(e);
        resilienceShield(e);
        shadowPierce(e);
        swiftShadowcutter(e);
        touchBleeding(e);
        touchPoison(e);
        touchWither(e);
        underPressure(e);
        undercurrent(e);
        unleashPotential(e);
        waterBottleProjection(e);
        xpReaperMobs(e);
    }

    /** 伏击：潜行时首次攻击增加伤害 */
    private static void ambush(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.register(builder, AMBUSH, 0xFF55FF, "ambush");
    }

    /** 微小克星：对小型生物造成多倍伤害 */
    private static void baneBady(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_BANE));
        e.value(builder, EnchantmentEffectComponents.DAMAGE, multiply(linear(2.0f, 2.0f)), entityProperties("this", entity().flags(flags().setIsBaby(true))));
        e.register(builder, BANE_BADY, 0xFF55FF, "bane_bady");
    }

    /** Boss克星：对Boss生物造成额外伤害 */
    private static void baneBoss(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_BANE));
        e.value(builder, EnchantmentEffectComponents.DAMAGE, add(linear(2.5f, 2.5f)), entityProperties("this", e.entityOf(REEntityTypeTags.ENTITY_BOSS)));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:slowness"), constant(1.5f), linear(1.5f, 0.5f), constant(3f), constant(3f)), allOf(entityProperties("this", e.entityOf(REEntityTypeTags.ENTITY_BOSS)), damageSource(damageSourcePredicate().isDirect(true))));
        e.register(builder, BANE_BOSS, 0xFF55FF, "bane_boss");
    }

    /** 末地克星：对末地生物造成额外伤害(Boss除外) */
    private static void baneEnd(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_BANE));
        e.value(builder, EnchantmentEffectComponents.DAMAGE, add(linear(2.5f, 2.5f)), entityProperties("this", e.entityOf(REEntityTypeTags.ENTITY_END)));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:slowness"), constant(1.5f), linear(1.5f, 0.5f), constant(3f), constant(3f)), allOf(entityProperties("this", e.entityOf(REEntityTypeTags.ENTITY_END)), damageSource(damageSourcePredicate().isDirect(true))));
        e.register(builder, BANE_END, 0xFF55FF, "bane_end");
    }

    /** 灾厄克星：对袭击生物造成额外伤害 */
    private static void baneIllager(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_BANE));
        e.value(builder, EnchantmentEffectComponents.DAMAGE, add(linear(2.5f, 2.5f)), entityProperties("this", e.entityOf(REEntityTypeTags.ENTITY_ILLAGER)));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:slowness"), constant(1.5f), linear(1.5f, 0.5f), constant(3f), constant(3f)), allOf(entityProperties("this", e.entityOf(REEntityTypeTags.ENTITY_ILLAGER)), damageSource(damageSourcePredicate().isDirect(true))));
        e.register(builder, BANE_ILLAGER, 0xFF55FF, "bane_illager");
    }

    /** 下界克星：对下界生物造成额外伤害(Boss除外) */
    private static void baneNether(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_BANE));
        e.value(builder, EnchantmentEffectComponents.DAMAGE, add(linear(2.5f, 2.5f)), entityProperties("this", e.entityOf(REEntityTypeTags.ENTITY_NETHER)));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:slowness"), constant(1.5f), linear(1.5f, 0.5f), constant(3f), constant(3f)), allOf(entityProperties("this", e.entityOf(REEntityTypeTags.ENTITY_NETHER)), damageSource(damageSourcePredicate().isDirect(true))));
        e.register(builder, BANE_NETHER, 0xFF55FF, "bane_nether");
    }

    /** 幻翼克星：对幻翼造成额外伤害 */
    private static void banePhantom(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_BANE));
        e.value(builder, EnchantmentEffectComponents.DAMAGE, add(linear(2.5f, 2.5f)), req(anyOf(entityProperties("direct_attacker", e.entityOf("#minecraft:arrows")), entityProperties("this", e.entityOf("minecraft:phantom").flags(flags().setOnGround(false))))));
        e.register(builder, BANE_PHANTOM, 0xFF55FF, "bane_phantom");
    }

    /** 福佑：攻击时有概率获得随机一个正面效果 */
    private static void boons(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 2, 5,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand");
        e.register(builder, BOONS, 0xFFAA00, "boons");
    }

    /** 灾厄：攻击时有概率让目标获得随机一个负面效果 */
    private static void calamity(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 2, 5,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand");
        e.register(builder, CALAMITY, 0xFFAA00, "calamity");
    }

    /** 锁链：击中生物会导致自身和附近的多个生物在短时间内被眩晕 */
    private static void chains(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.register(builder, CHAINS, 0xFF55FF, "chains");
    }

    /** 斩首：增加生物头颅的掉落概率 */
    private static void decapitation(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 2, 5,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand");
        e.register(builder, DECAPITATION, 0xFFAA00, "decapitation");
    }

    /** 吞噬：攻击生物时恢复饥饿值，满饥饿时转为生命恢复 */
    private static void devouring(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 2, 1,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand");
        e.register(builder, DEVOURING, 0xFFAA00, "devouring");
    }

    /** 发电机：冲刺时增加攻击力 */
    private static void dynamo(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.value(builder, EnchantmentEffectComponents.DAMAGE, add(linear(1f, 1f)), entityProperties("direct_attacker", entity().flags(flags().setSprinting(true))));
        e.value(builder, EnchantmentEffectComponents.KNOCKBACK, add(linear(0.5f, 0.5f)), entityProperties("direct_attacker", entity().flags(flags().setSprinting(true))));
        e.register(builder, DYNAMO, 0x55FFFF, "dynamo");
    }

    /** 均衡器：伤害根据目标血量百分比变化；目标血量越高，伤害越高 */
    private static void equalizer(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.register(builder, EQUALIZER, 0xFF55FF, "equalizer");
    }

    /** 最后的希望：消耗武器对目标造成一击必杀效果(使用后武器消失) */
    private static void lastHope(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.LAST_HOPE_WEAPONS, REItemTags.LAST_HOPE_WEAPONS, 1, 1,
                Enchantment.dynamicCost(120, 100), Enchantment.dynamicCost(150, 120), 200, "mainhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_DAMAGE));
        e.value(builder, EnchantmentEffectComponents.DAMAGE, add(constant(2147483647f)), inverted(entityProperties("this", e.entityOf(REEntityTypeTags.BLACK_ENTITY))));
        e.register(builder, LAST_HOPE, 0xFF00BB, "last_hope");
    }

    /** 生命吸取：攻击时恢复造成伤害10%的生命值 */
    private static void lifeSteal(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 2, 5,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand");
        e.register(builder, LIFE_STEAL, 0xFFAA00, "life_steal");
    }

    /** 消解：攻击时有概率移除目标身上的一个正面效果 */
    private static void nullify(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 2, 5,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand");
        e.register(builder, NULLIFY, 0xFFAA00, "nullify");
    }

    /** 抗击之盾：提升击退抗性 */
    private static void resilienceShield(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 2, 4,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.resilience_shield_1", e.attribute("minecraft:generic.knockback_resistance"), EnchantmentConstructorHelper.operation("add_multiplied_base"), linear(0.2f, 0.1f)));
        e.register(builder, RESILIENCE_SHIELD, 0xFFAA00, "resilience_shield");
    }

    /** 影刺：攻击处于隐身或黑暗效果下的目标时，会造成多倍伤害 */
    private static void shadowPierce(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS, REItemTags.SWORDS, 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        e.value(builder, EnchantmentEffectComponents.DAMAGE, multiply(linear(1.5f, 1.5f)), anyOf(entityProperties("this", entity().effects(effects().and(e.mobEffect("minecraft:invisibility")))), entityProperties("this", entity().effects(effects().and(e.mobEffect("minecraft:darkness"))))));
        e.register(builder, SHADOW_PIERCE, 0xFF55FF, "shadow_pierce");
    }

    /** 迅捷斩影：提升攻击速度 */
    private static void swiftShadowcutter(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_DAMAGE));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.swift_shadowcutter_1", e.attribute("minecraft:generic.attack_speed"), EnchantmentConstructorHelper.operation("add_multiplied_base"), linear(0.05f, 0.15f)));
        e.register(builder, SWIFT_SHADOWCUTTER, 0xFF55FF, "swift_shadowcutter");
    }

    /** 流血之触：攻击时使目标流血 */
    private static void touchBleeding(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_TOUCH));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("apothic_attributes:bleeding"), constant(4f), constant(6f), constant(0f), linear(0f, 1f)), null);
        e.register(builder, TOUCH_BLEEDING, 0xFF55FF, "touch_bleeding");
    }

    /** 毒触之刃：攻击时使目标中毒 */
    private static void touchPoison(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_TOUCH));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:poison"), constant(4f), constant(6f), constant(0f), linear(0f, 1f)), null);
        e.register(builder, TOUCH_POISON, 0xFF55FF, "touch_poison");
    }

    /** 凋零之触：攻击时使目标凋零 */
    private static void touchWither(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_TOUCH));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:wither"), constant(4f), constant(6f), constant(0f), linear(0f, 1f)), null);
        e.register(builder, TOUCH_WITHER, 0xFF55FF, "touch_wither");
    }

    /** 越战越勇：攻击时获得急迫、力量和迅捷效果 */
    private static void underPressure(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 2, 3,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_DAMAGE));
        e.postAttack(builder, target("attacker"), target("attacker"), mobEffect(e.mobEffects("minecraft:strength,minecraft:haste,minecraft:speed"), constant(5f), constant(5f), constant(0f), linear(0f, 1f)), null);
        e.register(builder, UNDER_PRESSURE, 0xFFAA00, "under_pressure");
    }

    /** 暗流涌动：攻击时造成冰霜伤害并附加霜冻、凋零和虚弱效果 */
    private static void undercurrent(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 2, 3,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_DAMAGE));
        e.postAttack(builder, target("attacker"), target("victim"), allEntities(mobEffect(e.mobEffects("twilightforest:frosted"), constant(5f), constant(10f), constant(0f), linear(0f, 1f)), mobEffect(e.mobEffects("minecraft:wither"), constant(5f), constant(10f), constant(0f), linear(0f, 1f)), mobEffect(e.mobEffects("minecraft:weakness"), constant(5f), constant(10f), constant(0f), linear(0f, 1f)), playSound(e.sound("minecraft:entity.player.hurt_freeze"), constantFloat(5.0f), uniformFloat(0.6f, 0.8f)), particles(particle("minecraft:snowflake"), position("in_bounding_box", 0f, 1f), position("in_bounding_box", 0.5f, 1f), velocity(0f, constantFloat(0f)), velocity(0f, constantFloat(0f)), constantFloat(1f)), particles(particle("minecraft:snowflake"), position("in_bounding_box", 0f, 1f), position("in_bounding_box", 0.5f, 1f), velocity(0f, constantFloat(0f)), velocity(0f, constantFloat(0f)), constantFloat(1f)), particles(particle("minecraft:snowflake"), position("in_bounding_box", 0f, 1f), position("in_bounding_box", 0.5f, 1f), velocity(0f, constantFloat(0f)), velocity(0f, constantFloat(0f)), constantFloat(1f))), null);
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.undercurrent_1", e.attribute("apothic_attributes:cold_damage"), EnchantmentConstructorHelper.operation("add_value"), linear(2f, 1.5f)));
        e.register(builder, UNDERCURRENT, 0xFF55FF, "undercurrent");
    }

    /** 激发潜能：饥饿值低于3鸡腿时获得急迫、速度、力量(随饥饿值降低而增强) */
    private static void unleashPotential(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 2, 1,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_DAMAGE));
        e.register(builder, UNLEASH_POTENTIAL, 0xFFAA00, "unleash_potential");
    }

    /** 水瓶投射：攻击时向目标投掷水瓶 */
    private static void waterBottleProjection(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 6, 2,
                Enchantment.dynamicCost(8, 4), Enchantment.dynamicCost(16, 8), 4, "mainhand");
        e.register(builder, WATER_BOTTLE_PROJECTION, 0xFFFF55, "water_bottle_projection");
    }

    /** 经验收割者：击杀生物时获得额外经验(只支持直接击杀) */
    private static void xpReaperMobs(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.SWORDS_AND_AXES, REItemTags.SWORDS, 4, 3,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "mainhand");
        e.value(builder, EnchantmentEffectComponents.MOB_EXPERIENCE, multiply(linear(1.5f, 1f)), null);
        e.register(builder, XP_REAPER_MOBS, 0x55FFFF, "xp_reaper_mobs");
    }

}
