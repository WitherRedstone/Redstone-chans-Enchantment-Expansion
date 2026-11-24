package com.chinaex123.redstone_enchants.init;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

public interface REEnchantmentTags {

    /** 全部附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_ALL_ENCHANTS = bindEnchantmentTag("exclusive_set/all_enchants");
    /** 护甲附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_ARMORS = bindEnchantmentTag("exclusive_set/armors");
    /** 头盔附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_ARMORS_HEAD = bindEnchantmentTag("exclusive_set/armors_head");
    /** 光环附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_AURA = bindEnchantmentTag("exclusive_set/aura");
    /** 克星附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_BANE = bindEnchantmentTag("exclusive_set/bane");
    /** 破基岩附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_BEDROCK = bindEnchantmentTag("exclusive_set/bedrock");
    /** 疾驰附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_BOOTS_GALLOP = bindEnchantmentTag("exclusive_set/boots_gallop");
    /** 弓散射附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_BOW_SPREAD = bindEnchantmentTag("exclusive_set/bow_spread");
    /** 伤害附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_DAMAGE = bindEnchantmentTag("exclusive_set/damage");
    /** 克星伤害附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_DAMAGE_BANE = bindEnchantmentTag("exclusive_set/damage_bane");
    /** 弓伤害附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_DAMAGE_BOW = bindEnchantmentTag("exclusive_set/damage_bow");
    /** 头部幸运附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_HEAD_LUCKY = bindEnchantmentTag("exclusive_set/head_lucky");
    /** 不毁附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_INDESTRUCTIBLE = bindEnchantmentTag("exclusive_set/indestructible");
    /** 重锤附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_MACE = bindEnchantmentTag("exclusive_set/mace");
    /** 海风附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_NO_SEA_BREEZE = bindEnchantmentTag("exclusive_set/no_sea_breeze");
    /** 霰弹附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_NO_SHOTGUN = bindEnchantmentTag("exclusive_set/no_shotgun");
    /** 盾牌附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_SHIELD = bindEnchantmentTag("exclusive_set/shield");
    /** 喷溅附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_SPLASH = bindEnchantmentTag("exclusive_set/splash");
    /** 点石成金附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_STONE_TRANSMUTATION = bindEnchantmentTag("exclusive_set/stone_transmutation");
    /** 剑与弓附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_SWORDS_AND_BOW = bindEnchantmentTag("exclusive_set/swords_and_bow");
    /** 触碰附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_TOUCH = bindEnchantmentTag("exclusive_set/touch");
    /** 耐久附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_UNBREAKING = bindEnchantmentTag("exclusive_set/unbreaking");
    /** 行者附魔互斥组 */
    TagKey<Enchantment> EXCLUSIVE_SET_WALKER = bindEnchantmentTag("exclusive_set/walker");

    private static TagKey<Enchantment> bindEnchantmentTag(String name) {
        return TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(RedstoneEnchants.MODID, name));
    }
}
