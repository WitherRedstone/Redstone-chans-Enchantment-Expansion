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

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!(attacker.level() instanceof ServerLevel level)) return;
        LivingEntity victim = event.getEntity();

        int enchantLevel = EnchantContext.level(attacker, ID);
        if (enchantLevel <= 0) return;

        new EnchantContext(level, victim, victim.position())
                .motion(victim, new Vec3(0.0D, enchantLevel, 0.0D));
    }
}
