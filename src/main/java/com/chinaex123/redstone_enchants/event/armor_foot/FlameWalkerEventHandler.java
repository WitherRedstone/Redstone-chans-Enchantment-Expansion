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
 * 踏焰者：可在岩浆表面行走，潜行时下潜到岩浆下
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class FlameWalkerEventHandler {
    private static final Identifier FLAME_WALKER_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "flame_walker");

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        if (boots.isEmpty()) return;

        Holder.Reference<Enchantment> flameWalkerEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(FLAME_WALKER_ID)
                .orElse(null);

        if (flameWalkerEnchant == null) return;

        @SuppressWarnings("deprecation")
        int enchantLevel = boots.getEnchantments().getLevel(flameWalkerEnchant);
        if (enchantLevel <= 0) return;

        // 如果玩家潜行，允许正常下潜
        if (player.isCrouching()) return;

        // 检查玩家是否不在岩浆中
        if (player.isInLava()) return;

        // 检测脚底下方0.4格的位置是否有岩浆
        BlockPos entityPos = player.blockPosition();
        BlockPos lavaCheckPos = new BlockPos(
                entityPos.getX(),
                (int) Math.floor(player.getBoundingBox().minY - 0.4),
                entityPos.getZ()
        );

        boolean hasLavaBelow = player.level().getFluidState(lavaCheckPos).is(Fluids.LAVA);

        if (hasLavaBelow) {
            // 获取岩浆表面高度
            double lavaHeight = lavaCheckPos.getY() + 1.0;

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