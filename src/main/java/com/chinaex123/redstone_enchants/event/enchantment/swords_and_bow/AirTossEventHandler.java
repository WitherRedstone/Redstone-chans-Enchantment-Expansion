package com.chinaex123.redstone_enchants.event.enchantment.swords_and_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 空中抛掷：攻击时将被击中的生物抛向空中。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class AirTossEventHandler {

    private static final String ID = "air_toss";

    /** 抛掷初速度 1 级基础值 */
    private static final double MOTION_BASE = 0.85D;
    /** 每级额外增加的抛掷初速度 */
    private static final double MOTION_PER_LEVEL = 0.35D;
    /** 抛掷生效延迟（tick） */
    private static final int TOSS_DELAY_TICKS = 1;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!(attacker.level() instanceof ServerLevel level)) return;

        LivingEntity victim = event.getEntity();

        int enchantLevel = EnchantContext.level(attacker, ID);
        if (enchantLevel <= 0) return;

        double motion = MOTION_BASE + MOTION_PER_LEVEL * (enchantLevel - 1);
        EnchantContext context = new EnchantContext(level, victim, victim.position());
        context.schedule(TOSS_DELAY_TICKS, () -> {
            if (victim.isAlive()) {
                Vec3 d = victim.getDeltaMovement();
                context.motion(victim, new Vec3(d.x, motion, d.z));
            }
        });
    }
}
