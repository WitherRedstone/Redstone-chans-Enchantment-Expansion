package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;

import com.chinaex123.redstone_enchants.init.REEnchantmentTags;
import com.chinaex123.redstone_enchants.init.REEntityTypeTags;
import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.*;

/**
 * [三叉戟]类型附魔
 * <p>
 * 只能附魔在三叉戟上
 */
public final class TridentEnchantments {
    /** 水生克星：对海洋生物造成额外伤害 */
    public static final ResourceKey<Enchantment> BANE_WATER = EnchantmentConstructorHelper.key("bane_water");
    /** 海风：命中时产生推进风弹 */
    public static final ResourceKey<Enchantment> SEA_BREEZE = EnchantmentConstructorHelper.key("sea_breeze");
    /** 投掷强化：提升投掷物伤害 */
    public static final ResourceKey<Enchantment> THROWING_ENHANCEMENT = EnchantmentConstructorHelper.key("throwing_enhancement");
    /** 雷鸣：雨天命中避雷针时引发雷暴 */
    public static final ResourceKey<Enchantment> THUNDERING = EnchantmentConstructorHelper.key("thundering");
    /** 水下爆破：水中攻击时召唤点燃的TNT */
    public static final ResourceKey<Enchantment> UNDERWATER_BLASTING = EnchantmentConstructorHelper.key("underwater_blasting");

    private TridentEnchantments() {
    }

    /**
     * [三叉戟]类型附魔
     * <p>
     * 只能附魔在三叉戟上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        baneWater(e);
        seaBreeze(e);
        throwingEnhancement(e);
        thundering(e);
        underwaterBlasting(e);
    }

    /** 水生克星：对海洋生物造成额外伤害 */
    private static void baneWater(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#minecraft:enchantable/trident", "#minecraft:enchantable/trident", 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_BANE));
        e.value(builder, EnchantmentEffectComponents.DAMAGE, add(linear(2.5f, 2.5f)), entityProperties("this", e.entityOf(REEntityTypeTags.ENTITY_WATER)));
        e.postAttack(builder, target("attacker"), target("victim"),
                mobEffect(e.mobEffects(MobEffects.MOVEMENT_SLOWDOWN), constant(1.5f), linear(1.5f, 0.5f), constant(3f), constant(3f)),
                allOf(entityProperties("this", e.entityOf(REEntityTypeTags.ENTITY_WATER)), damageSource(damageSourcePredicate().isDirect(true)))
        );
        e.register(builder, BANE_WATER, 0xFF55FF, "bane_water");
    }

    /** 海风：命中时产生推进风弹 */
    private static void seaBreeze(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#minecraft:enchantable/trident", "#minecraft:enchantable/trident", 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_NO_SEA_BREEZE));
        e.register(builder, SEA_BREEZE, 0xFF55FF, "sea_breeze");
    }

    /** 投掷强化：提升投掷物伤害 */
    private static void throwingEnhancement(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#minecraft:enchantable/trident", "#minecraft:enchantable/trident", 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        e.value(builder, EnchantmentEffectComponents.DAMAGE, add(linear(3f, 1.5f)),
                entityProperties("direct_attacker", e.entityOf("minecraft:trident"))
        );
        e.register(builder, THROWING_ENHANCEMENT, 0xFF55FF, "throwing_enhancement");
    }

    /** 雷鸣：雨天命中避雷针时引发雷暴 */
    private static void thundering(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#minecraft:enchantable/trident", "#minecraft:enchantable/trident", 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        e.entity(builder, EnchantmentEffectComponents.HIT_BLOCK, allEntities(summon(e.entityTypes("minecraft:lightning_bolt"), false),
                playSound(e.sound("minecraft:item.trident.thunder"), constantFloat(5.0f), constantFloat(1.0f))),
                allOf(weatherCheck(true, null), entityProperties("this", e.entityOf("minecraft:trident")),
                        locationCheck(location().setCanSeeSky(true)), blockStateProperty(e.block("minecraft:lightning_rod").value())));
        e.register(builder, THUNDERING, 0xFF55FF, "thundering");
    }

    /** 水下爆破：水中攻击时召唤点燃的TNT */
    private static void underwaterBlasting(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#minecraft:enchantable/trident", "#minecraft:enchantable/trident", 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments(REEnchantmentTags.EXCLUSIVE_SET_NO_SEA_BREEZE));
        e.postAttack(builder, target("attacker"), target("victim"), summon(e.entityTypes("minecraft:tnt"), false),
                req(randomChance(constantNumber(0.5f)), entityProperties("this", entity().located(location().setBlock(locBlockBuilder("minecraft:water")))))
        );
        e.register(builder, UNDERWATER_BLASTING, 0xFF55FF, "underwater_blasting");
    }

}
