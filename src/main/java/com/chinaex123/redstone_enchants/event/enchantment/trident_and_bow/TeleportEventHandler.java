package com.chinaex123.redstone_enchants.event.enchantment.trident_and_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 传送：命中后与目标交换位置。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class TeleportEventHandler {

    /** 附魔 ID。 */
    private static final String ID = "teleport";

    /** 粒子相对传送点的抬高距离。 */
    private static final double PARTICLE_OFFSET_Y = 0.5D;
    /** 粒子相对传送点的水平偏移（格，不做水平偏移）。 */
    private static final double PARTICLE_OFFSET_HORIZONTAL = 0.0D;
    /** 粒子数量。 */
    private static final int PARTICLE_COUNT = 100;
    /** 粒子水平扩散范围。 */
    private static final double PARTICLE_SPREAD_XZ = 0.3D;
    /** 粒子垂直扩散范围。 */
    private static final double PARTICLE_SPREAD_Y = 0.7D;
    /** 粒子速度。 */
    private static final double PARTICLE_SPEED = 0.0D;
    /** 传送音效音量。 */
    private static final float SOUND_VOLUME = 0.5F;
    /** 传送音效音调。 */
    private static final float SOUND_PITCH = 0.5F;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!(attacker.level() instanceof ServerLevel level)) return;
        LivingEntity victim = event.getEntity();
        int enchantLevel = EnchantContext.level(attacker, ID);
        if (enchantLevel <= 0) return;

        EnchantContext context = new EnchantContext(level, attacker, attacker.position());
        // 亡灵与傀儡不会被传送
        if (context.isNonLiving(victim)) return;

        Vec3 attackerPos = attacker.position();
        Vec3 victimPos = victim.position();

        teleport(context, attacker, victimPos);
        teleport(context, victim, attackerPos);
    }

    private static void teleport(EnchantContext context, Entity target, Vec3 destination) {
        context.teleport(target, destination);
        EnchantContext effect = new EnchantContext(context.level(), target, destination);
        effect.particleAt(destination.add(PARTICLE_OFFSET_HORIZONTAL, PARTICLE_OFFSET_Y, PARTICLE_OFFSET_HORIZONTAL),
                ParticleTypes.REVERSE_PORTAL,
                PARTICLE_COUNT, PARTICLE_SPREAD_XZ, PARTICLE_SPREAD_Y, PARTICLE_SPREAD_XZ, PARTICLE_SPEED);
        effect.soundHostile(SoundEvents.ENDERMAN_TELEPORT, SOUND_VOLUME, SOUND_PITCH);
    }
}
