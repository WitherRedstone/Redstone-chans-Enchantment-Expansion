package com.chinaex123.redstone_enchants.event.enchantment.all_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 药水箭附魔：箭矢命中方块或生物后，在原地留下一团对应的滞留药水云。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class SplashArrowEventHandler {

    private static final Map<String, String> CLOUDS = new LinkedHashMap<>();

    static {
        CLOUDS.put("splash_blindness",
                "{id:\"minecraft:area_effect_cloud\", Particle: {type:\"dust_color_transition\",from_color:[0.122, 0.122, 0.137],scale:1,to_color:[0.122, 0.122, 0.137]}, Radius:2f, Duration:100, potion_contents:{custom_effects:[{id:\"minecraft:blindness\",amplifier:0,duration:100,show_particles:1b,show_icon:0b,ambient:1b}]}}");
        CLOUDS.put("splash_delayed_explosion",
                "{id:\"minecraft:area_effect_cloud\", Particle: {type:\"small_flame\",scale:1.0,}, Radius:2f, Duration:40, potion_contents:{custom_effects:[{id:\"ars_nouveau:blasting\",amplifier:0,duration:50,show_particles:0b,show_icon:0b,ambient:0b}]}}");
        CLOUDS.put("splash_glowing",
                "{id:\"minecraft:area_effect_cloud\", Particle: {type:\"dust_color_transition\",from_color:[1.000,0.933,0.020],scale:1,to_color:[1.000,0.988,0.620]}, Radius:2f, Duration:100, potion_contents:{custom_effects:[{id:\"minecraft:glowing\",amplifier:0,duration:100,show_particles:1b,show_icon:0b,ambient:1b}]}}");
        CLOUDS.put("splash_hunger",
                "{id:\"minecraft:area_effect_cloud\", Particle: {type:\"dust_color_transition\",from_color:[0.345, 0.463, 0.325],scale:1,to_color:[0.345, 0.463, 0.325]}, Radius:2f, Duration:100, potion_contents:{custom_effects:[{id:\"minecraft:hunger\",amplifier:0,duration:100,show_particles:1b,show_icon:0b,ambient:1b}]}}");
        CLOUDS.put("splash_infested",
                "{id:\"minecraft:area_effect_cloud\", Particle: {type:\"dust_color_transition\",from_color:[0.549, 0.608, 0.549],scale:1,to_color:[0.549, 0.608, 0.549]}, Radius:2f, Duration:100, potion_contents:{custom_effects:[{id:\"minecraft:infested\",amplifier:0,duration:100,show_particles:1b,show_icon:0b,ambient:1b}]}}");
        CLOUDS.put("splash_oozing",
                "{id:\"minecraft:area_effect_cloud\", Particle: {type:\"dust_color_transition\",from_color:[0.6, 1.0, 0.639],scale:1,to_color:[0.6, 1.0, 0.639]}, Radius:2f, Duration:100, potion_contents:{custom_effects:[{id:\"minecraft:oozing\",amplifier:0,duration:100,show_particles:1b,show_icon:0b,ambient:1b}]}}");
        CLOUDS.put("splash_poison",
                "{id:\"minecraft:area_effect_cloud\", Particle: {type:\"dust_color_transition\",from_color:[0.529,0.639,0.388],scale:1,to_color:[0.529,0.639,0.388]}, Radius:2f, Duration:100, potion_contents:{custom_effects:[{id:\"minecraft:poison\",amplifier:0,duration:100,show_particles:1b,show_icon:0b,ambient:1b}]}}");
        CLOUDS.put("splash_regeneration",
                "{id:\"minecraft:area_effect_cloud\", Particle: {type:\"dust_color_transition\",from_color:[1.0, 0.239, 0.239],scale:1,to_color:[1.0, 0.239, 0.239]}, Radius:2f, Duration:100, potion_contents:{custom_effects:[{id:\"minecraft:regeneration\",amplifier:0,duration:100,show_particles:1b,show_icon:0b,ambient:1b}]}}");
        CLOUDS.put("splash_slow_falling",
                "{id:\"minecraft:area_effect_cloud\", Particle: {type:\"dust_color_transition\",from_color:[0.953, 0.812, 0.725],scale:1,to_color:[0.953, 0.812, 0.725]}, Radius:2f, Duration:100, potion_contents:{custom_effects:[{id:\"minecraft:slow_falling\",amplifier:0,duration:100,show_particles:1b,show_icon:0b,ambient:1b}]}}");
        CLOUDS.put("splash_slowness",
                "{id:\"minecraft:area_effect_cloud\", Particle: {type:\"dust_color_transition\",from_color:[0.545,0.686,0.878],scale:1,to_color:[0.545,0.686,0.878]}, Radius:2f, Duration:100, potion_contents:{custom_effects:[{id:\"minecraft:slowness\",amplifier:0,duration:100,show_particles:1b,show_icon:0b,ambient:1b}]}}");
        CLOUDS.put("splash_speed",
                "{id:\"minecraft:area_effect_cloud\", Particle: {type:\"dust_color_transition\",from_color:[0.200,0.922,1.000],scale:1,to_color:[0.200,0.922,1.000]}, Radius:2f, Duration:100, potion_contents:{custom_effects:[{id:\"minecraft:speed\",amplifier:0,duration:100,show_particles:1b,show_icon:0b,ambient:1b}]}}");
        CLOUDS.put("splash_weaving",
                "{id:\"minecraft:area_effect_cloud\", Particle: {type:\"dust_color_transition\",from_color:[0.471, 0.412, 0.353],scale:1,to_color:[0.471, 0.412, 0.353]}, Radius:2f, Duration:100, potion_contents:{custom_effects:[{id:\"minecraft:weaving\",amplifier:0,duration:100,show_particles:1b,show_icon:0b,ambient:1b}]}}");
        CLOUDS.put("splash_wind_charged",
                "{id:\"minecraft:area_effect_cloud\", Particle: {type:\"dust_color_transition\",from_color:[0.741, 0.788, 1.0],scale:1,to_color:[0.741, 0.788, 1.0]}, Radius:2f, Duration:100, potion_contents:{custom_effects:[{id:\"minecraft:wind_charged\",amplifier:0,duration:100,show_particles:1b,show_icon:0b,ambient:1b}]}}");
        CLOUDS.put("splash_wither",
                "{id:\"minecraft:area_effect_cloud\", Particle: {type:\"dust_color_transition\",from_color:[0.486,0.490,0.475],scale:1,to_color:[0.000,0.000,0.000]}, Radius:2f, Duration:100, potion_contents:{custom_effects:[{id:\"minecraft:wither\",amplifier:0,duration:100,show_particles:1b,show_icon:0b,ambient:1b}]}}");

    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof AbstractArrow arrow)) return;
        if (!(event.getRayTraceResult() instanceof net.minecraft.world.phys.BlockHitResult hit)) return;
        if (!(arrow.level() instanceof ServerLevel level)) return;

        EnchantContext context = new EnchantContext(level, arrow, hit.getLocation());
        for (Map.Entry<String, String> entry : CLOUDS.entrySet()) {
            if (EnchantContext.level(level, entry.getKey(), arrow.getWeaponItem()) <= 0) continue;
            spawnCloud(context, entry.getValue());
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)) return;
        if (!(arrow.level() instanceof ServerLevel level)) return;

        EnchantContext context = new EnchantContext(level, event.getEntity(), event.getEntity().position());
        for (Map.Entry<String, String> entry : CLOUDS.entrySet()) {
            if (EnchantContext.level(level, entry.getKey(), arrow.getWeaponItem()) <= 0) continue;
            spawnCloud(context, entry.getValue());
        }
    }

    private static void spawnCloud(EnchantContext context, String snbt) {
        Entity cloud = context.summon(snbt);
        if (cloud != null) {
            cloud.setDeltaMovement(Vec3.ZERO);
        }
        context.particleAt(context.pos().add(0.0D, 0.7D, 0.0D), ParticleTypes.SCULK_SOUL, 25, 1.0D, 0.0D, 1.0D, 0.01D);
    }
}
