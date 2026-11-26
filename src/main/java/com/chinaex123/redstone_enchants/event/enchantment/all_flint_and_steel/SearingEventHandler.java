package com.chinaex123.redstone_enchants.event.enchantment.all_flint_and_steel;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

/**
 * 灼烧：右键目标时使其燃烧
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class SearingEventHandler {
    private static final ResourceLocation SEARING_ID = RedstoneEnchants.id("searing");

    /** 1 级时的燃烧时长（tick）。 */
    private static final int FIRE_TICKS_BASE = 40;
    /** 每级额外增加的燃烧时长（tick）。 */
    private static final int FIRE_TICKS_PER_LEVEL = 40;
    /** 1 级时的火焰伤害。 */
    private static final float DAMAGE_BASE = 1.0F;
    /** 每级额外增加的火焰伤害。 */
    private static final float DAMAGE_PER_LEVEL = 1.0F;
    /** 每次使用消耗的耐久。 */
    private static final int DURABILITY_COST = 1;

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget() instanceof LivingEntity target)) return;

        Player player = event.getEntity();
        ItemStack tool = player.getMainHandItem();

        if (tool.isEmpty() || !tool.is(Items.FLINT_AND_STEEL)) return;

        ResourceKey<Enchantment> searingEnchant = EnchantContext.key(SEARING_ID);

        int level = EnchantContext.level(tool, searingEnchant);
        if (level <= 0) return;

        int fireTicks = FIRE_TICKS_BASE + FIRE_TICKS_PER_LEVEL * (level - 1);
        target.setRemainingFireTicks(fireTicks);

        float damage = DAMAGE_BASE + DAMAGE_PER_LEVEL * (level - 1);
        target.hurt(player.level().damageSources().onFire(), damage);

        // 播放使用动画
        player.swing(InteractionHand.MAIN_HAND);

        // 消耗1点耐久
        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);
        tool.hurtAndBreak(DURABILITY_COST, player, EquipmentSlot.MAINHAND);
    }
}
