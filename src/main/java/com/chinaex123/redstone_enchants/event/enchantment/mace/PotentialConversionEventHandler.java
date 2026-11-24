package com.chinaex123.redstone_enchants.event.enchantment.mace;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 势能转化：下落攻击时，每格高度都增加伤害，并对有护甲的目标造成额外伤害
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
@SuppressWarnings("deprecation")
public class PotentialConversionEventHandler {
    private static final ResourceLocation POTENTIAL_CONVERSION_ID = RedstoneEnchants.id("potential_conversion");

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getDirectEntity() instanceof Player attacker)) return;

        LivingEntity target = event.getEntity();
        ItemStack weapon = attacker.getMainHandItem();

        if (weapon.isEmpty()) return;

        Holder.Reference<Enchantment> potentialConversionEnchant = attacker.level()
                .registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(POTENTIAL_CONVERSION_ID)
                .orElse(null);

        if (potentialConversionEnchant == null) return;

        int level = weapon.getEnchantments().getLevel(potentialConversionEnchant);
        if (level <= 0) return;

        // 检查是否是下落攻击（fallDistance > 0）
        float fallDistance = attacker.fallDistance;
        if (fallDistance <= 0) return;

        // 每格高度增加0.8%伤害 × 附魔等级
        float bonusPerBlock = 0.008F * level;
        float totalBonus = fallDistance * bonusPerBlock;

        // 如果目标有护甲，额外增加伤害
        float targetArmor = target.getArmorValue();
        if (targetArmor > 0) {
            float armorBonus = targetArmor * 0.015F;
            totalBonus *= (1 + armorBonus);
        }

        // 增加伤害
        float newDamage = event.getOriginalDamage() * (1 + totalBonus);
        event.setNewDamage(newDamage);
    }
}
