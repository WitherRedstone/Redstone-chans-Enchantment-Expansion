package com.chinaex123.redstone_enchants.event.swords;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
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
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class BackstabEventHandler {
    private static final Identifier BACKSTAB_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "backstab");

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;

        LivingEntity target = event.getEntity();

        ItemStack weapon = attacker.getMainHandItem();
        Holder.Reference<Enchantment> backstabEnchant = attacker.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(BACKSTAB_ID)
                .orElse(null);

        if (backstabEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = weapon.getEnchantments().getLevel(backstabEnchant);
        if (level <= 0) return;

        // 计算攻击者相对于目标的方向向量
        double dx = target.getX() - attacker.getX();
        double dz = target.getZ() - attacker.getZ();

        // 计算目标朝向的单位向量
        float targetYawRad = target.getYRot() * (float) Math.PI / 180.0f;
        float targetLookX = -Mth.sin(targetYawRad);
        float targetLookZ = Mth.cos(targetYawRad);

        // 计算点积：>0 表示攻击者在目标背后，<0 表示在正面
        double dotProduct = dx * targetLookX + dz * targetLookZ;

        float damageMultiplier = 1.0f;

        // 背后攻击（点积 > 0）：每级+30%
        if (dotProduct > 0) {
            damageMultiplier = 1.0f + (0.3f * level);
        }
        // 正面攻击（点积 < 0）：每级-15%
        else {
            damageMultiplier = 1.0f - (0.15f * level);
        }

        event.setNewDamage(event.getNewDamage() * damageMultiplier);
    }
}
