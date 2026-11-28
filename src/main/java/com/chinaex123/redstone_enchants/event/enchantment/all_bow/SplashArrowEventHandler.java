package com.chinaex123.redstone_enchants.event.enchantment.all_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.joml.Vector3f;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 药水箭附魔：箭矢命中方块或生物后，在原地留下一团对应的滞留药水云。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class SplashArrowEventHandler {

    /** 药水云生成后的灵魂粒子数量 */
    private static final int PARTICLE_COUNT = 25;
    /** 粒子生成点相对药水云的垂直偏移（格） */
    private static final double PARTICLE_OFFSET_Y = 0.7D;
    /** 粒子生成点相对药水云的水平偏移（格，不做水平偏移） */
    private static final double PARTICLE_OFFSET_HORIZONTAL = 0.0D;
    /** 粒子水平扩散范围（格） */
    private static final double PARTICLE_SPREAD_HORIZONTAL = 1.0D;
    /** 粒子垂直扩散范围（格） */
    private static final double PARTICLE_SPREAD_VERTICAL = 0.0D;
    /** 粒子速度 */
    private static final double PARTICLE_SPEED = 0.01D;
    /** 药水云半径（格） */
    private static final float CLOUD_RADIUS = 2.0F;
    /** 药水云生效前的等待刻数 */
    private static final int CLOUD_WAIT_TIME = 0;
    /** 粉尘粒子的缩放 */
    private static final float DUST_SCALE = 1.0F;
    /** 普通药水云持续刻数 */
    private static final int CLOUD_DURATION = 100;
    /** 普通药水云施加的效果刻数 */
    private static final int EFFECT_DURATION = 100;
    /** 延迟爆炸药水云持续刻数 */
    private static final int DELAYED_CLOUD_DURATION = 40;
    /** 延迟爆炸施加的爆炸效果刻数 */
    private static final int DELAYED_EFFECT_DURATION = 50;

    /**
     * 一团药水云的配置。
     *
     * @param cloudDuration  云持续刻数
     * @param effectDuration 施加的效果刻数
     * @param particle       云的粒子效果
     * @param effect         云施加的药水效果（模组效果缺失时为 null，此时不生成）
     * @param ambient        效果是否为环境效果（不显示药水图标边框）
     * @param visible        是否显示效果粒子
     */
    private record Cloud(int cloudDuration, int effectDuration, ParticleOptions particle,
                         Holder<MobEffect> effect, boolean ambient, boolean visible) {
    }

    /** 各药水箭附魔对应的滞留药水云（键为附魔 ID） */
    private static final Map<String, Cloud> CLOUDS = new LinkedHashMap<>();

    static {
        CLOUDS.put("splash_blindness", dust(0.122F, 0.122F, 0.137F, 0.122F, 0.122F, 0.137F, MobEffects.BLINDNESS));
        CLOUDS.put("splash_delayed_explosion", new Cloud(DELAYED_CLOUD_DURATION, DELAYED_EFFECT_DURATION,
                ParticleTypes.SMALL_FLAME, moddedEffect("ars_nouveau", "blasting"), false, false));
        CLOUDS.put("splash_glowing", dust(1.000F, 0.933F, 0.020F, 1.000F, 0.988F, 0.620F, MobEffects.GLOWING));
        CLOUDS.put("splash_hunger", dust(0.345F, 0.463F, 0.325F, 0.345F, 0.463F, 0.325F, MobEffects.HUNGER));
        CLOUDS.put("splash_infested", dust(0.549F, 0.608F, 0.549F, 0.549F, 0.608F, 0.549F, MobEffects.INFESTED));
        CLOUDS.put("splash_oozing", dust(0.6F, 1.0F, 0.639F, 0.6F, 1.0F, 0.639F, MobEffects.OOZING));
        CLOUDS.put("splash_poison", dust(0.529F, 0.639F, 0.388F, 0.529F, 0.639F, 0.388F, MobEffects.POISON));
        CLOUDS.put("splash_regeneration", dust(1.0F, 0.239F, 0.239F, 1.0F, 0.239F, 0.239F, MobEffects.REGENERATION));
        CLOUDS.put("splash_slow_falling", dust(0.953F, 0.812F, 0.725F, 0.953F, 0.812F, 0.725F, MobEffects.SLOW_FALLING));
        CLOUDS.put("splash_slowness", dust(0.545F, 0.686F, 0.878F, 0.545F, 0.686F, 0.878F, MobEffects.MOVEMENT_SLOWDOWN));
        CLOUDS.put("splash_speed", dust(0.200F, 0.922F, 1.000F, 0.200F, 0.922F, 1.000F, MobEffects.MOVEMENT_SPEED));
        CLOUDS.put("splash_weaving", dust(0.471F, 0.412F, 0.353F, 0.471F, 0.412F, 0.353F, MobEffects.WEAVING));
        CLOUDS.put("splash_wind_charged", dust(0.741F, 0.788F, 1.0F, 0.741F, 0.788F, 1.0F, MobEffects.WIND_CHARGED));
        CLOUDS.put("splash_wither", dust(0.486F, 0.490F, 0.475F, 0.000F, 0.000F, 0.000F, MobEffects.WITHER));
    }

    /**
     * 构造一团「彩色粉尘」形态的药水云。
     */
    private static Cloud dust(float fromRed, float fromGreen, float fromBlue,
                              float toRed, float toGreen, float toBlue, Holder<MobEffect> effect) {
        return new Cloud(CLOUD_DURATION, EFFECT_DURATION,
                new DustColorTransitionOptions(new Vector3f(fromRed, fromGreen, fromBlue),
                        new Vector3f(toRed, toGreen, toBlue), DUST_SCALE),
                effect, true, true);
    }

    /**
     * 取模组提供药水效果；未安装对应模组时返回 null（对应附魔本身也不会注册）。
     */
    private static Holder<MobEffect> moddedEffect(String namespace, String path) {
        ResourceKey<MobEffect> key = ResourceKey.create(Registries.MOB_EFFECT,
                ResourceLocation.fromNamespaceAndPath(namespace, path));
        return BuiltInRegistries.MOB_EFFECT.getHolder(key).orElse(null);
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (event.getProjectile().level().isClientSide()) return;
        if (!(event.getProjectile() instanceof AbstractArrow arrow)) return;
        if (!(event.getRayTraceResult() instanceof net.minecraft.world.phys.BlockHitResult hit)) return;
        if (!(arrow.level() instanceof ServerLevel level)) return;

        EnchantContext context = new EnchantContext(level, arrow, hit.getLocation());
        for (Map.Entry<String, Cloud> entry : CLOUDS.entrySet()) {
            int enchantLevel = EnchantContext.level(level, entry.getKey(), arrow.getWeaponItem());
            if (enchantLevel <= 0) continue;
            spawnCloud(context, entry.getValue());
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)) return;
        if (!(arrow.level() instanceof ServerLevel level)) return;

        EnchantContext context = new EnchantContext(level, event.getEntity(), event.getEntity().position());
        for (Map.Entry<String, Cloud> entry : CLOUDS.entrySet()) {
            int enchantLevel = EnchantContext.level(level, entry.getKey(), arrow.getWeaponItem());
            if (enchantLevel <= 0) continue;
            spawnCloud(context, entry.getValue());
        }
    }

    /**
     * 在当前位置生成一团药水云。
     */
    private static void spawnCloud(EnchantContext context, Cloud cloud) {
        if (cloud.effect() == null) {
            return;
        }
        AreaEffectCloud entity = new AreaEffectCloud(context.level(), context.pos().x, context.pos().y, context.pos().z);
        entity.setRadius(CLOUD_RADIUS);
        entity.setDuration(cloud.cloudDuration());
        entity.setWaitTime(CLOUD_WAIT_TIME);
        entity.setParticle(cloud.particle());
        entity.setPotionContents(new PotionContents(Optional.empty(), Optional.empty(),
                List.of(new MobEffectInstance(cloud.effect(), cloud.effectDuration(), 0,
                        cloud.ambient(), cloud.visible(), false))));
        context.spawn(entity);
        entity.setDeltaMovement(Vec3.ZERO);
        context.particleAt(context.pos().add(PARTICLE_OFFSET_HORIZONTAL, PARTICLE_OFFSET_Y, PARTICLE_OFFSET_HORIZONTAL),
                ParticleTypes.SCULK_SOUL, PARTICLE_COUNT,
                PARTICLE_SPREAD_HORIZONTAL, PARTICLE_SPREAD_VERTICAL, PARTICLE_SPREAD_HORIZONTAL, PARTICLE_SPEED);
    }
}
