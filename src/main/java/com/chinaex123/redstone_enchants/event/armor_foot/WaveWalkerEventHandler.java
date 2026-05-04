package com.chinaex123.redstone_enchants.event.armor_foot;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
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
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class WaveWalkerEventHandler {
    private static final Identifier WAVE_WALKER_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "wave_walker");

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;

        // 检查靴子是否有踏浪者附魔
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        if (boots.isEmpty()) return;

        Holder.Reference<Enchantment> waveWalkerEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(WAVE_WALKER_ID)
                .orElse(null);

        if (waveWalkerEnchant == null) return;

        @SuppressWarnings("deprecation")
        int enchantLevel = boots.getEnchantments().getLevel(waveWalkerEnchant);
        if (enchantLevel <= 0) return;

        // 如果玩家潜行，允许正常下潜
        if (player.isCrouching()) return;

        // 检查玩家是否不在水中（只是脚踩在水面上）
        if (player.isInWater()) return;

        // 检测脚底下方0.4格的位置是否有水
        BlockPos entityPos = player.blockPosition();
        BlockPos waterCheckPos = new BlockPos(entityPos.getX(), (int) Math.floor(player.getBoundingBox().minY - 0.4), entityPos.getZ());

        boolean hasWaterBelow = player.level().getFluidState(waterCheckPos).is(Fluids.WATER);

        if (hasWaterBelow) {
            // 设置垂直速度为0
            player.setDeltaMovement(player.getDeltaMovement().x, 0, player.getDeltaMovement().z);

            // 重置掉落距离
            player.fallDistance = 0;

            // 标记为在地面上
            player.setOnGround(true);
        }
    }
}
