package com.chinaex123.redstone_enchants;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(RedstoneEnchants.MODID)
public class RedstoneEnchants {
    public static final String MODID = "redstone_enchants";
    public static final Logger LOGGER = LogUtils.getLogger();

    public RedstoneEnchants(IEventBus modEventBus, ModContainer modContainer) {
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
