package com.chinaex123.redstone_enchants.event.enchantment.all_shear;

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
@SuppressWarnings("deprecation")
public class ExperienceShearEventHandler {
    private static final ResourceLocation EXPERIENCE_SHEAR_ID = RedstoneEnchants.id("experience_shear");
    private static final ResourceLocation HARVEST_ECHO = RedstoneEnchants.id("harvest_echo");

    @SubscribeEvent
    @SuppressWarnings("deprecation")
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Level level = player.level();
        if (level.isClientSide()) return;

        ItemStack stack = event.getItemStack();
        Entity target = event.getTarget();

        if (!(target instanceof Sheep sheep)) return;
        if (!stack.is(Items.SHEARS)) return;

        Holder.Reference<Enchantment> experienceShearEnchant = level
                .registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(EXPERIENCE_SHEAR_ID)
                .orElse(null);

        if (experienceShearEnchant == null) return;

        int enchantLevel = stack.getEnchantments().getLevel(experienceShearEnchant);
        if (enchantLevel <= 0) return;

        if (!sheep.isShearable(player, stack, level, sheep.blockPosition())) return;

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);

        List<ItemStack> drops = sheep.onSheared(player, stack, level, sheep.blockPosition());

        RandomSource random = level.getRandom();

        // 根据附魔等级生成经验球，每级增加经验数量
        for (ItemStack drop : drops) {
            int expAmount = random.nextInt(3 * enchantLevel) + 1;
            ExperienceOrb.award((ServerLevel) level, sheep.position(), expAmount);
        }

        sheep.gameEvent(GameEvent.SHEAR, player);
        stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(event.getHand()));

        Holder.Reference<Enchantment> harvestEchoEnchant = level
                .registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(HARVEST_ECHO)
                .orElse(null);

        if (harvestEchoEnchant != null) {
            int harvestLevel = stack.getEnchantments().getLevel(harvestEchoEnchant);
            if (harvestLevel > 0) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 0, false, true));
            }
        }
    }
}
