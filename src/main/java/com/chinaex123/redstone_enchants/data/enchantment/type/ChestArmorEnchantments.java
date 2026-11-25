package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;

import com.chinaex123.redstone_enchants.init.REItemTags;
import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.*;

/**
 * [胸甲]类型附魔
 * <p>
 * 只能附魔在胸甲上
 */
public final class ChestArmorEnchantments {
    /** 狂战士：根据损失的生命值百分比增加近战伤害 */
    public static final ResourceKey<Enchantment> BERSERK = EnchantmentConstructorHelper.key("berserk");
    /** 防弹：有概率免疫潜影子弹伤害 */
    public static final ResourceKey<Enchantment> BULLETPROOF = EnchantmentConstructorHelper.key("bulletproof");
    /** 双重暴击：提升暴击率和暴击伤害 */
    public static final ResourceKey<Enchantment> DUAL_CRITICAL_BOOST = EnchantmentConstructorHelper.key("dual_critical_boost");
    /** 冰霜荆棘：被攻击时使攻击者获得缓慢/虚弱效果 */
    public static final ResourceKey<Enchantment> FROST_THORN = EnchantmentConstructorHelper.key("frost_thorn");
    /** 坚毅壁垒：提升抗性效果 */
    public static final ResourceKey<Enchantment> RESILIENT_BASTION = EnchantmentConstructorHelper.key("resilient_bastion");
    /** 检索：将射向你的箭收入背包 */
    public static final ResourceKey<Enchantment> RETRIEVAL = EnchantmentConstructorHelper.key("retrieval");
    /** 法术防护：增强[铁魔法]的法术抗性 */
    public static final ResourceKey<Enchantment> SPELL_MAGIC_RESIST = EnchantmentConstructorHelper.key("spell_magic_resist");
    /** 法术增幅：增强[铁魔法]的法术强度 */
    public static final ResourceKey<Enchantment> SPELL_POWER = EnchantmentConstructorHelper.key("spell_power");
    /** 活力：提升最大生命值 */
    public static final ResourceKey<Enchantment> VITALITY = EnchantmentConstructorHelper.key("vitality");

    private ChestArmorEnchantments() {
    }

    /**
     * [胸甲]类型附魔
     * <p>
     * 只能附魔在胸甲上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        berserk(e);
        bulletproof(e);
        dualCriticalBoost(e);
        frostThorn(e);
        resilientBastion(e);
        retrieval(e);
        spellMagicResist(e);
        spellPower(e);
        vitality(e);
    }

    /** 狂战士：根据损失的生命值百分比增加近战伤害 */
    private static void berserk(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_CHEST, REItemTags.ARMORS_CHEST, 2, 5,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "chest");
        e.register(builder, BERSERK, 0xFFAA00, "berserk");
    }

    /** 防弹：有概率免疫潜影子弹伤害 */
    private static void bulletproof(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_CHEST, REItemTags.ARMORS_CHEST, 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "chest");
        e.register(builder, BULLETPROOF, 0xFF55FF, "bulletproof");
    }

    /** 双重暴击：提升暴击率和暴击伤害 */
    private static void dualCriticalBoost(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_CHEST, REItemTags.ARMORS_CHEST, 1, 5,
                Enchantment.dynamicCost(18, 8), Enchantment.dynamicCost(48, 18), 16, "chest");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.dual_critical_boost_1", e.attribute("apothic_attributes:crit_chance"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.233f, 0.126f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.dual_critical_boost_2", e.attribute("apothic_attributes:crit_damage"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.582f, 0.286f))
        );
        e.register(builder, DUAL_CRITICAL_BOOST, 0xFF00BB, "dual_critical_boost");
    }

    /** 冰霜荆棘：被攻击时使攻击者获得缓慢/虚弱效果 */
    private static void frostThorn(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_CHEST, REItemTags.ARMORS_CHEST, 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "chest");
        e.postAttack(builder, target("victim"), target("attacker"), allEntities(damageEntity(constant(1f), constant(5f),
                        e.damageType("minecraft:thorns")), playSound(e.sound("minecraft:entity.player.hurt_freeze"), constantFloat(5.0f), uniformFloat(0.6f, 0.8f)), particles(particle("minecraft:snowflake"),
                position("in_bounding_box", 0f, 1f), position("in_bounding_box", 0.5f, 1f),
                velocity(0f, constantFloat(0f)), velocity(0f, constantFloat(0f)), constantFloat(1f)),
                particles(particle("minecraft:snowflake"), position("in_bounding_box", 0f, 1f), position("in_bounding_box", 0.5f, 1f),
                        velocity(0f, constantFloat(0f)), velocity(0f, constantFloat(0f)), constantFloat(1f)),
                particles(particle("minecraft:snowflake"), position("in_bounding_box", 0f, 1f), position("in_bounding_box", 0.5f, 1f),
                        velocity(0f, constantFloat(0f)), velocity(0f, constantFloat(0f)), constantFloat(1f))), null
        );
        e.postAttack(builder, target("victim"), target("victim"), damageItem(linear(0.15f, 0f)), null);
        e.postAttack(builder, target("victim"), target("attacker"), mobEffect(e.mobEffects(MobEffects.MOVEMENT_SLOWDOWN),
                linear(1f, 1f), linear(5f, 5f), linear(0f, 0.25f), linear(1f, 0.5f)), null
        );
        e.entity(builder, EnchantmentEffectComponents.TICK, particles(particle("minecraft:snowflake"),
                position("in_bounding_box", 0f, 2f), position("in_bounding_box", 1f, 2f),
                velocity(0f, constantFloat(0f)), velocity(0f, constantFloat(0.5f)), constantFloat(0.25f)),
                req(entityProperties("this", entity().periodicTick(15)), randomChance(constantNumber(0.75f))));
        e.register(builder, FROST_THORN, 0xFF55FF, "frost_thorn");
    }

    /** 坚毅壁垒：提升抗性效果 */
    private static void resilientBastion(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_CHEST, REItemTags.ARMORS_CHEST, 2, 3,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "chest");
        e.entity(builder, EnchantmentEffectComponents.TICK, mobEffect(e.mobEffects(MobEffects.DAMAGE_RESISTANCE),
                constant(1f), constant(1f), linear(0f, 1f), linear(0f, 1f)), null);
        e.register(builder, RESILIENT_BASTION, 0xFFAA00, "resilient_bastion");
    }

    /** 检索：将射向你的箭收入背包 */
    private static void retrieval(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_CHEST, REItemTags.ARMORS_CHEST, 3, 4,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "chest");
        e.register(builder, RETRIEVAL, 0xFF55FF, "retrieval");
    }

    /** 法术防护：增强[铁魔法]的法术抗性 */
    private static void spellMagicResist(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#minecraft:chest_armor", "#minecraft:chest_armor", 1, 5,
                Enchantment.dynamicCost(18, 8), Enchantment.dynamicCost(48, 18), 16, "chest");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.magic_resist_1", e.attribute("irons_spellbooks:ice_magic_resist"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.1f, 0.1f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.magic_resist_2", e.attribute("irons_spellbooks:blood_magic_resist"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.1f, 0.1f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.magic_resist_3", e.attribute("irons_spellbooks:holy_magic_resist"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.1f, 0.1f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.magic_resist_4", e.attribute("irons_spellbooks:nature_magic_resist"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.1f, 0.1f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.magic_resist_5", e.attribute("irons_spellbooks:eldritch_magic_resist"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.1f, 0.1f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.magic_resist_6", e.attribute("irons_spellbooks:lightning_magic_resist"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.1f, 0.1f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.magic_resist_7", e.attribute("irons_spellbooks:evocation_magic_resist"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.1f, 0.1f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.magic_resist_8", e.attribute("irons_spellbooks:ender_magic_resist"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.1f, 0.1f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.magic_resist_9", e.attribute("irons_spellbooks:fire_magic_resist"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.1f, 0.1f))
        );
        e.register(builder, SPELL_MAGIC_RESIST, 0xFF00BB, "magic_resist");
    }

    /** 法术增幅：增强[铁魔法]的法术强度 */
    private static void spellPower(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#minecraft:chest_armor", "#minecraft:chest_armor", 1, 5,
                Enchantment.dynamicCost(18, 8), Enchantment.dynamicCost(48, 18), 16, "chest");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.spell_power_1", e.attribute("irons_spellbooks:ice_spell_power"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.1f, 0.1f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.spell_power_2", e.attribute("irons_spellbooks:blood_spell_power"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.1f, 0.1f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.spell_power_3", e.attribute("irons_spellbooks:holy_spell_power"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.1f, 0.1f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.spell_power_4", e.attribute("irons_spellbooks:nature_spell_power"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.1f, 0.1f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.spell_power_5", e.attribute("irons_spellbooks:eldritch_spell_power"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.1f, 0.1f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.spell_power_6", e.attribute("irons_spellbooks:lightning_spell_power"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.1f, 0.1f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.spell_power_7", e.attribute("irons_spellbooks:evocation_spell_power"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.1f, 0.1f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.spell_power_8", e.attribute("irons_spellbooks:ender_spell_power"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.1f, 0.1f))
        );
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.spell_power_9", e.attribute("irons_spellbooks:fire_spell_power"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0.1f, 0.1f))
        );
        e.register(builder, SPELL_POWER, 0xFF00BB, "spell_power");
    }

    /** 活力：提升最大生命值 */
    private static void vitality(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ARMORS_CHEST, REItemTags.ARMORS_CHEST, 2, 4,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "chest");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.vitality_1", e.attribute("minecraft:generic.max_health"),
                EnchantmentConstructorHelper.operation("add_multiplied_base"), linear(0.25f, 0.25f))
        );
        e.register(builder, VITALITY, 0xFFAA00, "vitality");
    }

}
