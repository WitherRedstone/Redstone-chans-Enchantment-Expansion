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
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 伏击：潜行时首次攻击增加伤害
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class AmbushEventHandler {
    private static final Identifier AMBUSH_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "ambush");
    private static final Map<UUID, Boolean> hasAttackedMap = new HashMap<>();

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getDirectEntity() instanceof Player attacker)) return;

        LivingEntity target = event.getEntity();
        ItemStack weapon = attacker.getMainHandItem();

        if (weapon.isEmpty()) return;

        Holder.Reference<Enchantment> ambushEnchant = attacker.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(AMBUSH_ID)
                .orElse(null);

        if (ambushEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = weapon.getEnchantments().getLevel(ambushEnchant);
        if (level <= 0) return;

        UUID playerId = attacker.getUUID();

        // 检查是否在潜行
        if (!attacker.isCrouching()) {
            // 不在潜行，标记为已攻击过
            hasAttackedMap.put(playerId, true);
            return;
        }

        // 检查是否是潜行后的首次攻击
        Boolean hasAttacked = hasAttackedMap.getOrDefault(playerId, false);
        if (hasAttacked) {
            return;
        }
       
        // 潜行时的首次攻击，增加伤害（每级+20%）
        float bonus = level * 0.2F;
        float newDamage = event.getOriginalDamage() * (1 + bonus);
        event.setNewDamage(newDamage);

        // 标记为已攻击
        hasAttackedMap.put(playerId, true);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        UUID playerId = player.getUUID();

        // 如果玩家停止潜行，重置标记
        if (!player.isCrouching()) {
            hasAttackedMap.remove(playerId);
        }
    }
}
