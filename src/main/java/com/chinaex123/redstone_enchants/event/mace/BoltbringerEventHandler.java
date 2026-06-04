package com.chinaex123.redstone_enchants.event.mace;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 闪电使者：从高处(≥8格)攻击时召唤闪电
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class BoltbringerEventHandler {
    private static final Identifier BOLTBRINGER_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "boltbringer");
    private static final Map<UUID, Long> lastStrikeTime = new HashMap<>();
    private static final float MIN_FALL_DISTANCE = 8.0f; // 从高处攻击的下落距离
    private static final long COOLDOWN_TICKS = 10; // 冷却时间

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getDirectEntity() instanceof Player attacker)) return;

        LivingEntity target = event.getEntity();
        ItemStack weapon = attacker.getMainHandItem();

        if (weapon.isEmpty()) return;

        Holder.Reference<Enchantment> boltbringerEnchant = attacker.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(BOLTBRINGER_ID)
                .orElse(null);

        if (boltbringerEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = weapon.getEnchantments().getLevel(boltbringerEnchant);
        if (level <= 0) return;

        // 检查是否从高处攻击
        float fallDistance = (float) attacker.fallDistance;
        if (fallDistance < MIN_FALL_DISTANCE) return;

        UUID attackerId = attacker.getUUID();
        long currentTime = attacker.level().getGameTime();

        // 检查冷却时间
        Long lastTime = lastStrikeTime.get(attackerId);
        if (lastTime != null && currentTime - lastTime < COOLDOWN_TICKS) return;

        lastStrikeTime.put(attackerId, currentTime);

        // 在目标位置召唤闪电
        if (attacker.level() instanceof ServerLevel serverLevel) {
            LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(serverLevel, null);
            if (lightning != null) {
                lightning.setPos(target.getX(), target.getY(), target.getZ());
                serverLevel.addFreshEntity(lightning);
            }
        }

        // 清理旧记录
        if (lastStrikeTime.size() > 100) {
            lastStrikeTime.clear();
        }
    }
}