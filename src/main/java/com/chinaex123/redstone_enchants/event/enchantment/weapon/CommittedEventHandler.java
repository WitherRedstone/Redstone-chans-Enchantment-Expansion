package com.chinaex123.redstone_enchants.event.enchantment.weapon;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 承诺：击中已经受伤的生物会造成更高的伤害。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class CommittedEventHandler {

    private static final String ID = "committed";
    private static final String MARK_TAG = "harmed_by_committed";

    /** 愤怒粒子生成位置相对目标向上的偏移 */
    private static final double PARTICLE_Y_OFFSET = 2.2D;
    /** 生成的粒子数量 */
    private static final int PARTICLE_COUNT = 1;
    /** 粒子生成位置的水平偏移（格，不做水平偏移） */
    private static final double OFFSET_HORIZONTAL = 0.0D;
    /** 粒子扩散范围与速度（不扩散） */
    private static final double PARTICLE_SPREAD = 0.0D;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!(attacker.level() instanceof ServerLevel level)) return;
        LivingEntity victim = event.getEntity();
        int enchantLevel = EnchantContext.level(attacker, ID);
        if (enchantLevel <= 0) return;

        EnchantContext context = new EnchantContext(level, victim, victim.position());
        context.particleAt(context.pos().add(OFFSET_HORIZONTAL, PARTICLE_Y_OFFSET, OFFSET_HORIZONTAL),
                ParticleTypes.ANGRY_VILLAGER, PARTICLE_COUNT,
                PARTICLE_SPREAD, PARTICLE_SPREAD, PARTICLE_SPREAD, PARTICLE_SPREAD);
        context.tagAdd(victim, MARK_TAG);
    }
}
