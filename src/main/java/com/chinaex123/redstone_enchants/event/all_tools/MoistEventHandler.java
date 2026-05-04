package com.chinaex123.redstone_enchants.event.all_tools;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

/**
 * 湿润：犁过的地会湿润一会
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class MoistEventHandler {
    private static final Identifier MOISTURE_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "moist");

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack tool = player.getMainHandItem();
        Holder.Reference<Enchantment> moistEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(MOISTURE_ID)
                .orElse(null);

        if (moistEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = tool.getEnchantments().getLevel(moistEnchant);
        if (level <= 0) return;

        BlockState state = event.getState();

        // 检查是否变成了耕地
        if (state.getBlock() == Blocks.FARMLAND) {
            // 设置为最大湿润度
            event.getLevel().setBlock(event.getPos(), state.setValue(FarmlandBlock.MOISTURE, 7), 2);
        }
    }
}
