package com.chinaex123.redstone_enchants.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

/**
 * 保留附魔的受损反馈。
 * <p>
 * 物品耐久耗尽时播放一次损坏音效与物品碎裂粒子作为提示，
 * 并在物品自定义数据中记录已提示标记，避免重复播放。
 */
public final class PreservationFeedback {

    /** 自定义数据中记录已提示的键。 */
    public static final String WARNED_KEY = "redstone_enchants:preservation_warned";

    private PreservationFeedback() {
    }

    /**
     * 判断物品是否已经提示过。
     *
     * @param stack 物品堆叠
     * @return 已提示返回 true
     */
    public static boolean hasWarned(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag()
                .getBoolean(WARNED_KEY);
    }

    /**
     * 清除已提示标记。
     *
     * @param stack 物品堆叠
     */
    public static void clearWarned(ItemStack stack) {
        if (hasWarned(stack)) {
            CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.remove(WARNED_KEY));
        }
    }

    /**
     * 按需播放受损提示。
     * <p>
     * 仅在实体存在、物品可损坏且耐久已耗尽、尚未提示过、且处于服务端时生效：
     * 写入已提示标记，播放物品损坏音效并在实体上方生成物品碎裂粒子。
     *
     * @param stack  物品堆叠
     * @param entity 持有实体
     */
    public static void warnIfNeeded(ItemStack stack, LivingEntity entity) {
        if (entity == null || stack.isEmpty()) return;
        if (!stack.isDamageableItem()) return;
        if (stack.getDamageValue() < stack.getMaxDamage()) return;
        if (hasWarned(stack)) return;
        if (!(entity.level() instanceof ServerLevel serverLevel)) return;

        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putBoolean(WARNED_KEY, true));
        serverLevel.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
        serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack),
                entity.getX(), entity.getY() + 1.5D, entity.getZ(),
                5, 0.25D, 0.25D, 0.25D, 0.05D);
    }
}