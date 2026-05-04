package com.chinaex123.redstone_enchants;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(RedstoneEnchants.MOD_ID)
public class RedstoneEnchants {
    public static final String MOD_ID = "redstone_enchants";
    public static final Logger LOGGER = LogUtils.getLogger();

    public RedstoneEnchants(IEventBus modEventBus, ModContainer modContainer) {
    }
}
