package com.chinaex123.redstone_enchants.event.enchantment.all_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

/**
 * 精准射击：箭矢无视重力直飞目标，未命中会消失。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class AccuracyShotEventHandler {

    private static final String ID = "accuracy_shot";
    private static final String ACCURACY_TAG = "redstone_enchants.accuracy_shot";

    /** 首次清理检查的延迟（tick） */
    private static final int INITIAL_CHECK_DELAY_TICKS = 100;
    /** 后续清理检查的周期（tick） */
    private static final int CHECK_INTERVAL_TICKS = 100;
    /** 箭矢被视为失效所需的最小存活时间（tick） */
    private static final int MIN_ARROW_TICKS = 100;
    /** 判定玩家仍在附近的半径（格） */
    private static final double PLAYER_NEARBY_RADIUS = 5.0D;

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof AbstractArrow arrow)) return;
        if (event.loadedFromDisk()) return;
        if (!(arrow.level() instanceof ServerLevel level)) return;
        int enchantLevel = EnchantContext.level(level, ID, arrow.getWeaponItem());
        if (enchantLevel <= 0) return;

        EnchantContext context = new EnchantContext(level, arrow, arrow.position());
        context.mergeNbt(arrow, "{NoGravity:1b}");
        context.tagAdd(arrow, ACCURACY_TAG);
        context.schedule(INITIAL_CHECK_DELAY_TICKS, () -> accuracyCheck(level));
    }

    private static void accuracyCheck(ServerLevel level) {
        List<Entity> arrows = EnchantContext.allWithTag(level, ACCURACY_TAG);
        for (Entity arrow : arrows) {
            if (!(arrow instanceof AbstractArrow)) continue;
            if (arrow.tickCount < MIN_ARROW_TICKS) continue;
            boolean playerNearby = !level.getEntitiesOfClass(Player.class, arrow.getBoundingBox().inflate(PLAYER_NEARBY_RADIUS),
                    Player::isAlive).isEmpty();
            if (!playerNearby) {
                arrow.discard();
            }
        }
        if (!arrows.isEmpty()) {
            EnchantContext context = new EnchantContext(level, arrows.get(0), arrows.get(0).position());
            context.schedule(CHECK_INTERVAL_TICKS, () -> accuracyCheck(level));
        }
    }
}
