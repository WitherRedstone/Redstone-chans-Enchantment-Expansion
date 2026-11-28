package com.chinaex123.redstone_enchants.event.enchantment.curse;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.event.EnchantEffectTracker;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
 * 水源诅咒：接触水会给予中毒效果
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class CurseOfWaterSourceEventHandler {
    private static final ResourceLocation CURSE_OF_WATER_SOURCE_ID = RedstoneEnchants.id("curse_of_water_source");
    private static final Map<UUID, Long> LAST_EFFECT_TIME = new HashMap<>();

    /** 每秒执行的间隔（tick）。 */
    private static final long INTERVAL_TICKS = 20L;
    /** 中毒持续时间（tick）。 */
    private static final int POISON_DURATION = 40;
    /** 中毒效果等级（0 为 I 级）。 */
    private static final int POISON_AMPLIFIER = 0;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        LivingEntity player = event.getEntity();

        // 检查是否在水中或雨中
        boolean inWater = player.isInWater();
        boolean inRain = player.level().isRaining() && player.level().canSeeSky(player.blockPosition());

        if (!inWater && !inRain) {
            EnchantEffectTracker.removeIfTracked(player, MobEffects.POISON);
            return;
        }

        UUID entityId = player.getUUID();
        long currentTime = player.level().getGameTime();
        Long lastTime = LAST_EFFECT_TIME.get(entityId);

        // 每秒执行一次（20 tick）
        if (lastTime != null && currentTime - lastTime < INTERVAL_TICKS) return;

        LAST_EFFECT_TIME.put(entityId, currentTime);

        ResourceKey<Enchantment> curseEnchant = EnchantContext.key(CURSE_OF_WATER_SOURCE_ID);

        // 检查所有装备槽
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = player.getItemBySlot(slot);
            if (stack.isEmpty()) continue;

            // 检查当前槽位的物品是否有水源诅咒
            int level = EnchantContext.level(stack, curseEnchant);
            if (level <= 0) continue;

            // 施加中毒效果
            EnchantEffectTracker.apply(player, MobEffects.POISON, POISON_DURATION, POISON_AMPLIFIER);
            break;
        }
    }
}
