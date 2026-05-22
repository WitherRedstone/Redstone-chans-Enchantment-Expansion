package com.chinaex123.redstone_enchants.event.all_tools;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;

import java.util.List;

/**
 * 自动熔炼：挖掘的方块自动熔炼成对应成品
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class AutoSmeltEventHandler {
    private static final Identifier AUTO_SMELT_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "auto_smelt");

    @SubscribeEvent
    public static void onBlockBreak(BreakBlockEvent event) {
        Player player = event.getPlayer();

        if (player.level().isClientSide()) return;

        ItemStack tool = player.getMainHandItem();
        Holder.Reference<Enchantment> autoSmeltEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(AUTO_SMELT_ID)
                .orElse(null);

        if (autoSmeltEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = tool.getEnchantments().getLevel(autoSmeltEnchant);
        if (level <= 0) return;

        // 阻止默认掉落，手动处理
        event.setCanceled(true);

        ServerLevel serverLevel = (ServerLevel) player.level();
        var blockState = event.getState();
        var pos = event.getPos();

        // 获取原方块的掉落物
        List<ItemStack> drops = net.minecraft.world.level.block.Block.getDrops(
                blockState, serverLevel, pos, serverLevel.getBlockEntity(pos), player, tool);

        // 移除原方块
        serverLevel.destroyBlock(pos, false, player);

        // 熔炼每个掉落物
        for (ItemStack drop : drops) {
            ItemStack smeltedResult = smeltItem(drop, serverLevel);

            ItemEntity itemEntity = new ItemEntity(
                    serverLevel,
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5,
                    smeltedResult.copy()
            );
            serverLevel.addFreshEntity(itemEntity);
        }

        // 消耗耐久
        tool.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
    }

    private static ItemStack smeltItem(ItemStack input, ServerLevel level) {
        if (input.isEmpty()) return ItemStack.EMPTY;

        RecipeManager recipeManager = level.getServer().getRecipeManager();
        SingleRecipeInput recipeInput = new SingleRecipeInput(input);

        var recipeOpt = recipeManager.getRecipeFor(RecipeType.SMELTING, recipeInput, level);

        return recipeOpt.map(smeltingRecipeRecipeHolder ->
                smeltingRecipeRecipeHolder.value().assemble(recipeInput).copy()).orElse(input);

    }
}
