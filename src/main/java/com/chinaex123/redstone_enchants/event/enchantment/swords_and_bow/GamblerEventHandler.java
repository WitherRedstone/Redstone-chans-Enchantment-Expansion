package com.chinaex123.redstone_enchants.event.enchantment.swords_and_bow;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 赌徒：50%概率伤害+40%，50%概率伤害-20%
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class GamblerEventHandler {
    private static final ResourceLocation GAMBLER_ID = RedstoneEnchants.id("gambler");

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        LivingEntity attacker = event.getContainer().getSource().getEntity() instanceof LivingEntity livingEntity
                ? livingEntity : null;

        if (attacker == null) return;

        // 检查主手和副手是否有赌徒附魔
        boolean hasGambler = false;

        EquipmentSlot[] handSlots = {
                EquipmentSlot.MAINHAND,
                EquipmentSlot.OFFHAND
        };

        for (EquipmentSlot slot : handSlots) {
            ItemStack weapon = attacker.getItemBySlot(slot);
            if (weapon.isEmpty()) continue;

            ResourceKey<Enchantment> gamblerEnchant = EnchantContext.key(GAMBLER_ID);

            int enchantLevel = EnchantContext.level(weapon, gamblerEnchant);

            if (enchantLevel > 0) {
                hasGambler = true;
                break;
            }
        }

        if (!hasGambler) return;

        // 50%概率决定是增益还是减益
        float originalDamage = event.getOriginalDamage();
        float newDamage;

        if (RedstoneEnchants.RANDOM.nextFloat() < 0.5f) {
            // 50%概率：伤害+40%
            newDamage = originalDamage * 1.4f;
        } else {
            // 50%概率：伤害-20%
            newDamage = originalDamage * 0.8f;
        }

        event.setNewDamage(newDamage);
    }
}
