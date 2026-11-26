package com.chinaex123.redstone_enchants.event.enchantment.all_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 连锁反应：受伤的生物有几率使它们向附近的生物发射箭矢。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class ChainReactionEventHandler {

    private static final String ID = "chain_reaction";
    private static final String ARROW_ID = "minecraft:arrow";
    private static final String SPECTRAL_ARROW_ID = "minecraft:spectral_arrow";

    /** 箭矢发射方向表（单位圆上的六个水平方向：东、西及四个斜向） */
    private static final double[][] MOTIONS = {
            {1.0D, 0.0D},
            {-1.0D, 0.0D},
            {0.5D, 0.866D},
            {-0.5D, 0.866D},
            {0.5D, -0.866D},
            {-0.5D, -0.866D}
    };

    /** 1 级发射的箭矢数量 */
    private static final int ARROW_COUNT_BASE = 6;
    /** 每级额外增加的箭矢数量 */
    private static final int ARROW_COUNT_PER_LEVEL = 2;
    /** 召唤位置相对实体的垂直偏移（格） */
    private static final double SUMMON_OFFSET_Y = 0.8D;
    /** 召唤位置的水平偏移（格，不做水平偏移） */
    private static final double SUMMON_OFFSET_HORIZONTAL = 0.0D;
    /** 箭矢运动向量的垂直分量（保持水平发射） */
    private static final double MOTION_Y = 0.0D;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)) return;
        if (!(arrow.level() instanceof ServerLevel level)) return;
        int enchantLevel = EnchantContext.level(level, ID, arrow.getWeaponItem());
        if (enchantLevel <= 0) return;

        EnchantContext context = new EnchantContext(level, event.getEntity(), event.getEntity().position());
        String entityId = arrow instanceof SpectralArrow ? SPECTRAL_ARROW_ID : ARROW_ID;
        String snbt = "{id:\"" + entityId + "\",pickup:2b}";

        int arrowCount = ARROW_COUNT_BASE + ARROW_COUNT_PER_LEVEL * (enchantLevel - 1);
        for (int i = 0; i < arrowCount; i++) {
            double[] motion = MOTIONS[i % MOTIONS.length];
            Entity spawned = context.summon(snbt, SUMMON_OFFSET_HORIZONTAL, SUMMON_OFFSET_Y, SUMMON_OFFSET_HORIZONTAL);
            if (spawned != null) {
                context.motion(spawned, new Vec3(motion[0], MOTION_Y, motion[1]));
            }
        }
    }
}
