package com.chinaex123.redstone_enchants.event.all_shear;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.List;

/**
 * 牧羊人：修剪羊毛或采集蜜脾时有概率获得多个
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class ShepherdEventHandler {
    private static final Identifier SHEPHERD_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "shepherd");

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Level level = player.level();
        if (level.isClientSide()) return;

        ItemStack stack = event.getItemStack();
        Entity target = event.getTarget();

        // 只处理剪羊毛
        if (!(target instanceof Sheep sheep)) return;
        if (!stack.is(Items.SHEARS)) return;

        Holder.Reference<Enchantment> shepherdEnchant = level
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(SHEPHERD_ID)
                .orElse(null);

        if (shepherdEnchant == null) return;

        @SuppressWarnings("deprecation")
        int enchantLevel = stack.getEnchantments().getLevel(shepherdEnchant);
        if (enchantLevel <= 0) return;

        handleSheepShearing(player, level, stack, sheep, enchantLevel, event);
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = player.level();
        if (level.isClientSide()) return;

        ItemStack stack = player.getItemInHand(event.getHand());
        if (!stack.is(Items.SHEARS)) return;

        BlockState state = level.getBlockState(event.getPos());
        if (!(state.getBlock() instanceof BeehiveBlock)) return;

        Holder.Reference<Enchantment> shepherdEnchant = level
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(SHEPHERD_ID)
                .orElse(null);

        if (shepherdEnchant == null) return;

        @SuppressWarnings("deprecation")
        int enchantLevel = stack.getEnchantments().getLevel(shepherdEnchant);
        if (enchantLevel <= 0) return;

        // 检查是否有蜂蜜
        int honeyLevel = state.getValue(BeehiveBlock.HONEY_LEVEL);
        if (honeyLevel < 5) return;

        // 取消原事件
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);

        // 生成基础蜜脾掉落（3个）
        ItemStack honeycomb = new ItemStack(Items.HONEYCOMB, 3);
        ItemEntity itemEntity = new ItemEntity(level, event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, honeycomb.copy());
        level.addFreshEntity(itemEntity);

        // 概率额外掉落
        RandomSource random = level.getRandom();
        double probability = enchantLevel * 0.2;

        if (probability >= 1.0 || random.nextDouble() < probability) {
            ItemStack extraDrop = honeycomb.copyWithCount(enchantLevel);
            ItemEntity extraEntity = new ItemEntity(level, event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, extraDrop);
            level.addFreshEntity(extraEntity);
        }

        // 降低蜂蜜等级
        level.setBlockAndUpdate(event.getPos(), state.setValue(BeehiveBlock.HONEY_LEVEL, 0));

        // 消耗耐久
        stack.hurtAndBreak(1, player, event.getHand());

    }

    private static void handleSheepShearing(Player player, Level level, ItemStack stack, Sheep sheep, int enchantLevel, PlayerInteractEvent.EntityInteract event) {
        if (!sheep.isShearable(player, stack, level, sheep.blockPosition())) return;

        List<ItemStack> drops = sheep.onSheared(player, stack, level, sheep.blockPosition());

        RandomSource random = level.getRandom();
        double probability = enchantLevel * 0.2;

        ServerLevel serverLevel = (ServerLevel) level;

        for (ItemStack drop : drops) {
            ItemEntity itemEntity = new ItemEntity(level, sheep.getX(), sheep.getY(), sheep.getZ(), drop.copy());
            level.addFreshEntity(itemEntity);

            if (probability >= 1.0 || random.nextDouble() < probability) {
                ItemStack extraDrop = drop.copy();
                ItemEntity extraEntity = new ItemEntity(level, sheep.getX(), sheep.getY(), sheep.getZ(), extraDrop);
                level.addFreshEntity(extraEntity);
            }
        }

        sheep.gameEvent(GameEvent.SHEAR, player);
        sheep.setSheared(true);
        stack.hurtAndBreak(1, player, event.getHand());
    }
}
