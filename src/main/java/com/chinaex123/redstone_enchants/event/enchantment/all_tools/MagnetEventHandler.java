package com.chinaex123.redstone_enchants.event.enchantment.all_tools;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

/**
 * 磁力：吸引附近的掉落物到身边
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class MagnetEventHandler {
    private static final ResourceLocation MAGNET_ID = RedstoneEnchants.id("magnet");

    /** 1 级时的吸引范围（格） */
    private static final double RANGE_BASE = 4.0D;
    /** 每级额外增加的吸引范围（格） */
    private static final double RANGE_PER_LEVEL = 4.0D;
    /** 吸引范围上限（格） */
    private static final double RANGE_MAX = 16.0D;
    /** 1 级时的基础吸引速度 */
    private static final double SPEED_BASE = 0.08D;
    /** 每级额外增加的吸引速度 */
    private static final double SPEED_PER_LEVEL = 0.08D;
    /** 最小吸附距离，小于该距离不再吸引 */
    private static final double MIN_DISTANCE = 0.5D;
    /** 保留原有运动的比例（阻尼） */
    private static final double DAMPING = 0.3D;
    /** 新吸引力在合成运动中所占的比重 */
    private static final double ATTRACTION_WEIGHT = 0.7D;
    /** 吸引力附带的向上初速度，避免掉落物贴地 */
    private static final double LIFT = 0.02D;
    /** 吸引目标点相对玩家脚部的抬高量 */
    private static final double EYE_HEIGHT_OFFSET = 1.0D;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        Player player = event.getEntity();

        // 检查主手和副手是否有磁力附魔
        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();

        ResourceKey<Enchantment> magnetEnchant = EnchantContext.key(MAGNET_ID);

        int mainLevel = EnchantContext.level(mainHand, magnetEnchant);
        int offLevel = EnchantContext.level(offHand, magnetEnchant);

        int maxLevel = Math.max(mainLevel, offLevel);
        if (maxLevel <= 0) return;

        // 吸引范围：1 级 4 格，每级再增加 4 格，最大 16 格
        double range = Math.min(RANGE_BASE + RANGE_PER_LEVEL * (maxLevel - 1), RANGE_MAX);

        // 吸引速度同样随等级提升
        double speedScale = SPEED_BASE + SPEED_PER_LEVEL * (maxLevel - 1);

        // 获取附近的掉落物
        List<ItemEntity> items = player.level().getEntitiesOfClass(ItemEntity.class,
                player.getBoundingBox().inflate(range));

        for (ItemEntity item : items) {
            if (item.isRemoved() || item.hasPickUpDelay()) continue;

            // 计算方向向量
            double dx = player.getX() - item.getX();
            double dy = (player.getY() + EYE_HEIGHT_OFFSET) - item.getY();
            double dz = player.getZ() - item.getZ();
            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

            if (distance < MIN_DISTANCE || distance > range) continue;

            // 平滑吸引：距离越近速度越慢
            double speed = speedScale * (distance / range);

            // 使用阻尼效果，避免抽搐
            double currentMotionX = item.getDeltaMovement().x;
            double currentMotionY = item.getDeltaMovement().y;
            double currentMotionZ = item.getDeltaMovement().z;

            // 混合当前运动和新吸引力（70%新 + 30%旧）
            item.setDeltaMovement(
                    currentMotionX * DAMPING + (dx / distance) * speed * ATTRACTION_WEIGHT,
                    currentMotionY * DAMPING + (dy / distance) * speed * ATTRACTION_WEIGHT + LIFT,
                    currentMotionZ * DAMPING + (dz / distance) * speed * ATTRACTION_WEIGHT
            );
            item.hurtMarked = true;
        }
    }
}
