package com.chinaex123.redstone_enchants.event.enchantment.head_armor;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 移动轨迹系列附魔。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class TrailEventHandler {

    /** 每次触发产生的粒子数量。 */
    private static final int PARTICLE_COUNT = 3;
    /** 粒子扩散范围。 */
    private static final double SPREAD = 0.3D;
    /** 粒子速度。 */
    private static final double SPEED = 0.0D;
    /** 粒子生成位置的垂直偏移（格）。 */
    private static final double Y_OFFSET = 1.0D;
    /** 粒子生成位置的水平偏移（格，不做水平偏移）。 */
    private static final double HORIZONTAL_OFFSET = 0.0D;

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
    public static void onPlayerTick(Post event) {
        if (event.getEntity().level().isClientSide()) return;
        Player player = event.getEntity();
        if (!(player.level() instanceof ServerLevel level)) return;
        if (!EnchantContext.moved(player)) return;

        EnchantContext context = new EnchantContext(level, player,
                player.position().add(HORIZONTAL_OFFSET, Y_OFFSET, HORIZONTAL_OFFSET));
        for (Map.Entry<String, ParticleOptions> entry : TRAILS.entrySet()) {
            int enchantLevel = EnchantContext.level(player, entry.getKey());
            if (enchantLevel <= 0) continue;
            context.particle(entry.getValue(), PARTICLE_COUNT, SPREAD, SPREAD, SPREAD, SPEED);
        }
    }
}
