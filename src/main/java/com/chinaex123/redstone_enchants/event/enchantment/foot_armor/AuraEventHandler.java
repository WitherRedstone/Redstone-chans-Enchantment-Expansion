package com.chinaex123.redstone_enchants.event.enchantment.foot_armor;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * 光环类附魔：穿戴者移动时，对周围生物施加对应状态效果。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class AuraEventHandler {

    /** 光环半径。 */
    private static final double RADIUS = 4.0D;
    /** 燃烧光环半径。 */
    private static final double BURNING_RADIUS = 2.0D;
    /** 光环效果持续时间（tick）。 */
    private static final int DURATION = 100;
    /** 光环效果等级（0 = I 级）。 */
    private static final int AMPLIFIER = 0;
    /** 燃烧光环点燃时长（秒）。 */
    private static final int BURNING_SECONDS = 4;

    private static final Map<String, Holder<MobEffect>> AURAS = new LinkedHashMap<>();

    static {
        AURAS.put("aura_speed", MobEffects.MOVEMENT_SPEED);
        AURAS.put("aura_slowness", MobEffects.MOVEMENT_SLOWDOWN);
        AURAS.put("aura_haste", MobEffects.DIG_SPEED);
        AURAS.put("aura_strength", MobEffects.DAMAGE_BOOST);
        AURAS.put("aura_jump_boost", MobEffects.JUMP);
        AURAS.put("aura_regeneration", MobEffects.REGENERATION);
        AURAS.put("aura_resistance", MobEffects.DAMAGE_RESISTANCE);
        AURAS.put("aura_weakness", MobEffects.WEAKNESS);
        AURAS.put("aura_poison", MobEffects.POISON);
        AURAS.put("aura_wither", MobEffects.WITHER);
        AURAS.put("aura_glowing", MobEffects.GLOWING);
        AURAS.put("aura_infested", MobEffects.INFESTED);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!(player.level() instanceof ServerLevel level)) return;
        // 只在移动时触发
        if (player.getDeltaMovement().horizontalDistanceSqr() < 1.0E-4D) return;

        EnchantContext context = new EnchantContext(level, player, player.position());

        for (Map.Entry<String, Holder<MobEffect>> entry : AURAS.entrySet()) {
            if (EnchantContext.level(player, entry.getKey()) <= 0) continue;
            for (Entity target : context.entities(RADIUS, Integer.MAX_VALUE, true, AuraEventHandler::isLiving)) {
                context.applyEffect(target, entry.getValue(), DURATION, AMPLIFIER);
            }
        }

        if (EnchantContext.level(player, "aura_burning") > 0) {
            for (Entity target : context.entities(BURNING_RADIUS, Integer.MAX_VALUE, false, AuraEventHandler::isLiving)) {
                target.setRemainingFireTicks(BURNING_SECONDS * 20);
            }
            context.effect(MobEffects.FIRE_RESISTANCE, DURATION / 20.0F, AMPLIFIER);
        }
    }

    private static boolean isLiving(Entity entity) {
        return entity instanceof LivingEntity;
    }
}
