package com.chinaex123.redstone_enchants.event.enchantment.swords;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class EqualizerEventHandler {
    private static final ResourceLocation EQUALIZER_ID = RedstoneEnchants.id("equalizer");

    /** 1 级时的伤害加成系数 */
    private static final double DAMAGE_BONUS_BASE = 0.2D;
    /** 每级额外增加的伤害加成系数 */
    private static final double DAMAGE_BONUS_PER_LEVEL = 0.2D;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getDirectEntity() instanceof Player attacker)) return;

        LivingEntity target = event.getEntity();
        ItemStack weapon = attacker.getMainHandItem();

        if (weapon.isEmpty()) return;

        ResourceKey<Enchantment> equalizerEnchant = EnchantContext.key(EQUALIZER_ID);

        int level = EnchantContext.level(weapon, equalizerEnchant);
        if (level <= 0) return;

        // 计算目标血量百分比
        float maxHealth = target.getMaxHealth();
        float currentHealth = target.getHealth();
        float healthPercentage = currentHealth / maxHealth;

        // 根据血量百分比和附魔等级计算伤害加成（1 级 0.2，每级额外 +0.2）
        double bonusPerLevel = DAMAGE_BONUS_BASE + DAMAGE_BONUS_PER_LEVEL * (level - 1);
        float bonusMultiplier = (float) (healthPercentage * bonusPerLevel);

        float originalDamage = event.getOriginalDamage();
        float newDamage = originalDamage * (1 + bonusMultiplier);
        event.setNewDamage(newDamage);
    }
}
