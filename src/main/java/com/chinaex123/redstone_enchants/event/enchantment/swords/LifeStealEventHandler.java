package com.chinaex123.redstone_enchants.event.enchantment.swords;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 生命吸取：攻击时恢复造成伤害10%的生命值
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class LifeStealEventHandler {
    private static final ResourceLocation LIFE_STEAL_ID = RedstoneEnchants.id("life_steal");

    /** 生命偷取比例（造成伤害的 10%） */
    private static final double HEAL_RATIO = 0.10D;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        LivingEntity attacker = event.getSource().getEntity() instanceof LivingEntity living ? living : null;
        if (attacker == null) return;

        ItemStack weapon = attacker.getMainHandItem();
        ResourceKey<Enchantment> lifeStealEnchant = EnchantContext.key(LIFE_STEAL_ID);

        int level = EnchantContext.level(attacker, lifeStealEnchant);
        if (level > 0) {
            float damageDealt = event.getOriginalDamage();
            float healAmount = (float) (damageDealt * HEAL_RATIO);
            attacker.heal(healAmount);
        }
    }
}
