package com.chinaex123.redstone_enchants.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import com.chinaex123.redstone_enchants.RedstoneEnchants;

import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModEntityTypeTags extends TagsProvider<EntityType<?>> {

    public ModEntityTypeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
            ExistingFileHelper existingFileHelper) {
        super(output, Registries.ENTITY_TYPE, lookupProvider, RedstoneEnchants.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("redstone_enchants:black_entity")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:player")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:wither")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:ender_dragon")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:warden")));
        this.tag(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("redstone_enchants:entity_boss")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:warden")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:wither")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:elder_guardian")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:ender_dragon")));
        this.tag(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("redstone_enchants:entity_end")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:enderman")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:shulker")));
        this.tag(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("redstone_enchants:entity_illager")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:evoker")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:illusioner")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:pillager")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:vindicator")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:ravager")));
        this.tag(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("redstone_enchants:entity_nether")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:blaze")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:wither_skeleton")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:strider")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:zombified_piglin")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:piglin")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:piglin_brute")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:enderman")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:zoglin")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:hoglin")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:ghast")));
        this.tag(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("redstone_enchants:entity_projectile")))
                .addTag(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:arrows")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:trident")));
        this.tag(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("redstone_enchants:entity_water")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:axolotl")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:cod")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:dolphin")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:elder_guardian")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:glow_squid")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:guardian")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:pufferfish")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:squid")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:tadpole")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:tropical_fish")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:turtle")));
        this.tag(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("redstone_enchants:exorcism")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:vex")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:allay")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:blaze")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:breeze")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:witch")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:evoker")));
        this.tag(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("redstone_enchants:hard_projectiles")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:firework_rocket")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:arrow")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:spectral_arrow")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:trident")));
        this.tag(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("redstone_enchants:non_living")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:item")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:item_display")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:item_frame")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:painting")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:glow_item_frame")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:armor_stand")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:marker")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:interaction")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:shulker_bullet")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:fireball")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:firework_rocket")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:fishing_bobber")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:wither_skull")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:arrow")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:dragon_fireball")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:spectral_arrow")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:text_display")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:boat")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:block_display")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:chest_boat")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:minecart")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:chest_minecart")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:furnace_minecart")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:hopper_minecart")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:spawner_minecart")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:area_effect_cloud")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:command_block_minecart")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:tnt_minecart")));
        this.tag(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("redstone_enchants:projectiles")))
                .addTag(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:arrows")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:trident")));
        this.tag(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("redstone_enchants:rideable")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:strider")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:pig")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:horse")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:camel")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:donkey")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:mule")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:skeleton_horse")))
                .add(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:zombie_horse")));
    }
}
