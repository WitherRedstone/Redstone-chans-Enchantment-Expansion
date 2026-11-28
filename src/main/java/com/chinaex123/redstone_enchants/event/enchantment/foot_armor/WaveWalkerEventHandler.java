package com.chinaex123.redstone_enchants.event.enchantment.foot_armor;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

/**
 * 踏浪者：可在水面行走，潜行时下潜到水下
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class WaveWalkerEventHandler {
    private static final ResourceLocation WAVE_WALKER_ID = RedstoneEnchants.id("wave_walker");

    /** 脚底向下检测水的深度（格） */
    private static final double WATER_CHECK_DEPTH = 0.4D;
    /** 流体表面高度偏移（格） */
    private static final double SURFACE_HEIGHT_OFFSET = 1.0D;

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getEntity() instanceof Player player)) return;

        // 检查靴子是否有踏浪者附魔
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        if (boots.isEmpty()) return;

        ResourceKey<Enchantment> waveWalkerEnchant = EnchantContext.key(WAVE_WALKER_ID);

        int enchantLevel = EnchantContext.level(boots, waveWalkerEnchant);
        if (enchantLevel <= 0) return;

        // 如果玩家潜行，允许正常下潜
        if (player.isCrouching()) return;

        // 检查玩家是否不在水中
        if (player.isInWater()) return;

        // 检测脚底下方 0.4 格的位置是否有水
        BlockPos entityPos = player.blockPosition();
        BlockPos waterCheckPos = new BlockPos(
                entityPos.getX(),
                (int) Math.floor(player.getBoundingBox().minY - WATER_CHECK_DEPTH),
                entityPos.getZ()
        );

        boolean hasWaterBelow = player.level().getFluidState(waterCheckPos).is(Fluids.WATER);

        if (hasWaterBelow) {
            // 获取水面高度
            double waterHeight = waterCheckPos.getY() + SURFACE_HEIGHT_OFFSET;

            // 如果玩家在水面上方，调整到水面高度
            if (player.getY() > waterHeight) {
                player.setPos(player.getX(), waterHeight, player.getZ());
            }

            // 设置垂直速度为0
            player.setDeltaMovement(player.getDeltaMovement().x, 0, player.getDeltaMovement().z);
            // 重置掉落距离
            player.fallDistance = 0;
            // 标记为在地面上
            player.setOnGround(true);
        }
    }
}
