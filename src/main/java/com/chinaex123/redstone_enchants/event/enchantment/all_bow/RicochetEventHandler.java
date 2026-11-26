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
 * 跳弹：击中一个生物会让箭从生物身上反弹并击中附近的另一个生物。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class RicochetEventHandler {

    private static final String ID = "ricochet";

    /** 搜索附近生物的水平半径（格） */
    private static final double SEARCH_RADIUS = 2.0D;
    /** 搜索附近生物的垂直半径（格） */
    private static final double SEARCH_HEIGHT = 12.0D;
    /** 1 级时的弹射速度倍率 */
    private static final double BOUNCE_SPEED_BASE = 0.25D;
    /** 每级额外增加的弹射速度倍率 */
    private static final double BOUNCE_SPEED_PER_LEVEL = 0.05D;
    /** 瞄准点相对目标眼睛的垂直偏移（格） */
    private static final double AIM_OFFSET_Y = 1.0D;
    /** 瞄准点相对目标眼睛的水平偏移（格，不做水平偏移） */
    private static final double AIM_OFFSET_HORIZONTAL = 0.0D;
    /** 音效音量 */
    private static final float SOUND_VOLUME = 1.0F;
    /** 音效音调 */
    private static final float SOUND_PITCH = 1.0F;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)) return;
        if (!(arrow.level() instanceof ServerLevel level)) return;
        int enchantLevel = EnchantContext.level(level, ID, arrow.getWeaponItem());
        if (enchantLevel <= 0) return;

        EnchantContext context = new EnchantContext(level, arrow, arrow.position());
        context.sound(SoundEvents.ARROW_SHOOT, SOUND_VOLUME, SOUND_PITCH);

        Entity target = context.nearestEntity(SEARCH_RADIUS, SEARCH_HEIGHT, false,
                candidate -> !(candidate instanceof Player) && !context.isNonLiving(candidate));
        if (target == null) return;

        Vec3 marker = target.getEyePosition().add(AIM_OFFSET_HORIZONTAL, AIM_OFFSET_Y, AIM_OFFSET_HORIZONTAL);
        Vec3 direction = marker.subtract(arrow.position());
        double bounceSpeed = BOUNCE_SPEED_BASE + BOUNCE_SPEED_PER_LEVEL * (enchantLevel - 1);
        context.motion(arrow, direction.scale(bounceSpeed));
    }
}
