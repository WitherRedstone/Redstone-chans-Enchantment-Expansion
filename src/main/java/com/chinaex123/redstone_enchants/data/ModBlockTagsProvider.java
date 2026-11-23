package com.chinaex123.redstone_enchants.data;

import java.util.concurrent.CompletableFuture;

import com.chinaex123.redstone_enchants.init.REBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import com.chinaex123.redstone_enchants.RedstoneEnchants;

import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, RedstoneEnchants.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(REBlockTags.BEDROCK_BREAKER)
                .add(Blocks.BEDROCK);
    }
}
