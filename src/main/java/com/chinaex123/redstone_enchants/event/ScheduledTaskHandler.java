package com.chinaex123.redstone_enchants.event;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Post;

/**
 * 延迟任务驱动器。
 * <p>
 * 服务端世界每刻结束时，触发附魔上下文中到期的延迟任务执行。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class ScheduledTaskHandler {

    /**
     * 处理服务端世界每刻结束事件。
     * <p>
     * 仅处理服务端世界，执行该世界中所有到期的延迟任务。
     *
     * @param event 世界每刻事件
     */
    @SubscribeEvent
    public static void onLevelTick(Post event) {
        if (event.getLevel() instanceof ServerLevel level) {
            EnchantContext.runDueTasks(level);
        }
    }
}
