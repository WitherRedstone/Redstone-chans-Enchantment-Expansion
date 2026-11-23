package com.chinaex123.redstone_enchants.init;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public interface REItemTags {

    /** [最后的希望]可用武器 */
    TagKey<Item> LAST_HOPE_WEAPONS = bindBlockTag("last_hope_weapons");

    /** 所有弓 */
    TagKey<Item> ALL_BOW = bindBlockTag("all_bow");
    /** 所有刷子 */
    TagKey<Item> ALL_BRUSH = bindBlockTag("all_brush");
    /** 所有钓竿 */
    TagKey<Item> ALL_FISHING = bindBlockTag("all_fishing");
    /** 所有打火石 */
    TagKey<Item> ALL_FLINT_AND_STEEL = bindBlockTag("all_flint_and_steel");
    /** 所有剪刀 */
    TagKey<Item> ALL_SHEAR = bindBlockTag("all_shear");
    /** 所有特殊工具（剪刀、打火石、刷子） */
    TagKey<Item> ALL_SPECIAL_TOOLS = bindBlockTag("all_special_tools");
    /** 所有工具（常规工具 + 剑 + 弓 + 三叉戟 + 重锤 + 特殊工具） */
    TagKey<Item> ALL_TOOLS = bindBlockTag("all_tools");
    /** 常规工具（镐、斧、锹、锄） */
    TagKey<Item> TOOLS = bindBlockTag("tools");
    /** 所有武器（剑、斧、三叉戟、重锤、弓、弩） */
    TagKey<Item> ALL_WEAPON = bindBlockTag("all_weapon");
    /** 剑 */
    TagKey<Item> SWORDS = bindBlockTag("swords");
    /** 剑与斧 */
    TagKey<Item> SWORDS_AND_AXES = bindBlockTag("swords_and_axes");
    /** 剑与弓 */
    TagKey<Item> SWORDS_AND_BOW = bindBlockTag("swords_and_bow");
    /** 三叉戟与弓 */
    TagKey<Item> TRIDENT_AND_BOW = bindBlockTag("trident_and_bow");
    /** 武器（剑、斧、弓、弩） */
    TagKey<Item> WEAPON = bindBlockTag("weapon");
    /** 所有盔甲（头、胸、腿、脚） */
    TagKey<Item> ARMORS = bindBlockTag("armors");
    /** 头盔类 */
    TagKey<Item> ARMORS_HEAD = bindBlockTag("armors_head");
    /** 胸甲类 */
    TagKey<Item> ARMORS_CHEST = bindBlockTag("armors_chest");
    /** 护腿 */
    TagKey<Item> ARMORS_LEG = bindBlockTag("armors_leg");
    /** 靴子 */
    TagKey<Item> ARMORS_FOOT = bindBlockTag("armors_foot");
    /** 鞘翅 */
    TagKey<Item> ELYTRA = bindBlockTag("elytra");
    /** 动物盔甲 */
    TagKey<Item> HORSE_ANIMAL_ARMOR = bindBlockTag("horse_animal_armor");
    /** 马铠 */
    TagKey<Item> HORSE_ARMOR = bindBlockTag("horse_armor");
    /** 狼铠 */
    TagKey<Item> WOLF_ARMOR = bindBlockTag("wolf_armor");

    private static TagKey<Item> bindBlockTag(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(RedstoneEnchants.MODID, name));
    }
}
