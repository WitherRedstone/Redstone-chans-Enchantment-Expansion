package com.chinaex123.redstone_enchants.event.enchantment.chest_armor;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 回收：被箭矢射中时有几率把箭矢回收进背包。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class RetrievalEventHandler {

    private static final String ID = "retrieval";

    /** 1 级时的回收触发概率 */
    private static final double CHANCE_BASE = 0.2D;
    /** 每级额外增加的概率 */
    private static final double CHANCE_PER_LEVEL = 0.2D;
    /** 回收的箭矢数量 */
    private static final int ARROW_COUNT = 1;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        LivingEntity victim = event.getEntity();
        if (!(victim.level() instanceof ServerLevel level)) return;

        int enchantLevel = EnchantContext.level(victim, ID);
        if (enchantLevel <= 0) return;

        // 只有被箭矢射中才生效
        if (!(event.getSource().getDirectEntity() instanceof Projectile projectile)) return;
        // 每级 20% 的触发几率
        double chance = Math.min(1.0D, CHANCE_BASE + CHANCE_PER_LEVEL * (enchantLevel - 1));
        if (level.getRandom().nextFloat() >= chance) return;

        ItemStack loot = projectile instanceof SpectralArrow
                ? new ItemStack(Items.SPECTRAL_ARROW, ARROW_COUNT)
                : new ItemStack(Items.ARROW, ARROW_COUNT);
        new EnchantContext(level, victim, victim.position()).give(loot);
    }
}
