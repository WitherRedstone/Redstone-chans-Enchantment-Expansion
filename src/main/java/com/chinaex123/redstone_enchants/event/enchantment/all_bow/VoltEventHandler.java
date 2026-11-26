package com.chinaex123.redstone_enchants.event.enchantment.all_bow;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 伏特：雷雨天射出的箭矢伤害增加
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class VoltEventHandler {
    private static final ResourceLocation VOLT_ID = RedstoneEnchants.id("volt");

    /** 1 级时的伤害加成 */
    private static final float DAMAGE_BONUS_BASE = 0.25F;
    /** 每级额外增加的伤害加成 */
    private static final float DAMAGE_BONUS_PER_LEVEL = 0.25F;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)) return;

        Entity shooter = arrow.getOwner();
        if (!(shooter instanceof Player player)) return;

        // 检查是否是雷雨天
        if (!player.level().isThundering()) return;

        ItemStack bow = player.getMainHandItem();
        if (bow.isEmpty()) bow = player.getOffhandItem();
        if (bow.isEmpty()) return;

        ResourceKey<Enchantment> voltEnchant = EnchantContext.key(VOLT_ID);

        int level = EnchantContext.level(bow, voltEnchant);
        if (level <= 0) return;

        // 伤害加成随附魔等级提高
        float bonus = DAMAGE_BONUS_BASE + DAMAGE_BONUS_PER_LEVEL * (level - 1);
        float newDamage = event.getOriginalDamage() * (1 + bonus);
        event.setNewDamage(newDamage);
    }
}
