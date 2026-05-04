package com.chinaex123.redstone_enchants.event.all_shear;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.animal.sheep.Sheep;
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
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class ExperienceShearEventHandler {
    private static final Identifier EXPERIENCE_SHEAR_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "experience_shear");

    @SubscribeEvent
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
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(EXPERIENCE_SHEAR_ID)
                .orElse(null);

        if (experienceShearEnchant == null) return;

        @SuppressWarnings("deprecation")
        int enchantLevel = stack.getEnchantments().getLevel(experienceShearEnchant);
        if (enchantLevel <= 0) return;

        if (!sheep.isShearable(player, stack, level, sheep.blockPosition())) return;

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);

        List<ItemStack> drops = sheep.onSheared(player, stack, level, sheep.blockPosition());

        RandomSource random = level.getRandom();

        // 根据附魔等级生成经验球，每级增加经验数量
        for (ItemStack drop : drops) {
            // 不掉落羊毛，改为掉落经验球
            int expAmount = random.nextInt(3 * enchantLevel) + 1; // 1到3*等级的随机经验
            ExperienceOrb.award((ServerLevel) level, sheep.position(), expAmount);
        }

        sheep.gameEvent(GameEvent.SHEAR, player);
        stack.hurtAndBreak(1, player, event.getHand());

        Holder.Reference<Enchantment> harvestEchoEnchant = level
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "harvest_echo"))
                .orElse(null);

        if (harvestEchoEnchant != null) {
            @SuppressWarnings("deprecation")
            int harvestLevel = stack.getEnchantments().getLevel(harvestEchoEnchant);
            if (harvestLevel > 0) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 0, false, true));
            }
        }
    }
}
