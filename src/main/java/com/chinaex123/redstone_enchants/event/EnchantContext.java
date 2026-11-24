package com.chinaex123.redstone_enchants.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.TickTask;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import com.chinaex123.redstone_enchants.init.REEntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * 附魔事件处理器的执行上下文。
 */
public final class EnchantContext {

    /** 非生物实体标签。 */
    public static final TagKey<EntityType<?>> NON_LIVING = REEntityTypeTags.NON_LIVING;
    /** 免疫即死类附魔的实体标签。 */
    public static final TagKey<EntityType<?>> BLACK_ENTITY = REEntityTypeTags.BLACK_ENTITY;

    private final ServerLevel level;
    private final Entity entity;
    private final Vec3 pos;
    private final int enchantLevel;

    public EnchantContext(ServerLevel level, Entity entity, Vec3 pos) {
        this(level, entity, pos, 0);
    }

    public EnchantContext(ServerLevel level, Entity entity, Vec3 pos, int enchantLevel) {
        this.level = level;
        this.entity = entity;
        this.pos = pos;
        this.enchantLevel = enchantLevel;
    }

    public ServerLevel level() {
        return this.level;
    }

    public Entity entity() {
        return this.entity;
    }

    public Vec3 pos() {
        return this.pos;
    }

    public int enchantLevel() {
        return this.enchantLevel;
    }

    public static Holder.Reference<Enchantment> holder(Level level, String path) {
        return level.registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(ResourceLocation.fromNamespaceAndPath("redstone_enchants", path))
                .orElse(null);
    }

    public static int level(LivingEntity entity, String path) {
        Holder.Reference<Enchantment> enchantment = holder(entity.level(), path);
        return enchantment == null ? 0 : EnchantmentHelper.getEnchantmentLevel(enchantment, entity);
    }

    public static int level(Level level, String path, ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return 0;
        }
        Holder.Reference<Enchantment> enchantment = holder(level, path);
        return enchantment == null ? 0 : EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack);
    }

    public void particle(ParticleOptions particle, int count, double dx, double dy, double dz, double speed) {
        this.level.sendParticles(particle, this.pos.x, this.pos.y, this.pos.z, count, dx, dy, dz, speed);
    }

    public void particleAt(Vec3 at, ParticleOptions particle, int count, double dx, double dy, double dz, double speed) {
        this.level.sendParticles(particle, at.x, at.y, at.z, count, dx, dy, dz, speed);
    }

    public void sound(SoundEvent sound, float volume, float pitch) {
        this.level.playSound(null, this.pos.x, this.pos.y, this.pos.z, sound, SoundSource.PLAYERS, volume, pitch);
    }

    public void soundAt(Vec3 at, SoundEvent sound, float volume, float pitch) {
        this.level.playSound(null, at.x, at.y, at.z, sound, SoundSource.PLAYERS, volume, pitch);
    }

    public void soundNeutral(SoundEvent sound, float volume, float pitch) {
        this.level.playSound(null, this.pos.x, this.pos.y, this.pos.z, sound, SoundSource.NEUTRAL, volume, pitch);
    }

    public void soundHostile(SoundEvent sound, float volume, float pitch) {
        this.level.playSound(null, this.pos.x, this.pos.y, this.pos.z, sound, SoundSource.HOSTILE, volume, pitch);
    }

    public void soundMaster(SoundEvent sound, float volume, float pitch) {
        this.level.playSound(null, this.pos.x, this.pos.y, this.pos.z, sound, SoundSource.MASTER, volume, pitch);
    }

    public void effect(Holder<MobEffect> effect, float seconds, int amplifier) {
        this.applyEffect(this.entity, effect, (int) (seconds * 20.0F), amplifier);
    }

    public void effectInfinite(Holder<MobEffect> effect, int amplifier) {
        this.applyEffect(this.entity, effect, -1, amplifier);
    }

    public void applyEffect(Entity target, Holder<MobEffect> effect, int durationTicks, int amplifier) {
        if (target instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(effect, durationTicks, amplifier, true, false, true));
        }
    }

    public Entity summon(String snbt, double dx, double dy, double dz) {
        CompoundTag tag = parse(snbt);
        Entity spawned = EntityType.loadEntityRecursive(tag, this.level, loaded -> loaded);
        if (spawned == null) {
            return null;
        }
        spawned.setPos(this.pos.x + dx, this.pos.y + dy, this.pos.z + dz);
        this.level.addFreshEntity(spawned);
        return spawned;
    }

    public Entity summon(String snbt) {
        return this.summon(snbt, 0.0D, 0.0D, 0.0D);
    }

    public void mergeNbt(Entity target, String snbt) {
        if (target != null) {
            target.load(parse(snbt));
        }
    }

    public static List<Entity> allWithTag(ServerLevel level, String tag) {
        List<Entity> result = new ArrayList<>();
        for (Entity entity : level.getAllEntities()) {
            if (entity.getTags().contains(tag)) {
                result.add(entity);
            }
        }
        return result;
    }

    public static CompoundTag parse(String snbt) {
        try {
            return TagParser.parseTag(snbt);
        } catch (Exception exception) {
            throw new IllegalStateException("无法解析 SNBT: " + snbt, exception);
        }
    }

    public void tagAdd(Entity target, String tag) {
        if (target != null) {
            target.addTag(tag);
        }
    }

    public void tagRemove(Entity target, String tag) {
        if (target != null) {
            target.removeTag(tag);
        }
    }

    public void kill(Entity target) {
        if (target != null) {
            target.kill();
        }
    }

    public void discard(Entity target) {
        if (target != null) {
            target.discard();
        }
    }

    public void motion(Entity target, Vec3 motion) {
        if (target != null) {
            target.setDeltaMovement(motion);
            target.hurtMarked = true;
        }
    }

    public void give(ItemStack stack) {
        if (this.entity instanceof Player player) {
            player.getInventory().add(stack);
        }
    }

    public boolean isNonLiving(Entity target) {
        return target != null && target.getType().is(NON_LIVING);
    }

    public Entity nearestEntity(double minDistance, double maxDistance, boolean includeSelf, Predicate<Entity> filter) {
        List<Entity> candidates = this.entities(maxDistance, Integer.MAX_VALUE, includeSelf, filter);
        double minSqr = minDistance * minDistance;
        for (Entity candidate : candidates) {
            if (candidate.position().distanceToSqr(this.pos) >= minSqr) {
                return candidate;
            }
        }
        return null;
    }

    public List<Entity> entities(double radius, int limit, boolean includeSelf, Predicate<Entity> filter) {
        AABB box = new AABB(this.pos, this.pos).inflate(radius);
        List<Entity> found = new ArrayList<>(this.level.getEntities(this.entity, box,
                candidate -> (includeSelf || candidate != this.entity) && candidate.isAlive() && filter.test(candidate)));
        found.sort((left, right) -> Double.compare(left.position().distanceToSqr(this.pos), right.position().distanceToSqr(this.pos)));
        return found.size() > limit ? found.subList(0, limit) : found;
    }

    public EnchantContext at(double dx, double dy, double dz) {
        return new EnchantContext(this.level, this.entity, this.pos.add(dx, dy, dz), this.enchantLevel);
    }

    public EnchantContext forEntity(Entity target) {
        return new EnchantContext(this.level, target, target.position(), this.enchantLevel);
    }

    public int foodLevel() {
        return this.entity instanceof Player player ? player.getFoodData().getFoodLevel() : 20;
    }

    public void clearMainHand() {
        if (this.entity instanceof LivingEntity living) {
            living.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
    }

    public void clearInventory() {
        if (this.entity instanceof Player player) {
            player.getInventory().clearContent();
        }
    }

    public void giveExperience(int amount) {
        if (this.entity instanceof Player player) {
            player.giveExperiencePoints(amount);
        }
    }

    public void teleport(Entity target, Vec3 destination) {
        if (target != null) {
            target.teleportTo(destination.x, destination.y, destination.z);
            target.setDeltaMovement(Vec3.ZERO);
            target.hurtMarked = true;
        }
    }

    public void thunder() {
        this.level.setWeatherParameters(0, 6000, true, true);
    }

    public void schedule(int delayTicks, Runnable task) {
        int target = this.level.getServer().getTickCount() + Math.max(0, delayTicks);
        this.level.getServer().tell(new TickTask(target, task));
    }
}
