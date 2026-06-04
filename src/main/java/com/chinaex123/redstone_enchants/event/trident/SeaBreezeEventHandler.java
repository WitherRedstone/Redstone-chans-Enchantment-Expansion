package com.chinaex123.redstone_enchants.event.trident;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

/** 坚不可摧：物品不再消耗耐久 **/
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class SeaBreezeEventHandler {
    private static final Identifier SEA_BREEZE_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "sea_breeze");

    private static final float EXPLOSION_RADIUS = 2.0f; // 爆炸半径
    private static final float BASE_DAMAGE = 4.0f; // 基础伤害
    private static final float DAMAGE_PER_LEVEL = 2.0f; // 每级附魔增加的伤害
    private static final float KNOCKBACK_HORIZONTAL = 1.2f; // 水平击退强度
    private static final float KNOCKBACK_VERTICAL = 0.3f; // 垂直击退强度

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        Entity directEntity = event.getSource().getDirectEntity();
        if (!(directEntity instanceof AbstractArrow arrow)) return;

        Entity owner = arrow.getOwner();
        if (!(owner instanceof LivingEntity attacker)) return;

        ItemStack weapon = attacker.getMainHandItem();
        if (weapon.isEmpty() || !weapon.is(Items.TRIDENT)) return;

        Holder.Reference<Enchantment> seaBreezeEnchant = attacker.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(SEA_BREEZE_ID)
                .orElse(null);

        if (seaBreezeEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = weapon.getEnchantments().getLevel(seaBreezeEnchant);
        if (level <= 0) return;

        LivingEntity target = event.getEntity();
        Vec3 explosionPos = target.position().add(0, target.getBbHeight() / 2, 0);

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
            float distanceMultiplier = (float) Math.max(0, 1.0 - (distance / EXPLOSION_RADIUS));
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
                    explosionPos.x, explosionPos.y, explosionPos.z, 1, 0, 0, 0, 0);

            for (int i = 0; i < 20; i++) {
                double offsetX = (attacker.getRandom().nextDouble() - 0.5) * EXPLOSION_RADIUS * 2;
                double offsetY = (attacker.getRandom().nextDouble() - 0.5) * EXPLOSION_RADIUS;
                double offsetZ = (attacker.getRandom().nextDouble() - 0.5) * EXPLOSION_RADIUS * 2;
                serverLevel.sendParticles(ParticleTypes.CLOUD,
                        explosionPos.x + offsetX,
                        explosionPos.y + offsetY,
                        explosionPos.z + offsetZ,
                        1, 0, 0.1, 0, 0);
            }
        }

        attacker.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                SoundEvents.WIND_CHARGE_BURST, SoundSource.PLAYERS, 1.5F, 1.0F);
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof AbstractArrow arrow)) return;

        Entity owner = arrow.getOwner();
        if (!(owner instanceof LivingEntity attacker)) return;

        ItemStack weapon = attacker.getMainHandItem();
        if (weapon.isEmpty() || !weapon.is(Items.TRIDENT)) return;

        Holder.Reference<Enchantment> seaBreezeEnchant = attacker.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(SEA_BREEZE_ID)
                .orElse(null);

        if (seaBreezeEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = weapon.getEnchantments().getLevel(seaBreezeEnchant);
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
            float distanceMultiplier = (float) Math.max(0, 1.0 - (distance / EXPLOSION_RADIUS));
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
                    hitPos.x, hitPos.y, hitPos.z, 1, 0, 0, 0, 0);

            for (int i = 0; i < 20; i++) {
                double offsetX = (attacker.getRandom().nextDouble() - 0.5) * EXPLOSION_RADIUS * 2;
                double offsetY = (attacker.getRandom().nextDouble() - 0.5) * EXPLOSION_RADIUS;
                double offsetZ = (attacker.getRandom().nextDouble() - 0.5) * EXPLOSION_RADIUS * 2;
                serverLevel.sendParticles(ParticleTypes.CLOUD,
                        hitPos.x + offsetX,
                        hitPos.y + offsetY,
                        hitPos.z + offsetZ,
                        1, 0, 0.1, 0, 0);
            }
        }

        attacker.level().playSound(null, hitPos.x, hitPos.y, hitPos.z,
                SoundEvents.WIND_CHARGE_BURST, SoundSource.PLAYERS, 1.5F, 1.0F);
    }
}