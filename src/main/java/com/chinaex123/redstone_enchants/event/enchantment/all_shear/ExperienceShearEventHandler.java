package com.chinaex123.redstone_enchants.event.enchantment.all_shear;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.List;

/**
 * 经验修剪：剪下的羊毛改为掉落随机的经验球
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class ExperienceShearEventHandler {
    private static final ResourceLocation EXPERIENCE_SHEAR_ID = RedstoneEnchants.id("experience_shear");
    private static final ResourceLocation HARVEST_ECHO = RedstoneEnchants.id("harvest_echo");

    /** 1 级时的经验上限系数，经验值取 1 ~ EXP_BOUND_BASE 之间的随机数 */
    private static final int EXP_BOUND_BASE = 3;
    /** 每级额外增加的经验上限系数 */
    private static final int EXP_BOUND_PER_LEVEL = 3;
    /** 单个经验球的最少经验值 */
    private static final int EXP_MIN = 1;
    /** 收获回响提供的生命恢复持续时间（tick） */
    private static final int REGENERATION_DURATION_TICKS = 60;
    /** 收获回响提供的生命恢复药水等级 */
    private static final int REGENERATION_AMPLIFIER = 0;
    /** 每剪一次消耗的耐久 */
    private static final int DURABILITY_COST = 1;

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Level level = player.level();
        if (level.isClientSide()) return;

        ItemStack stack = event.getItemStack();
        Entity target = event.getTarget();

        if (!(target instanceof Sheep sheep)) return;
        if (!stack.is(Items.SHEARS)) return;

        ResourceKey<Enchantment> experienceShearEnchant = EnchantContext.key(EXPERIENCE_SHEAR_ID);

        int enchantLevel = EnchantContext.level(stack, experienceShearEnchant);
        if (enchantLevel <= 0) return;

        if (!sheep.isShearable(player, stack, level, sheep.blockPosition())) return;

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);

        List<ItemStack> drops = sheep.onSheared(player, stack, level, sheep.blockPosition());

        RandomSource random = level.getRandom();

        // 经验上限随等级提高：1 级 1~3 点，每级再增加 3 点上限
        int expBound = EXP_BOUND_BASE + EXP_BOUND_PER_LEVEL * (enchantLevel - 1);

        // 根据附魔等级生成经验球，每级增加经验数量
        for (ItemStack drop : drops) {
            int expAmount = random.nextInt(expBound) + EXP_MIN;
            ExperienceOrb.award((ServerLevel) level, sheep.position(), expAmount);
        }

        sheep.gameEvent(GameEvent.SHEAR, player);
        stack.hurtAndBreak(DURABILITY_COST, player, LivingEntity.getSlotForHand(event.getHand()));

        ResourceKey<Enchantment> harvestEchoEnchant = EnchantContext.key(HARVEST_ECHO);

        int harvestLevel = EnchantContext.level(stack, harvestEchoEnchant);
        if (harvestLevel > 0) {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, REGENERATION_DURATION_TICKS, REGENERATION_AMPLIFIER, false, true));
        }
    }
}
