package com.chinaex123.redstone_enchants.event.armor_chest;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 狂战士：根据损失的生命值百分比增加近战伤害
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class BerserkEventHandler {
    private static final Identifier BERSERK_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "berserk");

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getDirectEntity() instanceof LivingEntity attacker)) return;
        if (!(attacker instanceof Player player)) return;

        // 检查胸甲是否有狂战士附魔
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        if (chestplate.isEmpty()) return;

        Holder.Reference<Enchantment> berserkEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(BERSERK_ID)
                .orElse(null);

        if (berserkEnchant == null) return;

        @SuppressWarnings("deprecation")
        int enchantLevel = chestplate.getEnchantments().getLevel(berserkEnchant);
        if (enchantLevel <= 0) return;

        // 计算生命值损失百分比
        float maxHealth = player.getMaxHealth();
        float currentHealth = player.getHealth();
        float healthLost = maxHealth - currentHealth;
        float healthLostPercent = healthLost / maxHealth;

        // 每损失10%生命，伤害+3%
        int lostTenPercent = (int) (healthLostPercent * 10);
        if (lostTenPercent <= 0) return;

        double damageBonus = lostTenPercent * 0.03 * enchantLevel;

        // 应用伤害加成
        event.setNewDamage((float) (event.getNewDamage() * (1 + damageBonus)));
    }
}
