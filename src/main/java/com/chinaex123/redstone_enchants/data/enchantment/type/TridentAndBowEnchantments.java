package com.chinaex123.redstone_enchants.data.enchantment.type;

import com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;

import static com.chinaex123.redstone_enchants.data.enchantment.EnchantmentConstructorHelper.*;

/**
 * [三叉戟+弓]类型附魔
 * <p>
 * 只能附魔在三叉戟/弓上
 */
public final class TridentAndBowEnchantments {
    /** 永恒冰霜：命中后冻结目标及周围水域并将水转为霜冰 */
    public static final ResourceKey<Enchantment> ETERNAL_FROST = EnchantmentConstructorHelper.key("eternal_frost");
    /** 传送：命中后与目标交换位置 */
    public static final ResourceKey<Enchantment> TELEPORT = EnchantmentConstructorHelper.key("teleport");

    private TridentAndBowEnchantments() {
    }

    /**
     * [三叉戟+弓]类型附魔
     * <p>
     * 只能附魔在三叉戟/弓上
     */
    public static void bootstrap(EnchantmentConstructorHelper e) {
        eternalFrost(e);
        teleport(e);
    }

    /** 永恒冰霜：命中后冻结目标及周围水域并将水转为霜冰 */
    private static void eternalFrost(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:trident_and_bow", "#redstone_enchants:trident_and_bow", 2, 3,
                Enchantment.dynamicCost(16, 8), Enchantment.dynamicCost(32, 16), 12, "mainhand", "offhand");
        builder = builder.exclusiveWith(e.enchantments("#redstone_enchants:exclusive_set/splash"));
        e.postAttack(builder, target("attacker"), target("victim"), mobEffect(e.mobEffects("minecraft:slowness"), linear(1f, 1f), linear(3f, 1f), linear(0f, 1f), linear(1f, 1f)), null);
        e.entity(builder, EnchantmentEffectComponents.HIT_BLOCK, replaceDisk(linear(3f, 2f), constant(1f), vec3i(0, 0, 0), allBlockPredicates(e.blockPredicate("minecraft:air", 0, 1, 0), e.blockPredicate("minecraft:water", 0, 0, 0)), simpleState(e.state("minecraft:frosted_ice", "age", "0")), null), entityProperties("this", e.entityOf("#redstone_enchants:projectiles")));
        e.register(builder, ETERNAL_FROST, 0xFFAA00, "eternal_frost");
    }

    /** 传送：命中后与目标交换位置 */
    private static void teleport(EnchantmentConstructorHelper e) {
        Enchantment.Builder builder = e.builder("#redstone_enchants:trident_and_bow", "#redstone_enchants:trident_and_bow", 3, 1,
                Enchantment.dynamicCost(12, 6), Enchantment.dynamicCost(24, 12), 8, "mainhand", "offhand");
        e.register(builder, TELEPORT, 0xFF55FF, "teleport");
    }

}
