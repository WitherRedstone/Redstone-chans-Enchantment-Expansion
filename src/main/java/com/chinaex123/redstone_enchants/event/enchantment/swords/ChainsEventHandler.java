package com.chinaex123.redstone_enchants.event.enchantment.swords;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 锁链：击中生物会导致自身和附近的多个生物在短时间内被眩晕。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class ChainsEventHandler {

    private static final String ID = "chains";

    /** 主目标身上的锁链持续秒数。 */
    private static final float MAIN_SECONDS = 6.0F;
    /** 传递目标身上的锁链持续秒数。 */
    private static final float CHAIN_SECONDS = 3.0F;
    /** 锁链传递的额外目标数量。 */
    private static final int CHAIN_TARGETS = 3;
    /** 锁链传递半径。 */
    private static final double CHAIN_RADIUS = 8.0D;
    /** 1 级时的触发概率。 */
    private static final double CHANCE_BASE = 0.2D;
    /** 每级额外增加的触发概率。 */
    private static final double CHANCE_PER_LEVEL = 0.2D;
    /** 锁链粒子相对目标的高度偏移。 */
    private static final double PARTICLE_HEIGHT_OFFSET = 1.0D;
    /** 锁链粒子相对目标的水平偏移（格，不做水平偏移）。 */
    private static final double PARTICLE_HORIZONTAL_OFFSET = 0.0D;
    /** 锁链粒子数量。 */
    private static final int PARTICLE_COUNT = 8;
    /** 锁链粒子水平扩散范围。 */
    private static final double PARTICLE_SPREAD = 0.2D;
    /** 锁链粒子上抛速度。 */
    private static final double PARTICLE_Y_SPEED = 0.5D;
    /** 锁链粒子垂直扩散范围。 */
    private static final double PARTICLE_Y_SPREAD = 0.2D;
    /** 锁链粒子基础速度。 */
    private static final double PARTICLE_SPEED = 0.0D;
    /** 锁链音效音量。 */
    private static final float SOUND_VOLUME = 1.0F;
    /** 锁链音效音高。 */
    private static final float SOUND_PITCH = 1.0F;
    /** 缓慢与虚弱的效果等级（9 即 X 级）。 */
    private static final int EFFECT_AMPLIFIER = 9;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!(attacker.level() instanceof ServerLevel level)) return;
        LivingEntity victim = event.getEntity();

        int enchantLevel = EnchantContext.level(attacker, ID);
        if (enchantLevel <= 0) return;

        // 只对直接攻击生效
        if (event.getSource().getDirectEntity() != attacker) return;
        // 1 级 20% 触发几率，每级额外 +20%
        double chance = Math.min(1.0D, CHANCE_BASE + CHANCE_PER_LEVEL * (enchantLevel - 1));
        if (level.getRandom().nextFloat() >= chance) return;
        // 已经被锁住的生物不会再次触发
        if (victim.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) || victim.hasEffect(MobEffects.WEAKNESS)) return;

        EnchantContext context = new EnchantContext(level, victim, victim.position());
        chainsEffect(context, MAIN_SECONDS);

        for (Entity target : context.entities(CHAIN_RADIUS, CHAIN_TARGETS, false,
                candidate -> !(candidate instanceof Player) && !context.isNonLiving(candidate))) {
            chainsEffect(context.forEntity(target), CHAIN_SECONDS);
        }
    }

    private static void chainsEffect(EnchantContext context, float seconds) {
        context.particleAt(context.pos().add(PARTICLE_HORIZONTAL_OFFSET, PARTICLE_HEIGHT_OFFSET, PARTICLE_HORIZONTAL_OFFSET),
                new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.CHAIN)),
                PARTICLE_COUNT, PARTICLE_SPREAD, PARTICLE_Y_SPEED, PARTICLE_Y_SPREAD, PARTICLE_SPEED);
        context.soundHostile(SoundEvents.CHAIN_PLACE, SOUND_VOLUME, SOUND_PITCH);
        context.effect(MobEffects.MOVEMENT_SLOWDOWN, seconds, EFFECT_AMPLIFIER);
        context.effect(MobEffects.WEAKNESS, seconds, EFFECT_AMPLIFIER);
    }
}
