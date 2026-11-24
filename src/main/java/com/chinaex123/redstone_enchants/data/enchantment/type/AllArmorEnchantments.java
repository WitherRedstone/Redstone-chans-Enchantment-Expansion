package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;

import com.chinaex123.redstone_enchants.init.REEnchantmentTags;
import com.chinaex123.redstone_enchants.init.REItemTags;
import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.*;

/**
 * [护甲通用]类型附魔
 * <p>
 * 只能附魔在护甲上
 */
public final class AllArmorEnchantments {
    /** 锋芒之险：提升攻击力但降低防御 */
    public static final ResourceKey<Enchantment> DANGEROUS_EDGE = EnchantmentConstructorHelper.key("dangerous_edge");
    /** 昼夜流转：白天增加伤害，夜晚增加移动速度 */
    public static final ResourceKey<Enchantment> DAYNIGHT_CYCLE = EnchantmentConstructorHelper.key("daynight_cycle");
    /** 外骨骼：增加盔甲韧性 */
    public static final ResourceKey<Enchantment> EXOSKELETON = EnchantmentConstructorHelper.key("exoskeleton");
    /** 消防：减少火焰持续时间 */
    public static final ResourceKey<Enchantment> FIRE_PROTECTION = EnchantmentConstructorHelper.key("fire_protection");
    /** 坚定：提升护甲值 */
    public static final ResourceKey<Enchantment> FORTITUDE = EnchantmentConstructorHelper.key("fortitude");
    /** 狂怒：提升攻击力和穿透但降低防御 */
    public static final ResourceKey<Enchantment> FURY = EnchantmentConstructorHelper.key("fury");
    /** 日光防护：白天保护效果×2(生效时间6:00~18:00) */
    public static final ResourceKey<Enchantment> PROTECTION_DAY = EnchantmentConstructorHelper.key("protection_day");
    /** 末地防护：末地维度保护效果×2 */
    public static final ResourceKey<Enchantment> PROTECTION_END = EnchantmentConstructorHelper.key("protection_end");
    /** 下界防护：下界维度保护效果×2 */
    public static final ResourceKey<Enchantment> PROTECTION_NETHER = EnchantmentConstructorHelper.key("protection_nether");
    /** 夜间防护：夜晚保护效果×2(生效时间18:00~6:00) */
    public static final ResourceKey<Enchantment> PROTECTION_NIGHT = EnchantmentConstructorHelper.key("protection_night");
    /** 重生护盾：受到致命伤害时阻止死亡，并给予生命恢复/抗性提升和抗火，触发后该附魔消失1个等级 */
    public static final ResourceKey<Enchantment> REVIVE_WARD = EnchantmentConstructorHelper.key("revive_ward");
    /** 雪球：佩戴时受伤会概率向攻击者扔雪球 */
    public static final ResourceKey<Enchantment> SNOWBALL = EnchantmentConstructorHelper.key("snowball");
    /** 脆弱护甲：降低防御但提升速度并在潜行且在地面时给予抗性提升 */
    public static final ResourceKey<Enchantment> WEAK_ARMOR = EnchantmentConstructorHelper.key("weak_armor");

    private AllArmorEnchantments() {
    }

    /**
     * [护甲通用]类型附魔
     * <p>
     * 只能附魔在护甲上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        dangerousEdge(e);
        daynightCycle(e);
        exoskeleton(e);
        fireProtection(e);
        fortitude(e);
        fury(e);
        protectionDay(e);
        protectionEnd(e);
        protectionNether(e);
        protectionNight(e);
        reviveWard(e);
        snowball(e);
        weakArmor(e);
    }

    /** 锋芒之险：提升攻击力但降低防御 */
    private static void dangerousEdge(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS, REItemTags.ARMORS, 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "armor");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_ARMORS));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.dangerous_edge_1", e.attribute("minecraft:generic.armor"), EnchantmentConstructorHelper.operation("add_value"), linear(-2.5f, -1.5f)));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.dangerous_edge_2", e.attribute("minecraft:generic.attack_damage"), EnchantmentConstructorHelper.operation("add_value"), linear(2.5f, 1.5f)));
        e.register(builder, DANGEROUS_EDGE, 0xFF55FF, "dangerous_edge");
    }

    /** 昼夜流转：白天增加伤害，夜晚增加移动速度 */
    private static void daynightCycle(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS, REItemTags.ARMORS, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "armor");
        e.register(builder, DAYNIGHT_CYCLE, 0xFF55FF, "daynight_cycle");
    }

    /** 外骨骼：增加盔甲韧性 */
    private static void exoskeleton(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS, REItemTags.ARMORS, 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "armor");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.exoskeleton_1", e.attribute("minecraft:generic.armor_toughness"), EnchantmentConstructorHelper.operation("add_value"), linear(4f, 2f)));
        e.register(builder, EXOSKELETON, 0xFF55FF, "exoskeleton");
    }

    /** 消防：减少火焰持续时间 */
    private static void fireProtection(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS, REItemTags.ARMORS, 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "armor");
        e.attributes(builder, attributeEffect("minecraft:enchantment.fire_protection_1", e.attribute("minecraft:generic.burning_time"), EnchantmentConstructorHelper.operation("add_multiplied_base"), linear(-0.25f, -0.25f)));
        e.value(builder, EnchantmentEffectComponents.DAMAGE_PROTECTION, add(linear(2.0f, 2.0f)), allOf(damageSource(damageSourcePredicate().tag(damageTag("minecraft:is_fire", true)).tag(damageTag("minecraft:bypasses_invulnerability", false)))));
        e.register(builder, FIRE_PROTECTION, 0xFF55FF, "fire_protection");
    }

    /** 坚定：提升护甲值 */
    private static void fortitude(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS, REItemTags.ARMORS, 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "armor");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.fortitude_1", e.attribute("minecraft:generic.armor"), EnchantmentConstructorHelper.operation("add_multiplied_base"), linear(0.14f, 0.12f)));
        e.register(builder, FORTITUDE, 0xFF55FF, "fortitude");
    }

    /** 狂怒：提升攻击力和穿透但降低防御 */
    private static void fury(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS, REItemTags.ARMORS, 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "armor");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_ARMORS));
        e.value(builder, EnchantmentEffectComponents.ARMOR_EFFECTIVENESS, add(linear(-0.045f, -0.035f)), null);
        e.attributes(builder, attributeEffect("minecraft:enchantment.fury_1", e.attribute("minecraft:generic.attack_damage"), EnchantmentConstructorHelper.operation("add_multiplied_total"), linear(0.2f, 0.2f)));
        e.attributes(builder, attributeEffect("minecraft:enchantment.fury_2", e.attribute("minecraft:generic.armor"), EnchantmentConstructorHelper.operation("add_multiplied_total"), linear(-0.1f, -0.2f)));
        e.register(builder, FURY, 0xFF55FF, "fury");
    }

    /** 日光防护：白天保护效果×2(生效时间6:00~18:00) */
    private static void protectionDay(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS, REItemTags.ARMORS, 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "armor");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_ARMORS));
        e.value(builder, EnchantmentEffectComponents.DAMAGE_PROTECTION, add(linear(2f, 2f)), allOf(damageSource(damageSourcePredicate().tag(damageTag("minecraft:bypasses_invulnerability", false))), timeCheck(24000, 0, 12000)));
        e.register(builder, PROTECTION_DAY, 0xFF55FF, "protection_day");
    }

    /** 末地防护：末地维度保护效果×2 */
    private static void protectionEnd(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS, REItemTags.ARMORS, 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "armor");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_ARMORS));
        e.value(builder, EnchantmentEffectComponents.DAMAGE_PROTECTION, add(linear(2f, 2f)), allOf(damageSource(damageSourcePredicate().tag(damageTag("minecraft:bypasses_invulnerability", false))), locationCheck(location().setDimension(dimension("minecraft:the_end")))));
        e.register(builder, PROTECTION_END, 0xFF55FF, "protection_end");
    }

    /** 下界防护：下界维度保护效果×2 */
    private static void protectionNether(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS, REItemTags.ARMORS, 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "armor");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_ARMORS));
        e.value(builder, EnchantmentEffectComponents.DAMAGE_PROTECTION, add(linear(2f, 2f)), allOf(damageSource(damageSourcePredicate().tag(damageTag("minecraft:bypasses_invulnerability", false))), locationCheck(location().setDimension(dimension("minecraft:the_nether")))));
        e.register(builder, PROTECTION_NETHER, 0xFF55FF, "protection_nether");
    }

    /** 夜间防护：夜晚保护效果×2(生效时间18:00~6:00) */
    private static void protectionNight(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS, REItemTags.ARMORS, 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "armor");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_ARMORS));
        e.value(builder, EnchantmentEffectComponents.DAMAGE_PROTECTION, add(linear(2f, 2f)), allOf(damageSource(damageSourcePredicate().tag(damageTag("minecraft:bypasses_invulnerability", false))), timeCheck(24000, 12000, 24000)));
        e.register(builder, PROTECTION_NIGHT, 0xFF55FF, "protection_night");
    }

    /** 重生护盾：受到致命伤害时阻止死亡，并给予生命恢复/抗性提升和抗火，触发后该附魔消失1个等级 */
    private static void reviveWard(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS, REItemTags.ARMORS, 2, 1,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "armor");
        e.register(builder, REVIVE_WARD, 0xFFAA00, "revive_ward");
    }

    /** 雪球：佩戴时受伤会概率向攻击者扔雪球 */
    private static void snowball(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS, REItemTags.ARMORS, 6, 3,
                Enchantment.dynamicCost(8, 4), Enchantment.dynamicCost(16, 8), 4, "armor");
        e.register(builder, SNOWBALL, 0xFFFF55, "snowball");
    }

    /** 脆弱护甲：降低防御但提升速度并在潜行且在地面时给予抗性提升 */
    private static void weakArmor(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS, REItemTags.ARMORS, 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "armor");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_ARMORS));
        e.attributes(builder, attributeEffect("redstone_enchants:weak_armor_1", e.attribute("minecraft:generic.armor"), EnchantmentConstructorHelper.operation("add_value"), linear(-1.0f, -1.0f)));
        e.attributes(builder, attributeEffect("redstone_enchants:weak_armor_2", e.attribute("minecraft:generic.movement_speed"), EnchantmentConstructorHelper.operation("add_multiplied_base"), linear(0.05f, 0.05f)));
        e.entity(builder, EnchantmentEffectComponents.TICK, mobEffect(e.mobEffects("minecraft:resistance"), constant(0.2f), constant(0.2f), constant(1f), constant(1f)), entityProperties("this", entity().flags(flags().setOnGround(true).setCrouching(true))));
        e.register(builder, WEAK_ARMOR, 0xFF55FF, "weak_armor");
    }

}
