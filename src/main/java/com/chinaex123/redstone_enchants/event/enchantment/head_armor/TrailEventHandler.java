package com.chinaex123.redstone_enchants.event.enchantment.head_armor;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * 拖尾类附魔：穿戴者在头顶留下对应颜色的粒子轨迹。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class TrailEventHandler {

    private static final Map<String, ParticleOptions> TRAILS = new LinkedHashMap<>();

    static {
        TRAILS.put("trail_cherry_leaves", ParticleTypes.CHERRY_LEAVES);
        TRAILS.put("trail_dragon_breath", ParticleTypes.DRAGON_BREATH);
        TRAILS.put("trail_firework", ParticleTypes.FIREWORK);
        TRAILS.put("trail_glow", ParticleTypes.GLOW);
        TRAILS.put("trail_sculk_soul", ParticleTypes.SCULK_SOUL);
        TRAILS.put("trail_snowflake", ParticleTypes.SNOWFLAKE);
        TRAILS.put("trail_trial_omen", ParticleTypes.TRIAL_OMEN);
        TRAILS.put("trail_wax_off", ParticleTypes.WAX_OFF);
        TRAILS.put("trail_wax_on", ParticleTypes.WAX_ON);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!(player.level() instanceof ServerLevel level)) return;
        if (player.getDeltaMovement().horizontalDistanceSqr() < 1.0E-4D) return;

        EnchantContext context = new EnchantContext(level, player, player.position().add(0.0D, 1.0D, 0.0D));
        for (Map.Entry<String, ParticleOptions> entry : TRAILS.entrySet()) {
            if (EnchantContext.level(player, entry.getKey()) <= 0) continue;
            context.particle(entry.getValue(), 3, 0.3D, 0.3D, 0.3D, 0.0D);
        }
    }
}
