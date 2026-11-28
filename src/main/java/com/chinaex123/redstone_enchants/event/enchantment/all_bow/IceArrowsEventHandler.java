package com.chinaex123.redstone_enchants.event.enchantment.all_bow;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 冰霜箭：命中区域产生冻结效果，有水则冻结成冰。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class IceArrowsEventHandler {

    private static final String ID = "ice_arrows";
    private static final ResourceLocation FREEZE_ID = RedstoneEnchants.id("ice_arrows");

    /** 冻缓持续时间（tick）。 */
    private static final int FREEZE_TICKS = 100;
    /** 冻结范围半径（格，按方块计） */
    private static final int FREEZE_RADIUS = 1;
    /** 雪花粒子数量 */
    private static final int PARTICLE_COUNT = 50;
    /** 粒子扩散范围（格） */
    private static final double PARTICLE_SPREAD = 1.0D;
    /** 粒子速度 */
    private static final double PARTICLE_SPEED = 0.5D;
    /** 冻结时属性修饰符的数值 */
    private static final double FREEZE_ATTRIBUTE_AMOUNT = -20.0D;

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (event.getProjectile().level().isClientSide()) return;
        if (!(event.getProjectile() instanceof AbstractArrow arrow)) return;
        if (!(event.getRayTraceResult() instanceof BlockHitResult hit)) return;
        if (!(arrow.level() instanceof ServerLevel level)) return;
        int enchantLevel = EnchantContext.level(level, ID, arrow.getWeaponItem());
        if (enchantLevel <= 0) return;

        Vec3 hitPos = hit.getLocation();
        freezeWater(level, hitPos);
        level.sendParticles(ParticleTypes.SNOWFLAKE, hitPos.x, hitPos.y, hitPos.z,
                PARTICLE_COUNT, PARTICLE_SPREAD, PARTICLE_SPREAD, PARTICLE_SPREAD, PARTICLE_SPEED);
        arrow.discard();
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)) return;
        if (!(arrow.level() instanceof ServerLevel level)) return;
        int enchantLevel = EnchantContext.level(level, ID, arrow.getWeaponItem());
        if (enchantLevel <= 0) return;
        LivingEntity victim = event.getEntity();

        freezeWater(level, victim.position());
        applyFreeze(level, victim);
        level.sendParticles(ParticleTypes.SNOWFLAKE, victim.getX(), victim.getY(), victim.getZ(),
                PARTICLE_COUNT, PARTICLE_SPREAD, PARTICLE_SPREAD, PARTICLE_SPREAD, PARTICLE_SPEED);
        arrow.discard();
    }

    private static void applyFreeze(ServerLevel level, LivingEntity victim) {
        addModifier(victim, Attributes.MOVEMENT_SPEED);
        addModifier(victim, Attributes.ATTACK_SPEED);
        addModifier(victim, Attributes.FLYING_SPEED);
        addModifier(victim, Attributes.JUMP_STRENGTH);
        new EnchantContext(level, victim, victim.position()).schedule(FREEZE_TICKS, () -> {
            removeModifier(victim, Attributes.MOVEMENT_SPEED);
            removeModifier(victim, Attributes.ATTACK_SPEED);
            removeModifier(victim, Attributes.FLYING_SPEED);
            removeModifier(victim, Attributes.JUMP_STRENGTH);
        });
    }

    private static void addModifier(LivingEntity living, Holder<Attribute> attribute) {
        AttributeInstance instance = living.getAttribute(attribute);
        if (instance == null) return;
        instance.removeModifier(FREEZE_ID);
        instance.addTransientModifier(new AttributeModifier(FREEZE_ID, FREEZE_ATTRIBUTE_AMOUNT, AttributeModifier.Operation.ADD_VALUE));
    }

    private static void removeModifier(LivingEntity living, Holder<Attribute> attribute) {
        AttributeInstance instance = living.getAttribute(attribute);
        if (instance != null) {
            instance.removeModifier(FREEZE_ID);
        }
    }

    private static void freezeWater(ServerLevel level, Vec3 center) {
        BlockPos origin = BlockPos.containing(center);
        for (int dx = -FREEZE_RADIUS; dx <= FREEZE_RADIUS; dx++) {
            for (int dy = -FREEZE_RADIUS; dy <= FREEZE_RADIUS; dy++) {
                for (int dz = -FREEZE_RADIUS; dz <= FREEZE_RADIUS; dz++) {
                    BlockPos pos = origin.offset(dx, dy, dz);
                    if (level.getBlockState(pos).is(Blocks.WATER)) {
                        level.setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
                    }
                }
            }
        }
    }
}
