package com.chinaex123.redstone_enchants.event.enchantment.swords;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 最后的希望：消耗主手武器对目标造成巨额伤害，使用后武器消失。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class LastHopeEventHandler {

    private static final String ID = "last_hope";

    /** 音爆粒子数量 */
    private static final int PARTICLE_COUNT = 50;
    /** 音爆粒子扩散范围 */
    private static final double PARTICLE_SPREAD = 1.0D;
    /** 音爆粒子速度 */
    private static final double PARTICLE_SPEED = 0.5D;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getEntity() instanceof Player attacker)) return;
        if (!(attacker.level() instanceof ServerLevel level)) return;

        LivingEntity victim = event.getEntity();
        if (victim == attacker) return;

        if (victim.getType().is(EnchantContext.BLACK_ENTITY)) return;

        ItemStack weapon = attacker.getMainHandItem();
        if (EnchantContext.level(level, ID, weapon) <= 0) return;

        new EnchantContext(level, attacker, attacker.position()).clearMainHand();

        new EnchantContext(level, victim, victim.position())
                .particle(ParticleTypes.SONIC_BOOM, PARTICLE_COUNT, PARTICLE_SPREAD, PARTICLE_SPREAD,
                        PARTICLE_SPREAD, PARTICLE_SPEED);
    }
}
