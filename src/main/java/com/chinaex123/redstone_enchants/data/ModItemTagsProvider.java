package com.chinaex123.redstone_enchants.data;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.init.REItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, RedstoneEnchants.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        tag(REItemTags.LAST_HOPE_WEAPONS)
                .add(Items.NETHERITE_SWORD)
                .add(Items.NETHERITE_AXE);

        tag(REItemTags.ALL_BOW)
                .addTag(Tags.Items.TOOLS_BOW)
                .addTag(Tags.Items.TOOLS_CROSSBOW);
        tag(REItemTags.ALL_BRUSH)
                .addTag(Tags.Items.TOOLS_BRUSH);
        tag(REItemTags.ALL_FISHING)
                .addOptionalTag(Tags.Items.TOOLS_FISHING_ROD);
        tag(REItemTags.ALL_FLINT_AND_STEEL)
                .addTag(Tags.Items.TOOLS_IGNITER);
        tag(REItemTags.ALL_SHEAR)
                .addTag(Tags.Items.TOOLS_SHEAR);
        tag(REItemTags.ALL_SPECIAL_TOOLS)
                .addTag(Tags.Items.TOOLS_SHEAR)
                .addTag(Tags.Items.TOOLS_IGNITER)
                .addTag(Tags.Items.TOOLS_BRUSH);
        tag(REItemTags.ALL_TOOLS)
                .addTag(ItemTags.SWORDS)
                .addTag(REItemTags.TOOLS)
                .add(Items.TRIDENT)
                .addTag(Tags.Items.TOOLS_BOW)
                .addTag(Tags.Items.TOOLS_MACE)
                .addTag(REItemTags.ALL_SPECIAL_TOOLS);
        tag(REItemTags.TOOLS)
                .addTag(ItemTags.PICKAXES)
                .addTag(ItemTags.AXES)
                .addTag(ItemTags.SHOVELS)
                .addTag(ItemTags.HOES);
        tag(REItemTags.ALL_WEAPON)
                .addTag(REItemTags.SWORDS_AND_AXES)
                .addTag(ItemTags.TRIDENT_ENCHANTABLE)
                .addTag(Tags.Items.TOOLS_MACE)
                .addTag(REItemTags.ALL_BOW);
        tag(REItemTags.SWORDS)
                .addTag(ItemTags.SWORDS);
        tag(REItemTags.SWORDS_AND_AXES)
                .addTag(ItemTags.SWORDS)
                .addTag(ItemTags.AXES);
        tag(REItemTags.SWORDS_AND_BOW)
                .addTag(REItemTags.SWORDS)
                .addTag(REItemTags.ALL_BOW);
        tag(REItemTags.TRIDENT_AND_BOW)
                .addTag(ItemTags.TRIDENT_ENCHANTABLE)
                .addTag(REItemTags.ALL_BOW);
        tag(REItemTags.WEAPON)
                .addTag(REItemTags.SWORDS_AND_AXES)
                .addTag(REItemTags.ALL_BOW);
        tag(REItemTags.ARMORS)
                .addTag(REItemTags.ARMORS_HEAD)
                .addTag(REItemTags.ARMORS_CHEST)
                .addTag(REItemTags.ARMORS_LEG)
                .addTag(REItemTags.ARMORS_FOOT);
        tag(REItemTags.ARMORS_HEAD)
                .addTag(ItemTags.HEAD_ARMOR)
                .add(Items.CARVED_PUMPKIN)
                .addTag(ItemTags.SKULLS);
        tag(REItemTags.ARMORS_CHEST)
                .addTag(ItemTags.CHEST_ARMOR)
                .addTag(REItemTags.ELYTRA);
        tag(REItemTags.ARMORS_LEG)
                .addTag(ItemTags.LEG_ARMOR);
        tag(REItemTags.ARMORS_FOOT)
                .addTag(ItemTags.FOOT_ARMOR);
        tag(REItemTags.ELYTRA)
                .add(Items.ELYTRA);
        tag(REItemTags.HORSE_ANIMAL_ARMOR)
                .addTag(REItemTags.HORSE_ARMOR)
                .addTag(REItemTags.WOLF_ARMOR);
        tag(REItemTags.HORSE_ARMOR)
                .add(Items.DIAMOND_HORSE_ARMOR)
                .add(Items.IRON_HORSE_ARMOR)
                .add(Items.GOLDEN_HORSE_ARMOR)
                .add(Items.LEATHER_HORSE_ARMOR);
        tag(REItemTags.WOLF_ARMOR)
                .add(Items.WOLF_ARMOR);
    }
}
