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
 * 精准射击：射出的箭矢失去重力并悬停，周围没有玩家时自动清除。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class AccuracyShotEventHandler {

    private static final String ID = "accuracy_shot";

    private static final String ACCURACY_TAG = "redstone_enchants.accuracy_shot";

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof AbstractArrow arrow)) return;
        if (event.loadedFromDisk()) return;
        if (!(arrow.level() instanceof ServerLevel level)) return;
        if (EnchantContext.level(level, ID, arrow.getWeaponItem()) <= 0) return;

        EnchantContext context = new EnchantContext(level, arrow, arrow.position());
        context.mergeNbt(arrow, "{NoGravity:1b}");
        context.tagAdd(arrow, ACCURACY_TAG);
        context.schedule(100, () -> accuracyCheck(level));
    }

    private static void accuracyCheck(ServerLevel level) {
        List<Entity> arrows = EnchantContext.allWithTag(level, ACCURACY_TAG);
        for (Entity arrow : arrows) {
            if (!(arrow instanceof AbstractArrow)) continue;
            if (arrow.tickCount < 100) continue;
            boolean playerNearby = !level.getEntitiesOfClass(Player.class, arrow.getBoundingBox().inflate(5.0D),
                    Player::isAlive).isEmpty();
            if (!playerNearby) {
                arrow.discard();
            }
        }
        if (!arrows.isEmpty()) {
            level.getServer().tell(new net.minecraft.server.TickTask(
                    level.getServer().getTickCount() + 100, () -> accuracyCheck(level)));
        }
    }
}
