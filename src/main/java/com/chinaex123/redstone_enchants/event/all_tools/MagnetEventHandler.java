package com.chinaex123.redstone_enchants.event.all_tools;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
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
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class MagnetEventHandler {
    private static final Identifier MAGNET_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "magnet");

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        // 检查主手和副手是否有磁力附魔
        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();

        Holder.Reference<Enchantment> magnetEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(MAGNET_ID)
                .orElse(null);

        if (magnetEnchant == null) return;

        int mainLevel = 0;
        int offLevel = 0;

        @SuppressWarnings("deprecation")
        int ml = mainHand.getEnchantments().getLevel(magnetEnchant);
        mainLevel = ml;

        @SuppressWarnings("deprecation")
        int ol = offHand.getEnchantments().getLevel(magnetEnchant);
        offLevel = ol;

        int maxLevel = Math.max(mainLevel, offLevel);
        if (maxLevel <= 0) return;

        // 吸引范围：每级增加2格，最大10格
        double range = Math.min(maxLevel * 4.0, 16.0);

        // 获取附近的掉落物
        List<ItemEntity> items = player.level().getEntitiesOfClass(ItemEntity.class,
                player.getBoundingBox().inflate(range));

        for (ItemEntity item : items) {
            if (item.isRemoved() || item.hasPickUpDelay()) continue;

            // 计算方向向量
            double dx = player.getX() - item.getX();
            double dy = (player.getY() + 1.0) - item.getY();
            double dz = player.getZ() - item.getZ();
            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

            if (distance < 0.5 || distance > range) continue;

            // 平滑吸引：距离越近速度越慢
            double speed = 0.08 * maxLevel * (distance / range);

            // 使用阻尼效果，避免抽搐
            double currentMotionX = item.getDeltaMovement().x;
            double currentMotionY = item.getDeltaMovement().y;
            double currentMotionZ = item.getDeltaMovement().z;

            // 混合当前运动和新吸引力（70%新 + 30%旧）
            item.setDeltaMovement(
                    currentMotionX * 0.3 + (dx / distance) * speed * 0.7,
                    currentMotionY * 0.3 + (dy / distance) * speed * 0.7 + 0.02,
                    currentMotionZ * 0.3 + (dz / distance) * speed * 0.7
            );
            item.hurtMarked = true;
        }
    }
}
