package com.chinaex123.redstone_enchants.event.enchantment.swords;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 吞噬：击杀目标时恢复饥饿值与生命，饱食状态下额外获得生命恢复。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class DevouringEventHandler {

    private static final String ID = "devouring";

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!(attacker.level() instanceof ServerLevel level)) return;
        if (EnchantContext.level(attacker, ID) <= 0) return;

        EnchantContext context = new EnchantContext(level, attacker, attacker.position());
        context.effect(MobEffects.SATURATION, 1.0F, 0);
        if (context.foodLevel() >= 20) {
            context.effect(MobEffects.REGENERATION, 1.0F, 0);
        }
    }
}
