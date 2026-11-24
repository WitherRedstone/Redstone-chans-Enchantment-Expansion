package com.chinaex123.redstone_enchants.init;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public interface REEntityTypeTags {

    /** 免疫必杀类附魔的实体（被杀后也不会触发效果） */
    TagKey<EntityType<?>> BLACK_ENTITY = bindEntityTypeTag("black_entity");
    /** Boss 生物 */
    TagKey<EntityType<?>> ENTITY_BOSS = bindEntityTypeTag("entity_boss");
    /** 末地生物 */
    TagKey<EntityType<?>> ENTITY_END = bindEntityTypeTag("entity_end");
    /** 灾厄村民 */
    TagKey<EntityType<?>> ENTITY_ILLAGER = bindEntityTypeTag("entity_illager");
    /** 下界生物 */
    TagKey<EntityType<?>> ENTITY_NETHER = bindEntityTypeTag("entity_nether");
    /** 投射物（箭、三叉戟） */
    TagKey<EntityType<?>> ENTITY_PROJECTILE = bindEntityTypeTag("entity_projectile");
    /** 水生生物 */
    TagKey<EntityType<?>> ENTITY_WATER = bindEntityTypeTag("entity_water");
    /** 可被驱魔的生物 */
    TagKey<EntityType<?>> EXORCISM = bindEntityTypeTag("exorcism");
    /** 硬质投射物（不可被击落的投射物） */
    TagKey<EntityType<?>> HARD_PROJECTILES = bindEntityTypeTag("hard_projectiles");
    /** 非生物实体 */
    TagKey<EntityType<?>> NON_LIVING = bindEntityTypeTag("non_living");
    /** 所有投射物 */
    TagKey<EntityType<?>> PROJECTILES = bindEntityTypeTag("projectiles");
    /** 可骑乘生物 */
    TagKey<EntityType<?>> RIDEABLE = bindEntityTypeTag("rideable");

    private static TagKey<EntityType<?>> bindEntityTypeTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(RedstoneEnchants.MODID, name));
    }
}
