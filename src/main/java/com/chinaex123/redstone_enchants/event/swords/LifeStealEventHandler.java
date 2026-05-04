package com.chinaex123.redstone_enchants.event.swords;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
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
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class LifeStealEventHandler {
    private static final Identifier LEECHING_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "leeching");

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        LivingEntity attacker = event.getSource().getEntity() instanceof LivingEntity living ? living : null;
        if (attacker == null) return;

        ItemStack weapon = attacker.getMainHandItem();
        Holder.Reference<Enchantment> leechingEnchant = attacker.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(LEECHING_ID)
                .orElse(null);

        if (leechingEnchant == null) return;

        int level = EnchantmentHelper.getEnchantmentLevel(leechingEnchant, attacker);
        if (level > 0) {
            float damageDealt = event.getOriginalDamage();
            float healAmount = damageDealt * 0.10f;
            attacker.heal(healAmount);
        }
    }
}
