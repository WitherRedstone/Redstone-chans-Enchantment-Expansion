package com.chinaex123.redstone_enchants.event.enchantment.chest_armor;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

/**
 * 防弹：有概率免疫潜影子弹伤害
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class BulletproofEventHandler {
    private static final ResourceLocation BULLETPROOF_ID = RedstoneEnchants.id("bulletproof");

    /** 基础免疫概率 */
    private static final float BASE_IMMUNITY_CHANCE = 0.25f;
    /** 每级附魔增加的免疫概率 */
    private static final float CHANCE_PER_LEVEL = 0.25f;

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();

        if (!(target instanceof Player player)) return;

        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        if (chestplate.isEmpty()) return;

        ResourceKey<Enchantment> bulletproofEnchant = EnchantContext.key(BULLETPROOF_ID);

        int level = EnchantContext.level(chestplate, bulletproofEnchant);
        if (level <= 0) return;

        // 检查伤害来源是否是潜影子弹
        Entity directEntity = event.getSource().getDirectEntity();
        if (!(directEntity instanceof ShulkerBullet)) return;

        float immunityChance = BASE_IMMUNITY_CHANCE + (level - 1) * CHANCE_PER_LEVEL;

        if (RedstoneEnchants.RANDOM.nextFloat() < immunityChance) {
            event.setCanceled(true);
        }
    }
}