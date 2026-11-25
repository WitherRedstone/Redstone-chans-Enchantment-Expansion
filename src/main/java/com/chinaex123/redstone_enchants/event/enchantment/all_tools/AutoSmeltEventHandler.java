package com.chinaex123.redstone_enchants.event.enchantment.all_tools;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.List;

/**
 * 自动熔炼：挖掘的方块自动熔炼成对应成品
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class AutoSmeltEventHandler {
    private static final ResourceLocation AUTO_SMELT_ID = RedstoneEnchants.id("auto_smelt");

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();

        if (player.level().isClientSide()) return;

        ItemStack tool = player.getMainHandItem();
        ResourceKey<Enchantment> autoSmeltEnchant = EnchantContext.key(AUTO_SMELT_ID);

        int level = EnchantContext.level(tool, autoSmeltEnchant);
        if (level <= 0) return;

        // 阻止默认掉落，手动处理
        event.setCanceled(true);

        ServerLevel serverLevel = (ServerLevel) player.level();
        var blockState = event.getState();
        var pos = event.getPos();

        // 获取原方块的掉落物
        List<ItemStack> drops = Block.getDrops(blockState, serverLevel, pos, serverLevel.getBlockEntity(pos), player, tool);

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

        RecipeManager recipeManager = level.getRecipeManager();

        // 遍历所有烧炼配方
        List<RecipeHolder<SmeltingRecipe>> recipes = recipeManager.getAllRecipesFor(RecipeType.SMELTING);

        for (RecipeHolder<SmeltingRecipe> holder : recipes) {
            SmeltingRecipe recipe = holder.value();
            SingleRecipeInput recipeInput = new SingleRecipeInput(input);

            if (recipe.matches(recipeInput, level)) {
                return recipe.getResultItem(level.registryAccess()).copy();
            }
        }

        return input;
    }
}
