package com.chinaex123.redstone_enchants.event.swords;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 均衡器：伤害根据目标血量百分比变化；目标血量越高，伤害越高
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class EqualizerEventHandler {
    private static final Identifier EQUALIZER_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "equalizer");

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getDirectEntity() instanceof Player attacker)) return;

        LivingEntity target = event.getEntity();
        ItemStack weapon = attacker.getMainHandItem();

        if (weapon.isEmpty()) return;

        Holder.Reference<Enchantment> equalizerEnchant = attacker.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(EQUALIZER_ID)
                .orElse(null);

        if (equalizerEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = weapon.getEnchantments().getLevel(equalizerEnchant);
        if (level <= 0) return;

        // 计算目标血量百分比
        float maxHealth = target.getMaxHealth();
        float currentHealth = target.getHealth();
        float healthPercentage = currentHealth / maxHealth;

        // 根据血量百分比和附魔等级计算伤害加成
        // 伤害加成 = 目标血量百分比 × 等级 × 40%
        float bonusMultiplier = healthPercentage * level * 0.2F;

        float originalDamage = event.getOriginalDamage();
        float newDamage = originalDamage * (1 + bonusMultiplier);
        event.setNewDamage(newDamage);
    }
}
