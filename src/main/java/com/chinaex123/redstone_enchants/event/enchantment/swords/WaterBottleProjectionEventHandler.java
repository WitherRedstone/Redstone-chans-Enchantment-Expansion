package com.chinaex123.redstone_enchants.event.enchantment.swords;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 水瓶投掷：近战命中时在目标身上砸出若干瓶装水。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class WaterBottleProjectionEventHandler {

    private static final String ID = "water_bottle_projection";

    private static final String SPLASH_WATER = "{id:\"minecraft:potion\",Item:{id:\"minecraft:splash_potion\","
            + "count:1,components:{\"minecraft:potion_contents\":{potion:\"minecraft:water\"}}}}";

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!(attacker.level() instanceof ServerLevel level)) return;
        if (event.getSource().getDirectEntity() != attacker) return;

        int enchantLevel = EnchantContext.level(attacker, ID);
        if (enchantLevel <= 0) return;

        int count = Math.min(enchantLevel, 2);
        EnchantContext context = new EnchantContext(level, event.getEntity(), event.getEntity().position());
        for (int index = 0; index < count; index++) {
            context.summon(SPLASH_WATER);
        }
    }
}
