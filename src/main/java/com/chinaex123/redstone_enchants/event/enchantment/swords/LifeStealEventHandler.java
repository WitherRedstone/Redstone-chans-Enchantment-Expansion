package com.chinaex123.redstone_enchants.event.enchantment.swords;

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

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        LivingEntity attacker = event.getSource().getEntity() instanceof LivingEntity living ? living : null;
        if (attacker == null) return;

        ItemStack weapon = attacker.getMainHandItem();
        Holder.Reference<Enchantment> lifeStealEnchant = attacker.level()
                .registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(LIFE_STEAL_ID)
                .orElse(null);

        if (lifeStealEnchant == null) return;

        int level = EnchantmentHelper.getEnchantmentLevel(lifeStealEnchant, attacker);
        if (level > 0) {
            float damageDealt = event.getOriginalDamage();
            float healAmount = damageDealt * 0.10f;
            attacker.heal(healAmount);
        }
    }
}
