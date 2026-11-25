package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;

import com.chinaex123.redstone_enchants.init.REItemTags;
import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.*;

/**
 * [护腿]类型附魔
 * <p>
 * 只能附魔在护腿上
 */
public final class LegArmorEnchantments {
    /** 快速游泳：大幅提高水中移动速度 */
    public static final ResourceKey<Enchantment> FAST_SWIM = EnchantmentConstructorHelper.key("fast_swim");
    /** 潜行庇佑：蹲伏时获得抗性提升(伴随缓慢效果) */
    public static final ResourceKey<Enchantment> FORTRESS_STANCE = EnchantmentConstructorHelper.key("fortress_stance");
    /** 隐身斗篷：蹲伏时获得隐身效果 */
    public static final ResourceKey<Enchantment> INVISIBILITY_CLOAK = EnchantmentConstructorHelper.key("invisibility_cloak");
    /** 抗性守护：被攻击时概率获得抗性提升/使攻击者虚弱 */
    public static final ResourceKey<Enchantment> RESILIENCE_SENTINEL = EnchantmentConstructorHelper.key("resilience_sentinel");
    /** 安全坠落：增加安全坠落高度 */
    public static final ResourceKey<Enchantment> SAFE_FALL = EnchantmentConstructorHelper.key("safe_fall");
    /** 跨步：提升行走高度和基础移动速度 */
    public static final ResourceKey<Enchantment> STRIDING = EnchantmentConstructorHelper.key("striding");
    /** 战术护膝：落地时潜行可抵消掉落伤害 */
    public static final ResourceKey<Enchantment> TACTICAL_KNEE = EnchantmentConstructorHelper.key("tactical_knee");

    private LegArmorEnchantments() {
    }

    /**
     * [护腿]类型附魔
     * <p>
     * 只能附魔在护腿上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        fastSwim(e);
        fortressStance(e);
        invisibilityCloak(e);
        resilienceSentinel(e);
        safeFall(e);
        striding(e);
        tacticalKnee(e);
    }

    /** 快速游泳：大幅提高水中移动速度 */
    private static void fastSwim(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_LEG, REItemTags.ARMORS_LEG, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "legs");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.fast_swim", e.attribute("neoforge:swim_speed"),
                operation("add_multiplied_base"), constant(0.5f))
        );
        e.register(builder, FAST_SWIM, 0xFF55FF, "fast_swim");
    }

    /** 潜行庇佑：蹲伏时获得抗性提升(伴随缓慢效果) */
    private static void fortressStance(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_LEG, REItemTags.ARMORS_LEG, 2, 4,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "legs");
        e.entity(builder, EnchantmentEffectComponents.TICK, mobEffect(e.mobEffects(MobEffects.MOVEMENT_SLOWDOWN, MobEffects.DAMAGE_RESISTANCE),
                constant(1f), constant(1f), linear(0f, 1f), linear(0f, 1f)),
                entityProperties("this", entity().flags(flags().setOnGround(true).setCrouching(true))));
        e.register(builder, FORTRESS_STANCE, 0xFFAA00, "fortress_stance");
    }

    /** 隐身斗篷：蹲伏时获得隐身效果 */
    private static void invisibilityCloak(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_LEG, REItemTags.ARMORS_LEG, 2, 1,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "legs");
        e.register(builder, INVISIBILITY_CLOAK, 0xFFAA00, "invisibility_cloak");
    }

    /** 抗性守护：被攻击时概率获得抗性提升/使攻击者虚弱 */
    private static void resilienceSentinel(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_LEG, REItemTags.ARMORS_LEG, 2, 4,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "legs");
        e.postAttack(builder, target("victim"), target("victim"),
                mobEffect(e.mobEffects(MobEffects.DAMAGE_RESISTANCE), constant(6f), constant(12f), constant(0f), constant(2f)),
                randomChance(levelNumber(linear(0.1f, 0.2f)))
        );
        e.postAttack(builder, target("victim"), target("attacker"),
                mobEffect(e.mobEffects(MobEffects.WEAKNESS), constant(3f), constant(3f), constant(0f), constant(0f)),
                randomChance(levelNumber(linear(0.1f, 0.05f)))
        );
        e.register(builder, RESILIENCE_SENTINEL, 0xFFAA00, "resilience_sentinel");
    }

    /** 安全坠落：增加安全坠落高度 */
    private static void safeFall(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_LEG, REItemTags.ARMORS_LEG, 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "legs");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.safe_fall_1", e.attribute("minecraft:generic.safe_fall_distance"),
                EnchantmentConstructorHelper.operation("add_value"), linear(2f, 1f))
        );
        e.register(builder, SAFE_FALL, 0xFF55FF, "safe_fall");
    }

    /** 跨步：提升行走高度和基础移动速度 */
    private static void striding(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_LEG, REItemTags.ARMORS_LEG, 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "legs");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.striding_1", e.attribute("minecraft:generic.step_height"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.5f, 0.25f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.striding_2", e.attribute("minecraft:generic.movement_speed"),
                EnchantmentConstructorHelper.operation("add_multiplied_base"), linear(0f, 0.05f))
        );
        e.register(builder, STRIDING, 0xFF55FF, "striding");
    }

    /** 战术护膝：落地时潜行可抵消掉落伤害 */
    private static void tacticalKnee(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_LEG, REItemTags.ARMORS_LEG, 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "legs");
        e.register(builder, TACTICAL_KNEE, 0xFF55FF, "tactical_knee");
    }

}
