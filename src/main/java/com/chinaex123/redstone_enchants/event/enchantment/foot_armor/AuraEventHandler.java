package com.chinaex123.redstone_enchants.event.enchantment.foot_armor;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 光环系列附魔。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class AuraEventHandler {

    /** 光环半径（格）。 */
    private static final double RADIUS = 4.0D;
    /** 燃烧光环半径（格）。 */
    private static final double BURNING_RADIUS = 2.0D;
    /** 光环效果持续时间（tick）。 */
    private static final int DURATION_TICKS = 100;
    /** 光环效果等级（0 = I 级）。 */
    private static final int AMPLIFIER = 0;
    /** tick 换算为秒的系数。 */
    private static final float TICKS_PER_SECOND = 20.0F;

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
    public static void onPlayerTick(Post event) {
        if (event.getEntity().level().isClientSide()) return;
        Player player = event.getEntity();
        if (!(player.level() instanceof ServerLevel level)) return;
        if (!EnchantContext.moved(player)) return;

        EnchantContext context = new EnchantContext(level, player, player.position());

        for (Map.Entry<String, Holder<MobEffect>> entry : AURAS.entrySet()) {
            int enchantLevel = EnchantContext.level(player, entry.getKey());
            if (enchantLevel <= 0) continue;
            for (Entity target : context.entities(RADIUS, Integer.MAX_VALUE, true, AuraEventHandler::isLiving)) {
                context.applyEffect(target, entry.getValue(), DURATION_TICKS, AMPLIFIER);
            }
        }

        if (EnchantContext.level(player, "aura_burning") > 0) {
            igniteGround(player, level);
            context.effect(MobEffects.FIRE_RESISTANCE, DURATION_TICKS / TICKS_PER_SECOND, AMPLIFIER);
        }
    }

    private static void igniteGround(Player player, ServerLevel level) {
        BlockPos feet = player.blockPosition();
        int radius = (int) Math.floor(BURNING_RADIUS);
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (dx * dx + dz * dz > BURNING_RADIUS * BURNING_RADIUS) continue;
                BlockPos pos = feet.offset(dx, 0, dz);
                if (!level.isEmptyBlock(pos)) continue;
                BlockState fire = Blocks.FIRE.defaultBlockState();
                if (!fire.canSurvive(level, pos)) continue;
                level.setBlockAndUpdate(pos, fire);
            }
        }
    }

    private static boolean isLiving(Entity entity) {
        return entity instanceof LivingEntity;
    }
}
