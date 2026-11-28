package com.chinaex123.redstone_enchants.event.enchantment.trident;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;

/**
 * 海风：命中时产生推进风弹。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class SeaBreezeEventHandler {
    private static final ResourceLocation SEA_BREEZE_ID = RedstoneEnchants.id("sea_breeze");

    /** 爆炸半径 */
    private static final float EXPLOSION_RADIUS = 2.0f;
    /** 1 级时的基础伤害 */
    private static final float BASE_DAMAGE = 4.0f;
    /** 每级附魔增加的伤害 */
    private static final float DAMAGE_PER_LEVEL = 2.0f;
    /** 水平击退强度 */
    private static final float KNOCKBACK_HORIZONTAL = 1.2f;
    /** 垂直击退强度 */
    private static final float KNOCKBACK_VERTICAL = 0.3f;
    /** 云雾粒子数量 */
    private static final int CLOUD_PARTICLE_COUNT = 20;
    /** 云雾水平扩散倍率（相对爆炸半径） */
    private static final double CLOUD_SPREAD_FACTOR = 2.0D;
    /** 云雾粒子的垂直速度 */
    private static final double CLOUD_VERTICAL_SPEED = 0.1D;
    /** 命中音效音量 */
    private static final float SOUND_VOLUME = 1.5F;
    /** 命中音效音调 */
    private static final float SOUND_PITCH = 1.0F;
    /** 单次粒子发射的粒子数量 */
    private static final int PARTICLE_COUNT = 1;
    /** 粒子的零偏移 / 零扩散 / 零速度 */
    private static final double NO_OFFSET = 0.0D;
    /** 取生物身体中部的二等分除数 */
    private static final double CENTER_HEIGHT_DIVISOR = 2.0D;
    /** 距离衰减的基准倍率 */
    private static final double DISTANCE_FALLOFF_BASE = 1.0D;
    /** 随机偏移的中心值，用于让扩散范围对称分布 */
    private static final double RANDOM_CENTER = 0.5D;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        Entity directEntity = event.getSource().getDirectEntity();
        if (!(directEntity instanceof AbstractArrow arrow)) return;

        Entity owner = arrow.getOwner();
        if (!(owner instanceof LivingEntity attacker)) return;

        ItemStack weapon = arrow.getWeaponItem();
        if (weapon == null || weapon.isEmpty()) return;

        ResourceKey<Enchantment> seaBreezeEnchant = EnchantContext.key(SEA_BREEZE_ID);

        int level = EnchantContext.level(weapon, seaBreezeEnchant);
        if (level <= 0) return;

        LivingEntity target = event.getEntity();
        Vec3 explosionPos = target.position().add(NO_OFFSET, target.getBbHeight() / CENTER_HEIGHT_DIVISOR, NO_OFFSET);

        float damage = BASE_DAMAGE + (level - 1) * DAMAGE_PER_LEVEL;

        AABB explosionBox = new AABB(
                explosionPos.x - EXPLOSION_RADIUS,
                explosionPos.y - EXPLOSION_RADIUS,
                explosionPos.z - EXPLOSION_RADIUS,
                explosionPos.x + EXPLOSION_RADIUS,
                explosionPos.y + EXPLOSION_RADIUS,
                explosionPos.z + EXPLOSION_RADIUS
        );

        List<Entity> affectedEntities = attacker.level().getEntities(target, explosionBox,
                entity -> entity instanceof LivingEntity && entity != attacker);

        for (Entity entity : affectedEntities) {
            double distance = entity.position().distanceTo(explosionPos);
            float distanceMultiplier = (float) Math.max(NO_OFFSET, DISTANCE_FALLOFF_BASE - (distance / EXPLOSION_RADIUS));
            float finalDamage = damage * distanceMultiplier;

            if (finalDamage > 0) {
                entity.hurt(event.getSource(), finalDamage);

                Vec3 knockbackDir = entity.position().subtract(explosionPos).normalize();
                double knockbackStrength = KNOCKBACK_HORIZONTAL * distanceMultiplier;
                entity.setDeltaMovement(entity.getDeltaMovement().add(
                        knockbackDir.x * knockbackStrength,
                        KNOCKBACK_VERTICAL * distanceMultiplier,
                        knockbackDir.z * knockbackStrength
                ));
                entity.hurtMarked = true;
            }
        }

        if (attacker.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.GUST_EMITTER_SMALL,
                    explosionPos.x, explosionPos.y, explosionPos.z, PARTICLE_COUNT,
                    NO_OFFSET, NO_OFFSET, NO_OFFSET, NO_OFFSET);

            for (int i = 0; i < CLOUD_PARTICLE_COUNT; i++) {
                double offsetX = (attacker.getRandom().nextDouble() - RANDOM_CENTER) * EXPLOSION_RADIUS * CLOUD_SPREAD_FACTOR;
                double offsetY = (attacker.getRandom().nextDouble() - RANDOM_CENTER) * EXPLOSION_RADIUS;
                double offsetZ = (attacker.getRandom().nextDouble() - RANDOM_CENTER) * EXPLOSION_RADIUS * CLOUD_SPREAD_FACTOR;
                serverLevel.sendParticles(ParticleTypes.CLOUD,
                        explosionPos.x + offsetX,
                        explosionPos.y + offsetY,
                        explosionPos.z + offsetZ,
                        PARTICLE_COUNT, NO_OFFSET, CLOUD_VERTICAL_SPEED, NO_OFFSET, NO_OFFSET);
            }
        }

        attacker.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                SoundEvents.WIND_CHARGE_BURST, SoundSource.PLAYERS, SOUND_VOLUME, SOUND_PITCH);
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (event.getProjectile().level().isClientSide()) return;
        if (!(event.getProjectile() instanceof AbstractArrow arrow)) return;

        Entity owner = arrow.getOwner();
        if (!(owner instanceof LivingEntity attacker)) return;

        ItemStack weapon = arrow.getWeaponItem();
        if (weapon == null || weapon.isEmpty()) return;

        ResourceKey<Enchantment> seaBreezeEnchant = EnchantContext.key(SEA_BREEZE_ID);

        int level = EnchantContext.level(weapon, seaBreezeEnchant);
        if (level <= 0) return;

        HitResult hitResult = event.getRayTraceResult();
        if (hitResult.getType() != HitResult.Type.BLOCK) return;

        BlockHitResult blockHit = (BlockHitResult) hitResult;
        Vec3 hitPos = blockHit.getLocation();

        float damage = BASE_DAMAGE + (level - 1) * DAMAGE_PER_LEVEL;

        AABB explosionBox = new AABB(
                hitPos.x - EXPLOSION_RADIUS,
                hitPos.y - EXPLOSION_RADIUS,
                hitPos.z - EXPLOSION_RADIUS,
                hitPos.x + EXPLOSION_RADIUS,
                hitPos.y + EXPLOSION_RADIUS,
                hitPos.z + EXPLOSION_RADIUS
        );

        List<LivingEntity> affectedEntities = attacker.level().getEntitiesOfClass(LivingEntity.class, explosionBox,
                entity -> entity != attacker);

        for (LivingEntity entity : affectedEntities) {
            double distance = entity.position().distanceTo(hitPos);
            float distanceMultiplier = (float) Math.max(NO_OFFSET, DISTANCE_FALLOFF_BASE - (distance / EXPLOSION_RADIUS));
            float finalDamage = damage * distanceMultiplier;

            if (finalDamage > 0) {
                entity.hurt(attacker.level().damageSources().magic(), finalDamage);

                Vec3 knockbackDir = entity.position().subtract(hitPos).normalize();
                double knockbackStrength = KNOCKBACK_HORIZONTAL * distanceMultiplier;
                entity.setDeltaMovement(entity.getDeltaMovement().add(
                        knockbackDir.x * knockbackStrength,
                        KNOCKBACK_VERTICAL * distanceMultiplier,
                        knockbackDir.z * knockbackStrength
                ));
                entity.hurtMarked = true;
            }
        }

        if (attacker.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.GUST_EMITTER_SMALL,
                    hitPos.x, hitPos.y, hitPos.z, PARTICLE_COUNT,
                    NO_OFFSET, NO_OFFSET, NO_OFFSET, NO_OFFSET);

            for (int i = 0; i < CLOUD_PARTICLE_COUNT; i++) {
                double offsetX = (attacker.getRandom().nextDouble() - RANDOM_CENTER) * EXPLOSION_RADIUS * CLOUD_SPREAD_FACTOR;
                double offsetY = (attacker.getRandom().nextDouble() - RANDOM_CENTER) * EXPLOSION_RADIUS;
                double offsetZ = (attacker.getRandom().nextDouble() - RANDOM_CENTER) * EXPLOSION_RADIUS * CLOUD_SPREAD_FACTOR;
                serverLevel.sendParticles(ParticleTypes.CLOUD,
                        hitPos.x + offsetX,
                        hitPos.y + offsetY,
                        hitPos.z + offsetZ,
                        PARTICLE_COUNT, NO_OFFSET, CLOUD_VERTICAL_SPEED, NO_OFFSET, NO_OFFSET);
            }
        }

        attacker.level().playSound(null, hitPos.x, hitPos.y, hitPos.z,
                SoundEvents.WIND_CHARGE_BURST, SoundSource.PLAYERS, SOUND_VOLUME, SOUND_PITCH);
    }
}
