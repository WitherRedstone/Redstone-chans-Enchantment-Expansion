package com.chinaex123.redstone_enchants.event;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 附魔效果登记器。
 * <p>
 * 以弱引用按实体记录其被本模组施加的效果及当时的持续时长与等级；
 * 移除时仅当当前效果与记录一致（等级相同且时长未超出）才真正移除，
 * 避免误删由其他来源施加的同类效果。
 */
public final class EnchantEffectTracker {

    /** 记录的施加信息：持续时间与等级。 */
    private record Tracked(int duration, int amplifier) {
    }

    /** 各实体被本模组施加的效果记录，弱引用避免泄漏。 */
    private static final Map<LivingEntity, Map<Holder<MobEffect>, Tracked>> TRACKED = new WeakHashMap<>();

    private EnchantEffectTracker() {
    }

    /**
     * 施加状态效果并登记。
     * <p>
     * 以不显示粒子与图标的形式添加效果，同时记录持续时长与等级。
     *
     * @param entity    目标实体
     * @param effect    状态效果
     * @param duration  持续时长（刻）
     * @param amplifier 效果等级
     */
    public static void apply(LivingEntity entity, Holder<MobEffect> effect, int duration, int amplifier) {
        entity.addEffect(new MobEffectInstance(effect, duration, amplifier, false, false));
        TRACKED.computeIfAbsent(entity, key -> new HashMap<>()).put(effect, new Tracked(duration, amplifier));
    }

    /**
     * 判断指定效果是否由本模组登记施加。
     *
     * @param entity 目标实体
     * @param effect 状态效果
     * @return 已登记返回 true
     */
    public static boolean isTracked(LivingEntity entity, Holder<MobEffect> effect) {
        Map<Holder<MobEffect>, Tracked> tracked = TRACKED.get(entity);
        return tracked != null && tracked.containsKey(effect);
    }

    /**
     * 若为登记的效果则移除。
     * <p>
     * 仅当实体当前该效果的等级与记录一致、且剩余时长不超过记录时长时才移除，
     * 以区分其他来源施加的同类效果。
     *
     * @param entity 目标实体
     * @param effect 状态效果
     */
    public static void removeIfTracked(LivingEntity entity, Holder<MobEffect> effect) {
        Map<Holder<MobEffect>, Tracked> tracked = TRACKED.get(entity);
        if (tracked == null) return;

        Tracked applied = tracked.remove(effect);
        if (applied == null) return;

        MobEffectInstance current = entity.getEffect(effect);
        if (current == null) return;

        if (current.getAmplifier() == applied.amplifier() && current.getDuration() <= applied.duration()) {
            entity.removeEffect(effect);
        }
    }

    /**
     * 清除实体的全部登记记录。
     *
     * @param entity 目标实体
     */
    public static void forget(LivingEntity entity) {
        TRACKED.remove(entity);
    }
}