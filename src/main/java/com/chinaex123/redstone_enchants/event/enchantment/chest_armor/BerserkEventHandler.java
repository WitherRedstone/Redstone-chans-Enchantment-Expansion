package com.chinaex123.redstone_enchants.event.enchantment.chest_armor;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class BerserkEventHandler {
    private static final ResourceLocation BERSERK_ID = RedstoneEnchants.id("berserk");

    /** 生命值损失的百分比档位数 */
    private static final int HEALTH_PERCENT_STEPS = 10;
    /** 1 级时每损失一档生命值增加的伤害比例 */
    private static final double DAMAGE_BONUS_BASE = 0.03D;
    /** 每级额外增加的伤害比例 */
    private static final double DAMAGE_BONUS_PER_LEVEL = 0.03D;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getDirectEntity() instanceof LivingEntity attacker)) return;
        if (!(attacker instanceof Player player)) return;

        // 检查胸甲是否有狂战士附魔
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        if (chestplate.isEmpty()) return;

        ResourceKey<Enchantment> berserkEnchant = EnchantContext.key(BERSERK_ID);

        int enchantLevel = EnchantContext.level(chestplate, berserkEnchant);
        if (enchantLevel <= 0) return;

        // 计算生命值损失百分比
        float maxHealth = player.getMaxHealth();
        float currentHealth = player.getHealth();
        float healthLost = maxHealth - currentHealth;
        float healthLostPercent = healthLost / maxHealth;

        // 每损失10%生命，伤害+3%
        int lostTenPercent = (int) (healthLostPercent * HEALTH_PERCENT_STEPS);
        if (lostTenPercent <= 0) return;

        double damageBonusPerStep = DAMAGE_BONUS_BASE + DAMAGE_BONUS_PER_LEVEL * (enchantLevel - 1);
        double damageBonus = lostTenPercent * damageBonusPerStep;

        // 应用伤害加成
        event.setNewDamage((float) (event.getNewDamage() * (1 + damageBonus)));
    }
}
