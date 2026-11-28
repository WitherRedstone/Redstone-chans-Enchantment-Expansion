package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import com.chinaex123.redstone_enchants.init.REEnchantmentTags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;

import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.*;

/**
 * [盾牌]类型附魔
 * <p>
 * 只能附魔在盾牌上
 */
public final class ShieldEnchantments {
    /** 吸收护盾：获得10秒伤害吸收效果(冷却10秒) */
    public static final ResourceKey<Enchantment> ABSORBENT_SHIELD = EnchantmentConstructorHelper.key("absorbent_shield");
    /** 战斗回响：盾牌格挡后，提升下一次攻击的伤害 */
    public static final ResourceKey<Enchantment> ECHOES_BATTLE = EnchantmentConstructorHelper.key("echoes_battle");
    /** 盾甲：副手持盾时增加护甲值 */
    public static final ResourceKey<Enchantment> SHIELD_ARMOR = EnchantmentConstructorHelper.key("shield_armor");
    /** 抗退盾牌：副手持盾时免疫击退 */
    public static final ResourceKey<Enchantment> STABLE_SHIELD = EnchantmentConstructorHelper.key("stable_shield");
    /** 力量之盾：被击中时有几率获得力量效果 */
    public static final ResourceKey<Enchantment> STRENGTH_SHIELD = EnchantmentConstructorHelper.key("strength_shield");

    private ShieldEnchantments() {
    }

    /**
     * [盾牌]类型附魔
     * <p>
     * 只能附魔在盾牌上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        absorbentShield(e);
        echoesBattle(e);
        shieldArmor(e);
        stableShield(e);
        strengthShield(e);
    }

    /** 吸收护盾：获得10秒伤害吸收效果(冷却10秒) */
    private static void absorbentShield(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#c:tools/shield", "#c:tools/shield", 3, 2,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "offhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_SHIELD));
        e.entity(builder, EnchantmentEffectComponents.TICK, mobEffect(e.mobEffects(MobEffects.ABSORPTION), constant(11f), constant(11f),
                linear(0f, 1f), linear(0f, 1f)), entityProperties("this", entity().periodicTick(200)));
        e.register(builder, ABSORBENT_SHIELD, 0xFF55FF, "absorbent_shield");
    }

    /** 战斗回响：盾牌格挡后，提升下一次攻击的伤害 */
    private static void echoesBattle(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#c:tools/shield", "#c:tools/shield", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "offhand");
        e.register(builder, ECHOES_BATTLE, 0xFF55FF, "echoes_battle");
    }

    /** 盾甲：副手持盾时增加护甲值 */
    private static void shieldArmor(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#c:tools/shield", "#c:tools/shield", 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "offhand");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.hardened_1", e.attribute("minecraft:generic.armor"),
                EnchantmentConstructorHelper.operation("add_value"), linear(2.5f, 1.5f))
        );
        e.register(builder, SHIELD_ARMOR, 0xFF55FF, "shield_armor");
    }

    /** 抗退盾牌：副手持盾时免疫击退 */
    private static void stableShield(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#c:tools/shield", "#c:tools/shield", 2, 2,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "offhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_SHIELD));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.stalwart_1", e.attribute("minecraft:generic.knockback_resistance"),
                EnchantmentConstructorHelper.operation("add_value"), constant(1f))
        );
        e.register(builder, STABLE_SHIELD, 0xFFAA00, "stable_shield");
    }

    /** 力量之盾：被击中时有几率获得力量效果 */
    private static void strengthShield(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#c:tools/shield", "#c:tools/shield", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "offhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_SHIELD));
        e.postAttack(builder, target("victim"), target("victim"),
                mobEffect(e.mobEffects(MobEffects.DAMAGE_BOOST), linear(2f, 1f), linear(4f, 3f), linear(0f, 1f), linear(0f, 1f)),
                randomChance(levelNumber(linear(0.1f, 0.2f)))
        );
        e.register(builder, STRENGTH_SHIELD, 0xFF55FF, "strength_shield");
    }

}
