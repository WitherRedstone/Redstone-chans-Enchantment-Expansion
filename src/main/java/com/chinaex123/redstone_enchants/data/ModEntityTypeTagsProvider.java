package com.chinaex123.redstone_enchants.data;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.init.REEntityTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagsProvider extends IntrinsicHolderTagsProvider<EntityType<?>> {
    public ModEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.ENTITY_TYPE, lookupProvider, entityType -> entityType.builtInRegistryHolder().key(),
                RedstoneEnchants.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(REEntityTypeTags.BLACK_ENTITY)
                .add(EntityType.PLAYER)
                .add(EntityType.WITHER)
                .add(EntityType.ENDER_DRAGON)
                .add(EntityType.WARDEN);
        tag(REEntityTypeTags.ENTITY_BOSS)
                .add(EntityType.WARDEN)
                .add(EntityType.WITHER)
                .add(EntityType.ELDER_GUARDIAN)
                .add(EntityType.ENDER_DRAGON);
        tag(REEntityTypeTags.ENTITY_END)
                .add(EntityType.ENDERMAN)
                .add(EntityType.SHULKER);
        tag(REEntityTypeTags.ENTITY_ILLAGER)
                .add(EntityType.EVOKER)
                .add(EntityType.ILLUSIONER)
                .add(EntityType.PILLAGER)
                .add(EntityType.VINDICATOR)
                .add(EntityType.RAVAGER);
        tag(REEntityTypeTags.ENTITY_NETHER)
                .add(EntityType.BLAZE)
                .add(EntityType.WITHER_SKELETON)
                .add(EntityType.STRIDER)
                .add(EntityType.ZOMBIFIED_PIGLIN)
                .add(EntityType.PIGLIN)
                .add(EntityType.PIGLIN_BRUTE)
                .add(EntityType.ENDERMAN)
                .add(EntityType.ZOGLIN)
                .add(EntityType.HOGLIN)
                .add(EntityType.GHAST);
        tag(REEntityTypeTags.ENTITY_PROJECTILE)
                .addTag(EntityTypeTags.ARROWS)
                .add(EntityType.TRIDENT);
        tag(REEntityTypeTags.ENTITY_WATER)
                .add(EntityType.AXOLOTL)
                .add(EntityType.COD)
                .add(EntityType.DOLPHIN)
                .add(EntityType.ELDER_GUARDIAN)
                .add(EntityType.GLOW_SQUID)
                .add(EntityType.GUARDIAN)
                .add(EntityType.PUFFERFISH)
                .add(EntityType.SQUID)
                .add(EntityType.TADPOLE)
                .add(EntityType.TROPICAL_FISH)
                .add(EntityType.TURTLE);
        tag(REEntityTypeTags.EXORCISM)
                .add(EntityType.VEX)
                .add(EntityType.ALLAY)
                .add(EntityType.BLAZE)
                .add(EntityType.BREEZE)
                .add(EntityType.WITCH)
                .add(EntityType.EVOKER);
        tag(REEntityTypeTags.HARD_PROJECTILES)
                .add(EntityType.FIREWORK_ROCKET)
                .add(EntityType.ARROW)
                .add(EntityType.SPECTRAL_ARROW)
                .add(EntityType.TRIDENT);
        tag(REEntityTypeTags.NON_LIVING)
                .add(EntityType.ITEM)
                .add(EntityType.ITEM_DISPLAY)
                .add(EntityType.ITEM_FRAME)
                .add(EntityType.PAINTING)
                .add(EntityType.GLOW_ITEM_FRAME)
                .add(EntityType.ARMOR_STAND)
                .add(EntityType.MARKER)
                .add(EntityType.INTERACTION)
                .add(EntityType.SHULKER_BULLET)
                .add(EntityType.FIREBALL)
                .add(EntityType.FIREWORK_ROCKET)
                .add(EntityType.FISHING_BOBBER)
                .add(EntityType.WITHER_SKULL)
                .add(EntityType.ARROW)
                .add(EntityType.DRAGON_FIREBALL)
                .add(EntityType.SPECTRAL_ARROW)
                .add(EntityType.TEXT_DISPLAY)
                .add(EntityType.BOAT)
                .add(EntityType.BLOCK_DISPLAY)
                .add(EntityType.CHEST_BOAT)
                .add(EntityType.MINECART)
                .add(EntityType.CHEST_MINECART)
                .add(EntityType.FURNACE_MINECART)
                .add(EntityType.HOPPER_MINECART)
                .add(EntityType.SPAWNER_MINECART)
                .add(EntityType.AREA_EFFECT_CLOUD)
                .add(EntityType.COMMAND_BLOCK_MINECART)
                .add(EntityType.TNT_MINECART);
        tag(REEntityTypeTags.PROJECTILES)
                .addTag(EntityTypeTags.ARROWS)
                .add(EntityType.TRIDENT);
        tag(REEntityTypeTags.RIDEABLE)
                .add(EntityType.STRIDER)
                .add(EntityType.PIG)
                .add(EntityType.HORSE)
                .add(EntityType.CAMEL)
                .add(EntityType.DONKEY)
                .add(EntityType.MULE)
                .add(EntityType.SKELETON_HORSE)
                .add(EntityType.ZOMBIE_HORSE);
    }
}
