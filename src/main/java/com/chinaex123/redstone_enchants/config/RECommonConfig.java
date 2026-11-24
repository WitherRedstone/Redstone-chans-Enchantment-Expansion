package com.chinaex123.redstone_enchants.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class RECommonConfig {
    public static final ModConfigSpec SPEC;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("[诅咒]类型附魔").push("[Curse]Type Enchantments");
        builder.pop();


        builder.comment("[通用]类型附魔").push("[Unbreaking]Type Enchantments");
        builder.pop();


        builder.comment("[武器]类型附魔").push("[Weapon]Type Enchantments");
        builder.pop();


        builder.comment("[剑]类型附魔").push("[Swords]Type Enchantments");
        builder.pop();


        builder.comment("[弓]类型附魔").push("[Bow]Type Enchantments");
        builder.pop();


        builder.comment("[三叉戟]类型附魔").push("[Trident]Type Enchantments");
        builder.pop();


        builder.comment("[重锤]类型附魔").push("[Mace]Type Enchantments");
        builder.pop();


        builder.comment("[工具]类型附魔").push("[Tool]Type Enchantments");
        builder.pop();


        builder.comment("[特殊工具]类型附魔").push("[Special Tools]Type Enchantments");
        builder.pop();


        builder.comment("[剪刀]类型附魔").push("[Shear]Type Enchantments");
        builder.pop();


        builder.comment("[刷子]类型附魔").push("[Brush]Type Enchantments");
        builder.pop();


        builder.comment("[打火石]类型附魔").push("[Flint and Steel]Type Enchantments");
        builder.pop();


        builder.comment("[鱼竿]类型附魔").push("[Fishing]Type Enchantments");
        builder.pop();


        builder.comment("[盾牌]类型附魔").push("[Shield]Type Enchantments");
        builder.pop();


        builder.comment("[武器+工具]类型附魔").push("[Weapon+Tool]Type Enchantments");
        builder.pop();


        builder.comment("[剑+弓]类型附魔").push("[Sword+Bow]Type Enchantments");
        builder.pop();


        builder.comment("[三叉戟+弓]类型附魔").push("[Trident+Bow]Type Enchantments");
        builder.pop();


        builder.comment("[鞘翅]类型附魔").push("[Elytra]Type Enchantments");
        builder.pop();


        builder.comment("[护甲通用]类型附魔").push("[Armor]Type Enchantments");
        builder.pop();


        builder.comment("[头盔]类型附魔").push("[Helmet]Type Enchantments");
        builder.pop();


        builder.comment("[胸甲]类型附魔").push("[Chestplate]Type Enchantments");
        builder.pop();


        builder.comment("[护腿]类型附魔").push("[Leggings]Type Enchantments");
        builder.pop();


        builder.comment("[靴子]类型附魔").push("[Boots]Type Enchantments");
        builder.pop();


        builder.comment("[马铠]类型附魔").push("[Horse Armor]Type Enchantments");
        builder.pop();


        builder.comment("[马铠+狼铠]类型附魔").push("[Horse+Wolf Armor]Type Enchantments");
        builder.pop();


        builder.comment("[狼铠]类型附魔").push("[Wolf Armor]Type Enchantments");
        builder.pop();


        SPEC = builder.build();
    }
}
