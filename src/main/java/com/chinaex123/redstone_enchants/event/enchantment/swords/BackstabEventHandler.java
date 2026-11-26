package com.chinaex123.redstone_enchants.event.enchantment.swords;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 背刺：从背后攻击造成额外伤害，而从正面攻击则伤害降低
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class BackstabEventHandler {
    private static final ResourceLocation BACKSTAB_ID = RedstoneEnchants.id("backstab");

    /** 背刺伤害倍率 1 级基础值 */
    private static final double BACK_DAMAGE_BASE = 1.3D;
    /** 背刺每级额外增加的伤害倍率 */
    private static final double BACK_DAMAGE_PER_LEVEL = 0.3D;
    /** 正面攻击伤害倍率 1 级基础值 */
    private static final double FRONT_DAMAGE_BASE = 0.85D;
    /** 正面攻击每级减少的伤害倍率 */
    private static final double FRONT_DAMAGE_PER_LEVEL = 0.15D;
    /** 角度制换算为弧度制的除数 */
    private static final float DEGREES_TO_RADIANS_DIVISOR = 180.0F;
    /** 伤害倍率的初始值 */
    private static final float DAMAGE_MULTIPLIER_DEFAULT = 1.0F;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;

        LivingEntity target = event.getEntity();

        ItemStack weapon = attacker.getMainHandItem();
        ResourceKey<Enchantment> backstabEnchant = EnchantContext.key(BACKSTAB_ID);

        int level = EnchantContext.level(weapon, backstabEnchant);
        if (level <= 0) return;

        // 计算攻击者相对于目标的方向向量
        float damageMultiplier = getDamageMultiplier(attacker, target, level);

        event.setNewDamage(event.getNewDamage() * damageMultiplier);
    }

    private static float getDamageMultiplier(LivingEntity attacker, LivingEntity target, int level) {
        double dx = target.getX() - attacker.getX();
        double dz = target.getZ() - attacker.getZ();

        // 计算目标朝向的单位向量
        float targetYawRad = target.getYRot() * (float) Math.PI / DEGREES_TO_RADIANS_DIVISOR;
        float targetLookX = -Mth.sin(targetYawRad);
        float targetLookZ = Mth.cos(targetYawRad);

        // 计算点积：>0 表示攻击者在目标背后，<0 表示在正面
        double dotProduct = dx * targetLookX + dz * targetLookZ;

        float damageMultiplier = DAMAGE_MULTIPLIER_DEFAULT;

        // 背后攻击（点积 > 0）：1 级 +30%，每级额外 +30%
        if (dotProduct > 0) {
            damageMultiplier = (float) (BACK_DAMAGE_BASE + BACK_DAMAGE_PER_LEVEL * (level - 1));
        }
        // 正面攻击（点积 < 0）：1 级 -15%，每级额外 -15%
        else {
            damageMultiplier = (float) (FRONT_DAMAGE_BASE - FRONT_DAMAGE_PER_LEVEL * (level - 1));
        }
        return damageMultiplier;
    }
}
