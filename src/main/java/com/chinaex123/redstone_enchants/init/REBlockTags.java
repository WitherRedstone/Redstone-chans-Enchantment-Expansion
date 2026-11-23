package com.chinaex123.redstone_enchants.init;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public interface REBlockTags {

    /** [基岩破坏者]可破坏的方块 */
    TagKey<Block> BEDROCK_BREAKER = bindBlockTag("bedrock_breaker");

    static TagKey<Block> bindBlockTag(String name) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(RedstoneEnchants.MODID, name));
    }
}
