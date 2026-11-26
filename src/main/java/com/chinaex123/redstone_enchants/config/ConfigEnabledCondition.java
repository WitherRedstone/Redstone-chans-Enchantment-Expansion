package com.chinaex123.redstone_enchants.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.neoforged.neoforge.common.conditions.ICondition;

/**
 * 配置启用条件。
 * <p>
 * 用于数据包条件判断：当指定附魔在配置中处于启用状态时条件成立，
 * 可据此按配置开关决定对应数据条目是否加载。
 *
 * @param enchantment 附魔标识
 */
public record ConfigEnabledCondition(String enchantment) implements ICondition {

    /** 条件编解码器。 */
    public static final MapCodec<ConfigEnabledCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.STRING.fieldOf("enchantment").forGetter(ConfigEnabledCondition::enchantment))
            .apply(instance, ConfigEnabledCondition::new));

    /**
     * 测试条件是否成立。
     *
     * @param context 条件上下文
     * @return 该附魔在配置中启用时返回 true
     */
    @Override
    public boolean test(IContext context) {
        return RECommonConfig.isEnabled(enchantment);
    }

    /**
     * 获取条件编解码器。
     *
     * @return 条件编解码器
     */
    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }

    /**
     * 获取条件的字符串表示。
     *
     * @return 字符串表示
     */
    @Override
    public String toString() {
        return "config_enabled(\"" + enchantment + "\")";
    }
}