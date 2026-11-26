package com.chinaex123.redstone_enchants.event.enchantment.foot_armor;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
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
 * 踏焰者：可在岩浆上行走，潜行时下潜到岩浆下
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class FlameWalkerEventHandler {
    private static final ResourceLocation FLAME_WALKER_ID = RedstoneEnchants.id("flame_walker");

    /** 脚底向下检测岩浆的深度（格） */
    private static final double LAVA_CHECK_DEPTH = 0.4D;
    /** 流体表面高度偏移（格） */
    private static final double SURFACE_HEIGHT_OFFSET = 1.0D;

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        if (boots.isEmpty()) return;

        ResourceKey<Enchantment> flameWalkerEnchant = EnchantContext.key(FLAME_WALKER_ID);

        int enchantLevel = EnchantContext.level(boots, flameWalkerEnchant);
        if (enchantLevel <= 0) return;

        // 如果玩家潜行，允许正常下潜
        if (player.isCrouching()) return;

        // 检查玩家是否不在岩浆中
        if (player.isInLava()) return;

        // 检测脚底下方 0.4 格的位置是否有岩浆
        BlockPos entityPos = player.blockPosition();
        BlockPos lavaCheckPos = new BlockPos(
                entityPos.getX(),
                (int) Math.floor(player.getBoundingBox().minY - LAVA_CHECK_DEPTH),
                entityPos.getZ()
        );

        boolean hasLavaBelow = player.level().getFluidState(lavaCheckPos).is(Fluids.LAVA);

        if (hasLavaBelow) {
            // 获取岩浆表面高度
            double lavaHeight = lavaCheckPos.getY() + SURFACE_HEIGHT_OFFSET;

            // 如果玩家在岩浆面上方，调整到岩浆表面高度
            if (player.getY() > lavaHeight) {
                player.setPos(player.getX(), lavaHeight, player.getZ());
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
