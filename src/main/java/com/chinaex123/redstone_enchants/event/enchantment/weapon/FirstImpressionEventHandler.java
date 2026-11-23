package com.chinaex123.redstone_enchants.event.enchantment.weapon;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 第一印象：首次命中时给目标留下印记，之后对同一目标的伤害会获得加成。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class FirstImpressionEventHandler {

    private static final String ID = "first_impression";

    private static final String MARK_TAG = "redstone_enchants.first_impression";

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!(attacker.level() instanceof ServerLevel level)) return;
        if (!(event.getEntity() instanceof LivingEntity victim)) return;
        if (EnchantContext.level(attacker, ID) <= 0) return;

        EnchantContext context = new EnchantContext(level, victim, victim.position());
        context.tagAdd(victim, MARK_TAG);
    }
}
