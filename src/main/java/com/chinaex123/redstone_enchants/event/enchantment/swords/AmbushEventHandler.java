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
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 伏击：潜行时首次攻击增加伤害
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class AmbushEventHandler {
    private static final ResourceLocation AMBUSH_ID = RedstoneEnchants.id("ambush");

    /** 1 级时的伤害加成 */
    private static final double BONUS_BASE = 0.2D;
    /** 每级额外增加的伤害加成 */
    private static final double BONUS_PER_LEVEL = 0.2D;

    private static final Map<UUID, Boolean> hasAttackedMap = new HashMap<>();

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getDirectEntity() instanceof Player attacker)) return;

        LivingEntity target = event.getEntity();
        ItemStack weapon = attacker.getMainHandItem();

        if (weapon.isEmpty()) return;

        ResourceKey<Enchantment> ambushEnchant = EnchantContext.key(AMBUSH_ID);

        int level = EnchantContext.level(weapon, ambushEnchant);
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
       
        // 潜行时的首次攻击，增加伤害（1 级 +20%，每级额外 +20%）
        double bonus = BONUS_BASE + BONUS_PER_LEVEL * (level - 1);
        float newDamage = (float) (event.getOriginalDamage() * (1 + bonus));
        event.setNewDamage(newDamage);

        // 标记为已攻击
        hasAttackedMap.put(playerId, true);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        Player player = event.getEntity();
        UUID playerId = player.getUUID();

        // 如果玩家停止潜行，重置标记
        if (!player.isCrouching()) {
            hasAttackedMap.remove(playerId);
        }
    }
}
