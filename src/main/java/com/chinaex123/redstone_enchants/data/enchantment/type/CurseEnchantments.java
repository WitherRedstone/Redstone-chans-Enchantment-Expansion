package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;

import com.chinaex123.redstone_enchants.init.REItemTags;
import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.*;

/** [诅咒]类型附魔 */
public final class CurseEnchantments {
    /** 恐高症：当高度超过180时获得反胃、缓慢和霜冻效果 */
    public static final ResourceKey<Enchantment> CURSE_OF_ACROPHOBIA = EnchantmentConstructorHelper.key("curse_of_acrophobia");
    /** 低洼恐惧症：当高度低于-36时获得挖掘疲劳和黑暗效果 */
    public static final ResourceKey<Enchantment> CURSE_OF_BASIPHOBIA = EnchantmentConstructorHelper.key("curse_of_basiphobia");
    /** 自爆：受到伤害时有几率引发爆炸 */
    public static final ResourceKey<Enchantment> CURSE_OF_BLAST = EnchantmentConstructorHelper.key("curse_of_blast");
    /** 失明诅咒：使穿戴者持续获得失明效果 */
    public static final ResourceKey<Enchantment> CURSE_OF_BLINDNESS = EnchantmentConstructorHelper.key("curse_of_blindness");
    /** 破损诅咒：每次使用都会消耗额外耐久 */
    public static final ResourceKey<Enchantment> CURSE_OF_BREAKING = EnchantmentConstructorHelper.key("curse_of_breaking");
    /** 笨拙诅咒：使用时有几率掉落物品 */
    public static final ResourceKey<Enchantment> CURSE_OF_CLUMSINESS = EnchantmentConstructorHelper.key("curse_of_clumsiness");
    /** 死亡诅咒：攻击时有几率立即死亡 */
    public static final ResourceKey<Enchantment> CURSE_OF_DEATH = EnchantmentConstructorHelper.key("curse_of_death");
    /** 双刃剑诅咒：使用时有几率伤害自己 */
    public static final ResourceKey<Enchantment> CURSE_OF_DOUBLE_EDGE = EnchantmentConstructorHelper.key("curse_of_double_edge");
    /** 重力诅咒：增加坠落速度和伤害 */
    public static final ResourceKey<Enchantment> CURSE_OF_GRAVITY = EnchantmentConstructorHelper.key("curse_of_gravity");
    /** 隐形诅咒：攻击会使目标生物隐形 */
    public static final ResourceKey<Enchantment> CURSE_OF_HIDING = EnchantmentConstructorHelper.key("curse_of_hiding");
    /** 饥饿诅咒：使穿戴者持续获得饥饿效果 */
    public static final ResourceKey<Enchantment> CURSE_OF_HUNGER = EnchantmentConstructorHelper.key("curse_of_hunger");
    /** 短手诅咒：减少交互和攻击距离 */
    public static final ResourceKey<Enchantment> CURSE_OF_REACH = EnchantmentConstructorHelper.key("curse_of_reach");
    /** 腐蚀：在水中或雨中时每秒减少耐久 */
    public static final ResourceKey<Enchantment> CURSE_OF_RUST = EnchantmentConstructorHelper.key("curse_of_rust");
    /** 静止诅咒：当玩家静止不动时会受到伤害 */
    public static final ResourceKey<Enchantment> CURSE_OF_STILLNESS = EnchantmentConstructorHelper.key("curse_of_stillness");
    /** 倒霉诅咒：你的运气会变差 */
    public static final ResourceKey<Enchantment> CURSE_OF_UNLUCKY = EnchantmentConstructorHelper.key("curse_of_unlucky");
    /** 脆弱诅咒：减少护甲同时增加移动速度 */
    public static final ResourceKey<Enchantment> CURSE_OF_VULNERABILITY = EnchantmentConstructorHelper.key("curse_of_vulnerability");
    /** 水源诅咒：接触水会给予中毒效果 */
    public static final ResourceKey<Enchantment> CURSE_OF_WATER_SOURCE = EnchantmentConstructorHelper.key("curse_of_water_source");

    private CurseEnchantments() {
    }

    /** [诅咒]类型附魔 */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        curseOfAcrophobia(e);
        curseOfBasiphobia(e);
        curseOfBlast(e);
        curseOfBlindness(e);
        curseOfBreaking(e);
        curseOfClumsiness(e);
        curseOfDeath(e);
        curseOfDoubleEdge(e);
        curseOfGravity(e);
        curseOfHiding(e);
        curseOfHunger(e);
        curseOfReach(e);
        curseOfRust(e);
        curseOfStillness(e);
        curseOfUnlucky(e);
        curseOfVulnerability(e);
        curseOfWaterSource(e);
    }

    /** 恐高症：当高度超过180时获得反胃、缓慢和霜冻效果 */
    private static void curseOfAcrophobia(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_HEAD, REItemTags.ARMORS_HEAD, 4, 1,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "head");
        e.entity(builder, EnchantmentEffectComponents.TICK, mobEffect(e.mobEffects("minecraft:nausea,minecraft:slowness"), constant(1f), constant(1f), constant(2f), constant(2f)), locationCheck(location().setY(atLeast(180f))));
        e.register(builder, CURSE_OF_ACROPHOBIA, 0xFF5555, "curse_of_acrophobia");
    }

    /** 低洼恐惧症：当高度低于-36时获得挖掘疲劳和黑暗效果 */
    private static void curseOfBasiphobia(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_HEAD, REItemTags.ARMORS_HEAD, 4, 1,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "head");
        e.entity(builder, EnchantmentEffectComponents.TICK, mobEffect(e.mobEffects("minecraft:mining_fatigue,minecraft:darkness"), constant(1f), constant(1f), constant(2f), constant(2f)), locationCheck(location().setY(atMost(-36f))));
        e.register(builder, CURSE_OF_BASIPHOBIA, 0xFF5555, "curse_of_basiphobia");
    }

    /** 自爆：受到伤害时有几率引发爆炸 */
    private static void curseOfBlast(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS, REItemTags.ARMORS, 4, 1,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "armor");
        e.register(builder, CURSE_OF_BLAST, 0xFF5555, "curse_of_blast");
    }

    /** 失明诅咒：使穿戴者持续获得失明效果 */
    private static void curseOfBlindness(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_HEAD, REItemTags.ARMORS_HEAD, 4, 3,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "head");
        e.entity(builder, EnchantmentEffectComponents.TICK, mobEffect(e.mobEffects("minecraft:blindness"), constant(1f), constant(1f), linear(0f, 1f), linear(0f, 1f)), null);
        e.register(builder, CURSE_OF_BLINDNESS, 0xFF5555, "curse_of_blindness");
    }

    /** 破损诅咒：每次使用都会消耗额外耐久 */
    private static void curseOfBreaking(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#c:enchantables", "#c:enchantables", 4, 4,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "any");
        e.value(builder, EnchantmentEffectComponents.ITEM_DAMAGE, add(linear(1f, 1f)), randomChance(levelNumber(linear(0.15f, 0.15f))));
        e.register(builder, CURSE_OF_BREAKING, 0xFF5555, "curse_of_breaking");
    }

    /** 笨拙诅咒：使用时有几率掉落物品 */
    private static void curseOfClumsiness(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#c:enchantables", "#c:enchantables", 4, 1,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "any");
        e.register(builder, CURSE_OF_CLUMSINESS, 0xFF5555, "curse_of_clumsiness");
    }

    /** 死亡诅咒：攻击时有几率立即死亡 */
    private static void curseOfDeath(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.TOOLS, REItemTags.TOOLS, 4, 1,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "mainhand", "offhand");
        e.register(builder, CURSE_OF_DEATH, 0xFF5555, "curse_of_death");
    }

    /** 双刃剑诅咒：使用时有几率伤害自己 */
    private static void curseOfDoubleEdge(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ALL_TOOLS, REItemTags.ALL_TOOLS, 4, 1,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "mainhand", "offhand");
        e.postAttack(builder, target("attacker"), target("attacker"), damageEntity(constant(2f), constant(4f), e.damageType("minecraft:magic")), randomChance(constantNumber(0.4f)));
        e.register(builder, CURSE_OF_DOUBLE_EDGE, 0xFF5555, "curse_of_double_edge");
    }

    /** 重力诅咒：增加坠落速度和伤害 */
    private static void curseOfGravity(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_FOOT, REItemTags.ARMORS_FOOT, 4, 3,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "feet");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.curse_of_gravity_1", e.attribute("minecraft:generic.gravity"), EnchantmentConstructorHelper.operation("add_value"), linear(0.02f, 0.06f)));
        e.register(builder, CURSE_OF_GRAVITY, 0xFF5555, "curse_of_gravity");
    }

    /** 隐形诅咒：攻击会使目标生物隐形 */
    private static void curseOfHiding(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ALL_TOOLS, REItemTags.ALL_TOOLS, 4, 1,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "mainhand", "offhand");
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:invisibility"), linear(2f, 3f), linear(5f, 5f), linear(1f, 1f), linear(1f, 1f)), damageSource(damageSourcePredicate()));
        e.register(builder, CURSE_OF_HIDING, 0xFF5555, "curse_of_hiding");
    }

    /** 饥饿诅咒：使穿戴者持续获得饥饿效果 */
    private static void curseOfHunger(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_HEAD, REItemTags.ARMORS_HEAD, 4, 4,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "head");
        e.entity(builder, EnchantmentEffectComponents.TICK, mobEffect(e.mobEffects("minecraft:hunger"), constant(1f), constant(1f), linear(0f, 1f), linear(0f, 1f)), null);
        e.register(builder, CURSE_OF_HUNGER, 0xFF5555, "curse_of_hunger");
    }

    /** 短手诅咒：减少交互和攻击距离 */
    private static void curseOfReach(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ALL_TOOLS, REItemTags.ALL_TOOLS, 4, 3,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "mainhand", "offhand");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.curse_of_reach_1", e.attribute("minecraft:player.block_interaction_range"), EnchantmentConstructorHelper.operation("add_value"), linear(-1f, -1f)));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.curse_of_reach_2", e.attribute("minecraft:player.entity_interaction_range"), EnchantmentConstructorHelper.operation("add_value"), linear(-1f, -1f)));
        e.register(builder, CURSE_OF_REACH, 0xFF5555, "curse_of_reach");
    }

    /** 腐蚀：在水中或雨中时每秒减少耐久 */
    private static void curseOfRust(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#c:enchantables", "#c:enchantables", 4, 3,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "any");
        e.register(builder, CURSE_OF_RUST, 0xFF5555, "curse_of_rust");
    }

    /** 静止诅咒：当玩家静止不动时会受到伤害 */
    private static void curseOfStillness(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS, REItemTags.ARMORS, 4, 1,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "armor");
        e.entity(builder, EnchantmentEffectComponents.TICK, damageEntity(constant(0f), constant(0.5f), e.damageType("minecraft:dry_out")), entityProperties("this", entity().moving(allMovements(speed(exactly(0f)), horizontalSpeed(exactly(0f)), verticalSpeed(exactly(0f)), fallDistance(exactly(0f))))));
        e.register(builder, CURSE_OF_STILLNESS, 0xFF5555, "curse_of_stillness");
    }

    /** 倒霉诅咒：你的运气会变差 */
    private static void curseOfUnlucky(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_HEAD, REItemTags.ARMORS_HEAD, 5, 4,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "head");
        e.entity(builder, EnchantmentEffectComponents.TICK, mobEffect(e.mobEffects("minecraft:unluck"), constant(1f), constant(1f), linear(0f, 1f), linear(0f, 1f)), null);
        e.register(builder, CURSE_OF_UNLUCKY, 0xFF5555, "curse_of_unlucky");
    }

    /** 脆弱诅咒：减少护甲同时增加移动速度 */
    private static void curseOfVulnerability(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS, REItemTags.ARMORS, 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "armor");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.curse_of_vulnerability_damage", e.attribute("minecraft:generic.armor"), EnchantmentConstructorHelper.operation("add_multiplied_total"), linear(-0.2f, 0f)));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.curse_of_vulnerability_speed", e.attribute("minecraft:generic.movement_speed"), EnchantmentConstructorHelper.operation("add_multiplied_total"), linear(0.1f, 0f)));
        e.register(builder, CURSE_OF_VULNERABILITY, 0xFF5555, "curse_of_vulnerability");
    }

    /** 水源诅咒：接触水会给予中毒效果 */
    private static void curseOfWaterSource(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS, REItemTags.ARMORS, 4, 1,
                Enchantment.dynamicCost(10, 6), Enchantment.dynamicCost(20, 10), 6, "armor");
        e.register(builder, CURSE_OF_WATER_SOURCE, 0xFF5555, "curse_of_water_source");
    }

}
