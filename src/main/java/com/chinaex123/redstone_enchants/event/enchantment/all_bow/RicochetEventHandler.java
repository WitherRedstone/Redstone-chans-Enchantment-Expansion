package com.chinaex123.redstone_enchants.event.enchantment.all_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 跳弹：箭矢命中生物后转向附近另一只生物。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class RicochetEventHandler {

    private static final String ID = "ricochet";

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)) return;
        if (!(arrow.level() instanceof ServerLevel level)) return;
        if (EnchantContext.level(level, ID, arrow.getWeaponItem()) <= 0) return;

        EnchantContext context = new EnchantContext(level, arrow, arrow.position());
        context.sound(SoundEvents.ARROW_SHOOT, 1.0F, 1.0F);

        Entity target = context.nearestEntity(2.0D, 12.0D, false,
                candidate -> !(candidate instanceof Player) && !context.isNonLiving(candidate));
        if (target == null) return;

        Vec3 marker = target.getEyePosition().add(0.0D, 1.0D, 0.0D);
        Vec3 direction = marker.subtract(arrow.position());
        context.motion(arrow, direction.scale(0.25D));
    }
}
