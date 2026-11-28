package com.chinaex123.redstone_enchants.event.enchantment.all_shear;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
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
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class ShepherdEventHandler {
    private static final ResourceLocation SHEPHERD_ID = RedstoneEnchants.id("shepherd");

    /** 蜂巢蜂蜜满级值，达到该值才能采集蜜脾 */
    private static final int FULL_HONEY_LEVEL = 5;
    /** 采集蜜脾后重置的蜂蜜等级 */
    private static final int EMPTY_HONEY_LEVEL = 0;
    /** 基础蜜脾掉落数量 */
    private static final int HONEYCOMB_BASE_COUNT = 3;
    /** 1 级时额外掉落的概率 */
    private static final double EXTRA_DROP_CHANCE_BASE = 0.2D;
    /** 每级额外增加的概率 */
    private static final double EXTRA_DROP_CHANCE_PER_LEVEL = 0.2D;
    /** 概率上限 */
    private static final double EXTRA_DROP_CHANCE_MAX = 1.0D;
    /** 1 级时额外掉落的数量 */
    private static final int EXTRA_COUNT_BASE = 1;
    /** 每级额外增加的掉落数量 */
    private static final int EXTRA_COUNT_PER_LEVEL = 1;
    /** 每使用一次消耗的耐久 */
    private static final int DURABILITY_COST = 1;
    /** 掉落物生成位置相对方块中心的偏移量 */
    private static final double SPAWN_OFFSET = 0.5D;

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

        ResourceKey<Enchantment> shepherdEnchant = EnchantContext.key(SHEPHERD_ID);

        int enchantLevel = EnchantContext.level(stack, shepherdEnchant);
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

        ResourceKey<Enchantment> shepherdEnchant = EnchantContext.key(SHEPHERD_ID);

        int enchantLevel = EnchantContext.level(stack, shepherdEnchant);
        if (enchantLevel <= 0) return;

        // 检查是否有蜂蜜
        int honeyLevel = state.getValue(BeehiveBlock.HONEY_LEVEL);
        if (honeyLevel < FULL_HONEY_LEVEL) return;

        // 取消原事件
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);

        // 生成基础蜜脾掉落
        ItemStack honeycomb = new ItemStack(Items.HONEYCOMB, HONEYCOMB_BASE_COUNT);
        ItemEntity itemEntity = new ItemEntity(level, event.getPos().getX() + SPAWN_OFFSET, event.getPos().getY() + SPAWN_OFFSET, event.getPos().getZ() + SPAWN_OFFSET, honeycomb.copy());
        level.addFreshEntity(itemEntity);

        // 概率额外掉落
        RandomSource random = level.getRandom();
        double probability = Math.min(EXTRA_DROP_CHANCE_MAX,
                EXTRA_DROP_CHANCE_BASE + EXTRA_DROP_CHANCE_PER_LEVEL * (enchantLevel - 1));
        int extraCount = EXTRA_COUNT_BASE + EXTRA_COUNT_PER_LEVEL * (enchantLevel - 1);

        if (probability >= EXTRA_DROP_CHANCE_MAX || random.nextDouble() < probability) {
            ItemStack extraDrop = honeycomb.copyWithCount(extraCount);
            ItemEntity extraEntity = new ItemEntity(level, event.getPos().getX() + SPAWN_OFFSET, event.getPos().getY() + SPAWN_OFFSET, event.getPos().getZ() + SPAWN_OFFSET, extraDrop);
            level.addFreshEntity(extraEntity);
        }

        // 降低蜂蜜等级
        level.setBlockAndUpdate(event.getPos(), state.setValue(BeehiveBlock.HONEY_LEVEL, EMPTY_HONEY_LEVEL));

        // 消耗耐久
        stack.hurtAndBreak(DURABILITY_COST, player, LivingEntity.getSlotForHand(event.getHand()));
    }

    private static void handleSheepShearing(Player player, Level level, ItemStack stack, Sheep sheep, int enchantLevel, PlayerInteractEvent.EntityInteract event) {
        if (!sheep.isShearable(player, stack, level, sheep.blockPosition())) return;

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);

        List<ItemStack> drops = sheep.onSheared(player, stack, level, sheep.blockPosition());

        RandomSource random = level.getRandom();
        double probability = Math.min(EXTRA_DROP_CHANCE_MAX,
                EXTRA_DROP_CHANCE_BASE + EXTRA_DROP_CHANCE_PER_LEVEL * (enchantLevel - 1));

        for (ItemStack drop : drops) {
            sheep.spawnShearedDrop(level, sheep.blockPosition(), drop);
            if (probability >= EXTRA_DROP_CHANCE_MAX || random.nextDouble() < probability) {
                ItemStack extraDrop = drop.copy();
                sheep.spawnShearedDrop(level, sheep.blockPosition(), extraDrop);
            }
        }

        sheep.gameEvent(GameEvent.SHEAR, player);
        stack.hurtAndBreak(DURABILITY_COST, player, LivingEntity.getSlotForHand(event.getHand()));
    }
}
