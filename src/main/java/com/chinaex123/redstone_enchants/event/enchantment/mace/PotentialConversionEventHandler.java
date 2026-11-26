package com.chinaex123.redstone_enchants.event.enchantment.mace;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
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
public class PotentialConversionEventHandler {
    private static final ResourceLocation POTENTIAL_CONVERSION_ID = RedstoneEnchants.id("potential_conversion");

    /** 1 级时每格下落高度增加的伤害比例。 */
    private static final float BONUS_PER_BLOCK_BASE = 0.008F;
    /** 每级额外增加的每格伤害比例。 */
    private static final float BONUS_PER_BLOCK_PER_LEVEL = 0.008F;
    /** 目标每点护甲提供的额外伤害比例。 */
    private static final float ARMOR_BONUS_PER_POINT = 0.015F;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getDirectEntity() instanceof Player attacker)) return;

        LivingEntity target = event.getEntity();
        ItemStack weapon = attacker.getMainHandItem();

        if (weapon.isEmpty()) return;

        ResourceKey<Enchantment> potentialConversionEnchant = EnchantContext.key(POTENTIAL_CONVERSION_ID);

        int level = EnchantContext.level(weapon, potentialConversionEnchant);
        if (level <= 0) return;

        // 检查是否是下落攻击
        float fallDistance = attacker.fallDistance;
        if (fallDistance <= 0) return;

        // 每格高度增加0.8%伤害 × 附魔等级
        float bonusPerBlock = BONUS_PER_BLOCK_BASE + BONUS_PER_BLOCK_PER_LEVEL * (level - 1);
        float totalBonus = fallDistance * bonusPerBlock;

        // 如果目标有护甲，额外增加伤害
        float targetArmor = target.getArmorValue();
        if (targetArmor > 0) {
            float armorBonus = targetArmor * ARMOR_BONUS_PER_POINT;
            totalBonus *= (1 + armorBonus);
        }

        // 增加伤害
        float newDamage = event.getOriginalDamage() * (1 + totalBonus);
        event.setNewDamage(newDamage);
    }
}
