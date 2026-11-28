package com.chinaex123.redstone_enchants.event.enchantment.all_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

/**
 * 绝命箭矢：一击必杀非Boss生物(无掉落战利品，使用后给予DeBuff)。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class FatalArrowEventHandler {

    private static final String ID = "fatal_arrow";

    /** 音爆粒子数量 */
    private static final int PARTICLE_COUNT = 50;
    /** 粒子扩散范围（格） */
    private static final double PARTICLE_SPREAD = 1.0D;
    /** 粒子速度 */
    private static final double PARTICLE_SPEED = 0.5D;
    /** 隐身效果持续时间（tick，-1 为无限） */
    private static final int EFFECT_DURATION_TICKS = -1;
    /** 隐身效果等级（0 为 I 级） */
    private static final int AMPLIFIER = 0;
    /** 实体持久化数据中「本次死亡不掉落战利品」的标记键 */
    private static final String NO_LOOT_KEY = "redstone_enchants.no_loot";

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)) return;
        if (!(arrow.level() instanceof ServerLevel level)) return;
        int enchantLevel = EnchantContext.level(level, ID, arrow.getWeaponItem());
        if (enchantLevel <= 0) return;
        LivingEntity victim = event.getEntity();
        if (victim.getType().is(EnchantContext.BLACK_ENTITY)) return;

        EnchantContext context = new EnchantContext(level, victim, victim.position());
        context.particle(ParticleTypes.SONIC_BOOM, PARTICLE_COUNT, PARTICLE_SPREAD, PARTICLE_SPREAD, PARTICLE_SPREAD, PARTICLE_SPEED);
        context.applyEffect(victim, MobEffects.INVISIBILITY, EFFECT_DURATION_TICKS, AMPLIFIER);
        victim.getPersistentData().putBoolean(NO_LOOT_KEY, true);
        context.clearInventory();
        context.kill(victim);
        arrow.discard();
    }

    /**
     * 绝命箭矢击杀的生物不掉落任何战利品。
     */
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        CompoundTag data = event.getEntity().getPersistentData();
        if (!data.getBoolean(NO_LOOT_KEY)) {
            return;
        }
        data.remove(NO_LOOT_KEY);
        event.setCanceled(true);
    }
}
