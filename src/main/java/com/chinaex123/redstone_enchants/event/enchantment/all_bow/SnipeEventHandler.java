package com.chinaex123.redstone_enchants.event.enchantment.all_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 狙击：与目标距离每增加10格，造成的伤害提高
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class SnipeEventHandler {
    private static final ResourceLocation SNIPE_ID = RedstoneEnchants.id("snipe");

    /** 每提高一档伤害加成所需的距离（格） */
    private static final double DISTANCE_STEP = 10.0D;
    /** 1 级时每档距离增加的伤害倍率 */
    private static final float DAMAGE_BONUS_BASE = 0.15F;
    /** 每级额外增加的伤害倍率 */
    private static final float DAMAGE_BONUS_PER_LEVEL = 0.15F;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)) return;

        Entity shooter = arrow.getOwner();
        if (!(shooter instanceof Player player)) return;

        LivingEntity target = event.getEntity();

        ItemStack bow = player.getMainHandItem();
        if (bow.isEmpty()) bow = player.getOffhandItem();
        if (bow.isEmpty()) return;

        ResourceKey<Enchantment> snipeEnchant = EnchantContext.key(SNIPE_ID);

        int level = EnchantContext.level(bow, snipeEnchant);
        if (level <= 0) return;

        // 计算距离
        double distance = player.distanceTo(target);

        // 每 10 格增加伤害，倍率随附魔等级提高
        int distanceBonus = (int) (distance / DISTANCE_STEP);
        float bonus = distanceBonus * (DAMAGE_BONUS_BASE + DAMAGE_BONUS_PER_LEVEL * (level - 1));

        float newDamage = event.getOriginalDamage() * (1 + bonus);
        event.setNewDamage(newDamage);
    }
}
