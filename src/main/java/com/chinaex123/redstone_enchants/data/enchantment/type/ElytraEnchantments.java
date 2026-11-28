package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import com.chinaex123.redstone_enchants.init.REItemTags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;

import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.*;

/**
 * [鞘翅]类型附魔
 * <p>
 * 只能附魔在鞘翅上
 */
public final class ElytraEnchantments {
    /** 末影之心：飞行时获得生命恢复(2级时增加最大生命值) */
    public static final ResourceKey<Enchantment> ENDER_HEART = EnchantmentConstructorHelper.key("ender_heart");
    /** 月球漫步：降低飞行时的重力影响 */
    public static final ResourceKey<Enchantment> MOONWALK = EnchantmentConstructorHelper.key("moonwalk");
    /** 安全着陆：提升安全坠落高度(最高+12格) */
    public static final ResourceKey<Enchantment> SAFE_LANDING = EnchantmentConstructorHelper.key("safe_landing");

    private ElytraEnchantments() {
    }

    /**
     * [鞘翅]类型附魔
     * <p>
     * 只能附魔在鞘翅上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        enderHeart(e);
        moonwalk(e);
        safeLanding(e);
    }

    /** 末影之心：飞行时获得生命恢复(2级时增加最大生命值) */
    private static void enderHeart(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ELYTRA, REItemTags.ELYTRA, 3, 2,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "chest");
        e.entity(builder, EnchantmentEffectComponents.TICK, mobEffect(e.mobEffects(MobEffects.REGENERATION), constant(3f), constant(3f), linear(0f, 1f), linear(0f, 1f)), entityProperties("this", entity().periodicTick(40)));
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.ender_heart_1", e.attribute("minecraft:generic.max_health"),
                EnchantmentConstructorHelper.operation("add_value"), linear(0f, 2f))
        );
        e.register(builder, ENDER_HEART, 0xFF55FF, "ender_heart");
    }

    /** 月球漫步：降低飞行时的重力影响 */
    private static void moonwalk(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ELYTRA, REItemTags.ELYTRA, 3, 3,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "chest");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantment.moonwalk_1", e.attribute("minecraft:generic.gravity"),
                EnchantmentConstructorHelper.operation("add_value"), linear(-0.03f, -0.015f))
        );
        e.register(builder, MOONWALK, 0xFF55FF, "moonwalk");
    }

    /** 安全着陆：提升安全坠落高度(最高+12格) */
    private static void safeLanding(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder(REItemTags.ELYTRA, REItemTags.ELYTRA, 3, 5,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "chest");
        e.attributes(builder, attributeEffect("redstone_enchants:enchantencore.safe_landing_1", e.attribute("minecraft:generic.safe_fall_distance"),
                EnchantmentConstructorHelper.operation("add_value"), linear(2f, 2.5f))
        );
        e.register(builder, SAFE_LANDING, 0xFF55FF, "safe_landing");
    }

}
