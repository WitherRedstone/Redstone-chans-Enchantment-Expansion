package com.chinaex123.redstone_enchants.data;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.config.ConfigEnabledCondition;
import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * 模组数据包条件注册类。
 * <p>
 * 向 NeoForge 的条件编解码器注册表注册自定义条件，
 * 使数据包可以使用「配置启用」条件，并按配置开关决定数据条目是否加载。
 */
public final class ModConditions {

    /** 条件编解码器注册器。 */
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS = DeferredRegister
            .create(NeoForgeRegistries.Keys.CONDITION_CODECS, RedstoneEnchants.MODID);

    private ModConditions() {
    }

    /**
     * 注册条件编解码器并挂载到模组事件总线。
     * <p>
     * 注册「配置启用」条件编解码器。
     *
     * @param modEventBus 模组事件总线
     */
    public static void register(IEventBus modEventBus) {
        CONDITION_CODECS.register("config_enabled", () -> ConfigEnabledCondition.CODEC);
        CONDITION_CODECS.register(modEventBus);
    }
}