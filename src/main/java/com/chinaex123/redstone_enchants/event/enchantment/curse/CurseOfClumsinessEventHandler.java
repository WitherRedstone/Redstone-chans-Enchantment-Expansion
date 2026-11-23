package com.chinaex123.redstone_enchants.event.enchantment.curse;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 笨拙诅咒：攻击时有几率把手里的武器脱手掉出去。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class CurseOfClumsinessEventHandler {

    private static final String ID = "curse_of_clumsiness";

    /** 脱手概率。 */
    private static final float CHANCE = 0.4F;

    /** 掉落的替身物品。 */
    private static final String DROP = "{id:\"minecraft:item\",Item:{id:\"minecraft:oak_button\",count:1},PickupDelay:60s}";

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!(attacker.level() instanceof ServerLevel level)) return;
        if (EnchantContext.level(attacker, ID) <= 0) return;
        if (level.getRandom().nextFloat() >= CHANCE) return;

        EnchantContext context = new EnchantContext(level, attacker, attacker.position());
        Vec3 look = attacker.getLookAngle();
        context.summon(DROP, look.x * 2.0D, 1.0D, look.z * 2.0D);
        context.clearMainHand();
        context.soundMaster(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
    }
}
