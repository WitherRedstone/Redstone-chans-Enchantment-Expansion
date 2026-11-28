package com.chinaex123.redstone_enchants.event.enchantment.all_tools;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

/**
 * 湿润：犁过的地会湿润一会
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class MoistEventHandler {
    private static final ResourceLocation MOISTURE_ID = RedstoneEnchants.id("moist");

    /** 耕地的最大湿润度 */
    private static final int MAX_MOISTURE = 7;
    /** 设置方块时使用的更新标志 */
    private static final int BLOCK_UPDATE_FLAG = 2;

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() != null && event.getEntity().level().isClientSide()) return;
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack tool = player.getMainHandItem();
        ResourceKey<Enchantment> moistEnchant = EnchantContext.key(MOISTURE_ID);

        int level = EnchantContext.level(tool, moistEnchant);
        if (level <= 0) return;

        BlockState state = event.getState();

        // 检查是否变成了耕地
        if (state.getBlock() == Blocks.FARMLAND) {
            // 设置为最大湿润度
            event.getLevel().setBlock(event.getPos(), state.setValue(FarmBlock.MOISTURE, MAX_MOISTURE), BLOCK_UPDATE_FLAG);
        }
    }
}
