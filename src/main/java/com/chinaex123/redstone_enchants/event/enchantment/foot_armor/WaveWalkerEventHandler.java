package com.chinaex123.redstone_enchants.event.enchantment.foot_armor;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
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
@SuppressWarnings("deprecation")
public class WaveWalkerEventHandler {
    private static final ResourceLocation WAVE_WALKER_ID = RedstoneEnchants.id("wave_walker");

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;

        // 检查靴子是否有踏浪者附魔
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        if (boots.isEmpty()) return;

        Holder.Reference<Enchantment> waveWalkerEnchant = player.level()
                .registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(WAVE_WALKER_ID)
                .orElse(null);

        if (waveWalkerEnchant == null) return;

        int enchantLevel = boots.getEnchantments().getLevel(waveWalkerEnchant);
        if (enchantLevel <= 0) return;

        // 如果玩家潜行，允许正常下潜
        if (player.isCrouching()) return;

        // 检查玩家是否不在水中
        if (player.isInWater()) return;

        // 检测脚底下方0.4格的位置是否有水
        BlockPos entityPos = player.blockPosition();
        BlockPos waterCheckPos = new BlockPos(
                entityPos.getX(),
                (int) Math.floor(player.getBoundingBox().minY - 0.4),
                entityPos.getZ()
        );

        boolean hasWaterBelow = player.level().getFluidState(waterCheckPos).is(Fluids.WATER);

        if (hasWaterBelow) {
            // 获取水面高度
            double waterHeight = waterCheckPos.getY() + 1.0;

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
