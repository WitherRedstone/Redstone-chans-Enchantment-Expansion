package com.chinaex123.redstone_enchants.event.curse;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 水源诅咒：在水中或雨中时施加中毒效果
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class CurseOfWaterSourceEventHandler {
    private static final Map<UUID, Long> LAST_EFFECT_TIME = new HashMap<>();
    private static final Identifier CURSE_OF_WATER_SOURCE_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "curse_of_water_source");

    @SubscribeEvent
    @SuppressWarnings("deprecation")
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        LivingEntity player = event.getEntity();

        // 检查是否在水中或雨中
        boolean inWater = player.isInWater();
        boolean inRain = player.level().isRaining() && player.level().canSeeSky(player.blockPosition());

        if (!inWater && !inRain) {
            if (player.hasEffect(MobEffects.POISON)) {
                player.removeEffect(MobEffects.POISON);
            }
            return;
        }

        UUID entityId = player.getUUID();
        long currentTime = player.level().getGameTime();
        Long lastTime = LAST_EFFECT_TIME.get(entityId);

        // 每秒执行一次（20 tick）
        if (lastTime != null && currentTime - lastTime < 20) return;

        LAST_EFFECT_TIME.put(entityId, currentTime);

        // 检查所有装备槽
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = player.getItemBySlot(slot);
            if (stack.isEmpty()) continue;

            Holder.Reference<Enchantment> curseEnchant = player.level()
                    .registryAccess()
                    .lookupOrThrow(Registries.ENCHANTMENT)
                    .get(CURSE_OF_WATER_SOURCE_ID)
                    .orElse(null);

            if (curseEnchant == null) continue;

            // 检查当前槽位的物品是否有水源诅咒
            int level = stack.getEnchantments().getLevel(curseEnchant);
            if (level <= 0) continue;

            // 施加中毒效果
            player.addEffect(new MobEffectInstance(MobEffects.POISON, 40, 0));
            break;
        }
    }
}
