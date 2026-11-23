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
 * 锁链：近战命中后有一定几率把锁链传递到附近的其它生物身上。
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

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!(attacker.level() instanceof ServerLevel level)) return;
        if (!(event.getEntity() instanceof LivingEntity victim)) return;

        int enchantLevel = EnchantContext.level(attacker, ID);
        if (enchantLevel <= 0) return;

        // 只对直接攻击生效
        if (event.getSource().getDirectEntity() != attacker) return;
        // 每级 20% 的触发几率
        if (level.getRandom().nextFloat() >= enchantLevel * 0.2F) return;
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
        context.particleAt(context.pos().add(0.0D, 1.0D, 0.0D),
                new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.CHAIN)),
                8, 0.2D, 0.5D, 0.2D, 0.0D);
        context.soundHostile(SoundEvents.CHAIN_PLACE, 1.0F, 1.0F);
        context.effect(MobEffects.MOVEMENT_SLOWDOWN, seconds, 9);
        context.effect(MobEffects.WEAKNESS, seconds, 9);
    }
}
