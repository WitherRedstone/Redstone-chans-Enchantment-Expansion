package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;

import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.*;

/**
 * [弓]类型附魔
 * <p>
 * 只能附魔在弓上
 */
public final class AllBowEnchantments {
    /** 精准射击：箭矢无视重力直飞目标，未命中会消失 */
    public static final ResourceKey<Enchantment> ACCURACY_SHOT = EnchantmentConstructorHelper.key("accuracy_shot");
    /** 爆裂箭矢：命中产生爆炸伤害 */
    public static final ResourceKey<Enchantment> BLAST_ARROWS = EnchantmentConstructorHelper.key("blast_arrows");
    /** 爆炸箭：命中产生强力爆炸和击退 */
    public static final ResourceKey<Enchantment> BOMB_ARROWS = EnchantmentConstructorHelper.key("bomb_arrows");
    /** 子弹时间：移动时获得缓降效果 */
    public static final ResourceKey<Enchantment> BULLET_TIME = EnchantmentConstructorHelper.key("bullet_time");
    /** 连锁反应：受伤的生物有几率使它们向附近的生物发射箭矢 */
    public static final ResourceKey<Enchantment> CHAIN_REACTION = EnchantmentConstructorHelper.key("chain_reaction");
    /** 绝命箭矢：一击必杀非Boss生物(无掉落战利品，使用后给予DeBuff) */
    public static final ResourceKey<Enchantment> FATAL_ARROW = EnchantmentConstructorHelper.key("fatal_arrow");
    /** 火焰箭：命中区域产生火焰效果 */
    public static final ResourceKey<Enchantment> FIRE_ARROWS = EnchantmentConstructorHelper.key("fire_arrows");
    /** 焦点：减少每支箭的散布 */
    public static final ResourceKey<Enchantment> FOCUS = EnchantmentConstructorHelper.key("focus");
    /** 玻璃破坏者：命中区域破坏玻璃 */
    public static final ResourceKey<Enchantment> GLASS_BREAKER = EnchantmentConstructorHelper.key("glass_breaker");
    /** 冰霜箭：命中区域产生冻结效果，有水则冻结成冰 */
    public static final ResourceKey<Enchantment> ICE_ARROWS = EnchantmentConstructorHelper.key("ice_arrows");
    /** 石锥雨：命中区域生成滴水石锥攻击 */
    public static final ResourceKey<Enchantment> RAIN_DRIPSTONE = EnchantmentConstructorHelper.key("rain_dripstone");
    /** 铁砧雨：命中区域生成铁砧攻击 */
    public static final ResourceKey<Enchantment> RAIN_FORGE = EnchantmentConstructorHelper.key("rain_forge");
    /** 速射：增加拉弓速度 */
    public static final ResourceKey<Enchantment> RAPID = EnchantmentConstructorHelper.key("rapid");
    /** 跳弹：击中一个生物会让箭从生物身上反弹并击中附近的另一个生物 */
    public static final ResourceKey<Enchantment> RICOCHET = EnchantmentConstructorHelper.key("ricochet");
    /** 分散：增加每支箭的散布 */
    public static final ResourceKey<Enchantment> SCATTER = EnchantmentConstructorHelper.key("scatter");
    /** 霰弹枪：一次射出多支箭矢 */
    public static final ResourceKey<Enchantment> SHOTGUN = EnchantmentConstructorHelper.key("shotgun");
    /** 狙击：与目标距离每增加10格，造成的伤害提高 */
    public static final ResourceKey<Enchantment> SNIPE = EnchantmentConstructorHelper.key("snipe");
    /** 失明蔓延：命中目标施加失明效果并生成区域效果云 */
    public static final ResourceKey<Enchantment> SPLASH_BLINDNESS = EnchantmentConstructorHelper.key("splash_blindness");
    /** 延迟爆破：箭矢命中生物后让其延迟爆炸 */
    public static final ResourceKey<Enchantment> SPLASH_DELAYED_EXPLOSION = EnchantmentConstructorHelper.key("splash_delayed_explosion");
    /** 发光蔓延：命中目标施加发光效果并生成区域效果云 */
    public static final ResourceKey<Enchantment> SPLASH_GLOWING = EnchantmentConstructorHelper.key("splash_glowing");
    /** 饥饿蔓延：命中目标施加饥饿效果并生成区域效果云 */
    public static final ResourceKey<Enchantment> SPLASH_HUNGER = EnchantmentConstructorHelper.key("splash_hunger");
    /** 寄生蔓延：命中目标施加寄生效果并生成区域效果云 */
    public static final ResourceKey<Enchantment> SPLASH_INFESTED = EnchantmentConstructorHelper.key("splash_infested");
    /** 渗浆蔓延：命中目标施加渗浆效果并生成区域效果云 */
    public static final ResourceKey<Enchantment> SPLASH_OOZING = EnchantmentConstructorHelper.key("splash_oozing");
    /** 中毒蔓延：命中目标施加中毒效果并生成区域效果云 */
    public static final ResourceKey<Enchantment> SPLASH_POISON = EnchantmentConstructorHelper.key("splash_poison");
    /** 治疗蔓延：命中目标施加生命恢复效果并生成区域效果云 */
    public static final ResourceKey<Enchantment> SPLASH_REGENERATION = EnchantmentConstructorHelper.key("splash_regeneration");
    /** 缓降蔓延：命中目标施加缓降效果并生成区域效果云 */
    public static final ResourceKey<Enchantment> SPLASH_SLOW_FALLING = EnchantmentConstructorHelper.key("splash_slow_falling");
    /** 缓慢蔓延：命中目标施加缓慢效果并生成区域效果云 */
    public static final ResourceKey<Enchantment> SPLASH_SLOWNESS = EnchantmentConstructorHelper.key("splash_slowness");
    /** 迅捷蔓延：命中目标施加速度效果并生成区域效果云 */
    public static final ResourceKey<Enchantment> SPLASH_SPEED = EnchantmentConstructorHelper.key("splash_speed");
    /** 盘丝蔓延：命中目标施加盘丝效果并生成区域效果云 */
    public static final ResourceKey<Enchantment> SPLASH_WEAVING = EnchantmentConstructorHelper.key("splash_weaving");
    /** 蓄风蔓延：命中目标施加蓄风效果并生成区域效果云 */
    public static final ResourceKey<Enchantment> SPLASH_WIND_CHARGED = EnchantmentConstructorHelper.key("splash_wind_charged");
    /** 凋零蔓延：命中目标施加凋零效果并生成区域效果云 */
    public static final ResourceKey<Enchantment> SPLASH_WITHER = EnchantmentConstructorHelper.key("splash_wither");
    /** 伏特：雷雨天射出的箭矢伤害增加 */
    public static final ResourceKey<Enchantment> VOLT = EnchantmentConstructorHelper.key("volt");

    private AllBowEnchantments() {
    }

    /**
     * [弓]类型附魔
     * <p>
     * 只能附魔在弓上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        accuracyShot(e);
        blastArrows(e);
        bombArrows(e);
        bulletTime(e);
        chainReaction(e);
        fatalArrow(e);
        fireArrows(e);
        focus(e);
        glassBreaker(e);
        iceArrows(e);
        rainDripstone(e);
        rainForge(e);
        rapid(e);
        ricochet(e);
        scatter(e);
        shotgun(e);
        snipe(e);
        splashBlindness(e);
        splashDelayedExplosion(e);
        splashGlowing(e);
        splashHunger(e);
        splashInfested(e);
        splashOozing(e);
        splashPoison(e);
        splashRegeneration(e);
        splashSlowFalling(e);
        splashSlowness(e);
        splashSpeed(e);
        splashWeaving(e);
        splashWindCharged(e);
        splashWither(e);
        volt(e);
    }

    /** 精准射击：箭矢无视重力直飞目标，未命中会消失 */
    private static void accuracyShot(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        e.register(builder, ACCURACY_SHOT, 0xFF55FF, "accuracy_shot");
    }

    /** 爆裂箭矢：命中产生爆炸伤害 */
    private static void blastArrows(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 2, 3,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/damage_bow"));
        e.postAttack(builder, target("attacker"), target("victim"), explode(false, e.damageType("minecraft:explosion"), linear(0.5f, 0.25f), e.blocks("#minecraft:blocks_wind_charge_explosions"), vec(0.0, 0.5, 0.0), linear(1.5f, 0.75f), false, interaction("none"), particle("minecraft:gust_emitter_small"), particle("minecraft:gust_emitter_large"), e.sound("minecraft:entity.wind_charge.wind_burst")), entityProperties("direct_attacker", e.entityOf("#minecraft:arrows")));
        e.entity(builder, EnchantmentEffectComponents.HIT_BLOCK, explode(false, e.damageType("minecraft:explosion"), linear(0.5f, 0.25f), e.blocks("#minecraft:blocks_wind_charge_explosions"), vec(0.0, 0.5, 0.0), linear(1.5f, 0.75f), false, interaction("none"), particle("minecraft:gust_emitter_small"), particle("minecraft:gust_emitter_large"), e.sound("minecraft:entity.wind_charge.wind_burst")), entityProperties("this", e.entityOf("#minecraft:arrows")));
        e.register(builder, BLAST_ARROWS, 0xFFAA00, "blast_arrows");
    }

    /** 爆炸箭：命中产生强力爆炸和击退 */
    private static void bombArrows(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 2, 3,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/damage_bow"));
        e.value(builder, EnchantmentEffectComponents.DAMAGE, add(constant(2f)), null);
        e.entity(builder, EnchantmentEffectComponents.HIT_BLOCK, explode(false, e.damageType("minecraft:arrow"), null, null, vec(0.0, 0.5, 0.0), linear(1.0f, 0.5f), false, interaction("none"), particle("minecraft:ash"), particle("minecraft:explosion_emitter"), e.fixedRangeSound("minecraft:entity.warden.sonic_boom", 16f)), allOf(entityProperties("this", e.entityOf("#minecraft:arrows"))));
        e.postAttack(builder, target("attacker"), target("victim"), explode(false, e.damageType("minecraft:arrow"), constant(0f), null, vec(0.0, 0.5, 0.0), linear(0.5f, 0.5f), false, interaction("none"), particle("minecraft:ash"), particle("minecraft:explosion_emitter"), e.fixedRangeSound("minecraft:entity.warden.sonic_boom", 16f)), entityProperties("direct_attacker", e.entityOf("#minecraft:arrows")));
        e.register(builder, BOMB_ARROWS, 0xFFAA00, "bomb_arrows");
    }

    /** 子弹时间：移动时获得缓降效果 */
    private static void bulletTime(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        e.location(builder, EnchantmentEffectComponents.LOCATION_CHANGED, mobEffect(e.mobEffects("minecraft:slow_falling"), constant(1.3f), constant(1.3f), linear(0f, 1f), linear(0f, 1f)), null);
        e.register(builder, BULLET_TIME, 0xFF55FF, "bullet_time");
    }

    /** 连锁反应：受伤的生物有几率使它们向附近的生物发射箭矢 */
    private static void chainReaction(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        e.register(builder, CHAIN_REACTION, 0xFF55FF, "chain_reaction");
    }

    /** 绝命箭矢：一击必杀非Boss生物(无掉落战利品，使用后给予DeBuff) */
    private static void fatalArrow(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 1, 1,
                Enchantment.dynamicCost(80, 50), Enchantment.dynamicCost(100, 50), 200, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/damage_bow"));
        e.postAttack(builder, target("attacker"), target("attacker"), allEntities(mobEffect(e.mobEffects("minecraft:bad_omen"), constant(3f), constant(3f), constant(2f), constant(2f)), mobEffect(e.mobEffects("minecraft:weakness"), constant(120f), constant(120f), constant(2f), constant(2f)), mobEffect(e.mobEffects("minecraft:unluck"), constant(600f), constant(600f), constant(9f), constant(9f)), mobEffect(e.mobEffects("minecraft:blindness"), constant(60f), constant(60f), constant(1f), constant(1f))), allOf(entityProperties("direct_attacker", e.entityOf("#minecraft:arrows")), inverted(entityProperties("this", e.entityOf("#redstone_enchants:black_entity")))));
        e.register(builder, FATAL_ARROW, 0xFF00BB, "fatal_arrow");
    }

    /** 火焰箭：命中区域产生火焰效果 */
    private static void fireArrows(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 2, 1,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/splash"));
        e.register(builder, FIRE_ARROWS, 0xFFAA00, "fire_arrows");
    }

    /** 焦点：减少每支箭的散布 */
    private static void focus(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/bow_spread"));
        e.value(builder, EnchantmentEffectComponents.PROJECTILE_SPREAD, add(linear(-5f, -5f)), null);
        e.register(builder, FOCUS, 0xFF55FF, "focus");
    }

    /** 玻璃破坏者：命中区域破坏玻璃 */
    private static void glassBreaker(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 6, 1,
                Enchantment.dynamicCost(8, 4), Enchantment.dynamicCost(16, 8), 4, "mainhand", "offhand");
        e.entity(builder, EnchantmentEffectComponents.HIT_BLOCK, replaceBlock(vec3i(0, 0, 0), e.blockPredicate("#c:glass_blocks", 0, 0, 0), simpleState(state("minecraft:air")), null), entityProperties("this", e.entityOf("#minecraft:arrows")));
        e.entity(builder, EnchantmentEffectComponents.HIT_BLOCK, particles(particle("minecraft:electric_spark"), position("in_bounding_box", 0f, 1f), position("in_bounding_box", 0f, 1f), velocity(0f, constantFloat(0f)), velocity(0f, constantFloat(0f)), constantFloat(0.3f)), null);
        e.register(builder, GLASS_BREAKER, 0xFFFF55, "glass_breaker");
    }

    /** 冰霜箭：命中区域产生冻结效果，有水则冻结成冰 */
    private static void iceArrows(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 2, 1,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/splash"));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:slowness"), constant(4f), constant(8f), constant(0f), constant(0f)), entityProperties("direct_attacker", e.entityOf("#minecraft:arrows")));
        e.register(builder, ICE_ARROWS, 0xFFAA00, "ice_arrows");
    }

    /** 石锥雨：命中区域生成滴水石锥攻击 */
    private static void rainDripstone(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/damage_bow"));
        e.register(builder, RAIN_DRIPSTONE, 0xFF55FF, "rain_dripstone");
    }

    /** 铁砧雨：命中区域生成铁砧攻击 */
    private static void rainForge(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/damage_bow"));
        e.register(builder, RAIN_FORGE, 0xFF55FF, "rain_forge");
    }

    /** 速射：增加拉弓速度 */
    private static void rapid(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        e.attributes(builder, attributeEffect("redstone_enchants:rapid", e.attribute("apothic_attributes:draw_speed"), EnchantmentConstructorHelper.operation("add_multiplied_total"), linear(0.3f, 0.15f)));
        e.register(builder, RAPID, 0xFF55FF, "rapid");
    }

    /** 跳弹：击中一个生物会让箭从生物身上反弹并击中附近的另一个生物 */
    private static void ricochet(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        e.value(builder, EnchantmentEffectComponents.PROJECTILE_PIERCING, add(linear(1f, 1f)), null);
        e.register(builder, RICOCHET, 0xFF55FF, "ricochet");
    }

    /** 分散：增加每支箭的散布 */
    private static void scatter(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/bow_spread"));
        e.value(builder, EnchantmentEffectComponents.PROJECTILE_SPREAD, add(linear(10.0f, 10.0f)), null);
        e.register(builder, SCATTER, 0xFF55FF, "scatter");
    }

    /** 霰弹枪：一次射出多支箭矢 */
    private static void shotgun(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 2, 6,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/no_shotgun"));
        e.value(builder, EnchantmentEffectComponents.PROJECTILE_COUNT, add(linear(0f, 1f)), null);
        e.value(builder, EnchantmentEffectComponents.PROJECTILE_SPREAD, add(linear(0f, 1f)), null);
        e.special(builder, EnchantmentEffectComponents.CROSSBOW_CHARGE_TIME, add(linear(0.5f, 0.25f)));
        e.value(builder, EnchantmentEffectComponents.ITEM_DAMAGE, multiply(fraction(constant(1f), linear(1f, 1f))), randomChance(constantNumber(0.9f)));
        e.register(builder, SHOTGUN, 0xFFAA00, "shotgun");
    }

    /** 狙击：与目标距离每增加10格，造成的伤害提高 */
    private static void snipe(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        e.register(builder, SNIPE, 0xFF55FF, "snipe");
    }

    /** 失明蔓延：命中目标施加失明效果并生成区域效果云 */
    private static void splashBlindness(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/splash"));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:blindness"), constant(4f), constant(8f), constant(0f), linear(0f, 1f)), entityProperties("direct_attacker", e.entityOf("#minecraft:arrows")));
        e.register(builder, SPLASH_BLINDNESS, 0xFF55FF, "splash_blindness");
    }

    /** 延迟爆破：箭矢命中生物后让其延迟爆炸 */
    private static void splashDelayedExplosion(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 2, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/splash"));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("ars_nouveau:blasting"), constant(3f), constant(3f), constant(0f), linear(0f, 1f)), entityProperties("direct_attacker", e.entityOf("#minecraft:arrows")));
        e.register(builder, SPLASH_DELAYED_EXPLOSION, 0xFF55FF, "splash_delayed_explosion");
    }

    /** 发光蔓延：命中目标施加发光效果并生成区域效果云 */
    private static void splashGlowing(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/splash"));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:glowing"), constant(4f), constant(8f), constant(0f), linear(0f, 1f)), entityProperties("direct_attacker", e.entityOf("#minecraft:arrows")));
        e.register(builder, SPLASH_GLOWING, 0xFF55FF, "splash_glowing");
    }

    /** 饥饿蔓延：命中目标施加饥饿效果并生成区域效果云 */
    private static void splashHunger(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/splash"));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:hunger"), constant(4f), constant(8f), constant(0f), linear(0f, 1f)), entityProperties("direct_attacker", e.entityOf("#minecraft:arrows")));
        e.register(builder, SPLASH_HUNGER, 0xFF55FF, "splash_hunger");
    }

    /** 寄生蔓延：命中目标施加寄生效果并生成区域效果云 */
    private static void splashInfested(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/splash"));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:infested"), constant(4f), constant(8f), constant(0f), linear(0f, 1f)), entityProperties("direct_attacker", e.entityOf("#minecraft:arrows")));
        e.register(builder, SPLASH_INFESTED, 0xFF55FF, "splash_infested");
    }

    /** 渗浆蔓延：命中目标施加渗浆效果并生成区域效果云 */
    private static void splashOozing(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/splash"));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:oozing"), constant(4f), constant(8f), constant(0f), linear(0f, 1f)), entityProperties("direct_attacker", e.entityOf("#minecraft:arrows")));
        e.register(builder, SPLASH_OOZING, 0xFF55FF, "splash_oozing");
    }

    /** 中毒蔓延：命中目标施加中毒效果并生成区域效果云 */
    private static void splashPoison(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/splash"));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:poison"), constant(4f), constant(8f), constant(0f), linear(0f, 1f)), entityProperties("direct_attacker", e.entityOf("#minecraft:arrows")));
        e.register(builder, SPLASH_POISON, 0xFF55FF, "splash_poison");
    }

    /** 治疗蔓延：命中目标施加生命恢复效果并生成区域效果云 */
    private static void splashRegeneration(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/splash"));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:regeneration"), constant(4f), constant(8f), constant(0f), linear(0f, 1f)), entityProperties("direct_attacker", e.entityOf("#minecraft:arrows")));
        e.register(builder, SPLASH_REGENERATION, 0xFF55FF, "splash_regeneration");
    }

    /** 缓降蔓延：命中目标施加缓降效果并生成区域效果云 */
    private static void splashSlowFalling(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/splash"));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:slow_falling"), constant(4f), constant(8f), constant(0f), linear(0f, 1f)), entityProperties("direct_attacker", e.entityOf("#minecraft:arrows")));
        e.register(builder, SPLASH_SLOW_FALLING, 0xFF55FF, "splash_slow_falling");
    }

    /** 缓慢蔓延：命中目标施加缓慢效果并生成区域效果云 */
    private static void splashSlowness(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/splash"));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:slowness"), constant(4f), constant(8f), constant(0f), linear(0f, 1f)), entityProperties("direct_attacker", e.entityOf("#minecraft:arrows")));
        e.register(builder, SPLASH_SLOWNESS, 0xFF55FF, "splash_slowness");
    }

    /** 迅捷蔓延：命中目标施加速度效果并生成区域效果云 */
    private static void splashSpeed(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/boots_gallop"));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:speed"), constant(4f), constant(8f), constant(0f), linear(0f, 1f)), entityProperties("direct_attacker", e.entityOf("#minecraft:arrows")));
        e.register(builder, SPLASH_SPEED, 0xFF55FF, "splash_speed");
    }

    /** 盘丝蔓延：命中目标施加盘丝效果并生成区域效果云 */
    private static void splashWeaving(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/splash"));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:weaving"), constant(4f), constant(8f), constant(0f), linear(0f, 1f)), entityProperties("direct_attacker", e.entityOf("#minecraft:arrows")));
        e.register(builder, SPLASH_WEAVING, 0xFF55FF, "splash_weaving");
    }

    /** 蓄风蔓延：命中目标施加蓄风效果并生成区域效果云 */
    private static void splashWindCharged(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/splash"));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:wind_charged"), constant(4f), constant(8f), constant(0f), linear(0f, 1f)), entityProperties("direct_attacker", e.entityOf("#minecraft:arrows")));
        e.register(builder, SPLASH_WIND_CHARGED, 0xFF55FF, "splash_wind_charged");
    }

    /** 凋零蔓延：命中目标施加凋零效果并生成区域效果云 */
    private static void splashWither(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/splash"));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:wither"), constant(4f), constant(8f), constant(0f), linear(0f, 1f)), entityProperties("direct_attacker", e.entityOf("#minecraft:arrows")));
        e.register(builder, SPLASH_WITHER, 0xFF55FF, "splash_wither");
    }

    /** 伏特：雷雨天射出的箭矢伤害增加 */
    private static void volt(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:all_bow", "#redstone_enchants:all_bow", 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        e.register(builder, VOLT, 0xFF55FF, "volt");
    }

}
