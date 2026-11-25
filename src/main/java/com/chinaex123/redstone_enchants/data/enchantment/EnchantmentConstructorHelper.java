package com.chinaex123.redstone_enchants.data.enchantment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.advancements.critereon.MovementPredicate;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.advancements.critereon.PlayerPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.advancements.critereon.TagPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.AllOf;
import net.minecraft.world.item.enchantment.effects.ApplyMobEffect;
import net.minecraft.world.item.enchantment.effects.DamageEntity;
import net.minecraft.world.item.enchantment.effects.DamageImmunity;
import net.minecraft.world.item.enchantment.effects.DamageItem;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentLocationBasedEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.item.enchantment.effects.ExplodeEffect;
import net.minecraft.world.item.enchantment.effects.Ignite;
import net.minecraft.world.item.enchantment.effects.MultiplyValue;
import net.minecraft.world.item.enchantment.effects.PlaySoundEffect;
import net.minecraft.world.item.enchantment.effects.RemoveBinomial;
import net.minecraft.world.item.enchantment.effects.ReplaceBlock;
import net.minecraft.world.item.enchantment.effects.ReplaceDisk;
import net.minecraft.world.item.enchantment.effects.SetValue;
import net.minecraft.world.item.enchantment.effects.SpawnParticlesEffect;
import net.minecraft.world.item.enchantment.effects.SummonEntityEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.minecraft.world.level.storage.loot.predicates.EnchantmentActiveCheck;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.TimeCheck;
import net.minecraft.world.level.storage.loot.predicates.ValueCheckCondition;
import net.minecraft.world.level.storage.loot.predicates.WeatherCheck;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.EnchantmentLevelProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.Vec3i;
import net.minecraft.sounds.SoundEvent;

/**
 * 附魔数据的构造辅助类。
 * <p>
 * 所有附魔都通过这里的方法构造。
 */
public final class EnchantmentConstructorHelper {
    public static final String MOD_ID = "redstone_enchants";

    private final BootstrapContext<Enchantment> context;

    public EnchantmentConstructorHelper(BootstrapContext<Enchantment> context) {
        this.context = context;
    }

    public static ResourceLocation rl(String id) {
        return id.indexOf(':') >= 0 ? ResourceLocation.parse(id) : ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
    }

    public static ResourceKey<Enchantment> key(String path) {
        return ResourceKey.create(Registries.ENCHANTMENT, rl(path));
    }

    public static TagKey<Item> itemTag(String tag) {
        return TagKey.create(Registries.ITEM, ResourceLocation.parse(stripHash(tag)));
    }

    public static TagKey<Block> blockTag(String tag) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.parse(stripHash(tag)));
    }

    public static TagKey<Fluid> fluidTag(String tag) {
        return TagKey.create(Registries.FLUID, ResourceLocation.parse(stripHash(tag)));
    }

    public static TagKey<EntityType<?>> entityTypeTag(String tag) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse(stripHash(tag)));
    }

    public static TagKey<Enchantment> enchantmentTag(String tag) {
        return TagKey.create(Registries.ENCHANTMENT, ResourceLocation.parse(stripHash(tag)));
    }

    private static String stripHash(String s) {
        return s.startsWith("#") ? s.substring(1) : s;
    }

    private <T> HolderGetter<T> lookup(ResourceKey<? extends Registry<? extends T>> registry) {
        return this.context.lookup(registry);
    }

    private static final HolderOwner<?> VIRTUAL_OWNER = new HolderOwner<Object>() {
        @Override
        public boolean canSerializeIn(HolderOwner<Object> owner) {
            return true;
        }
    };

    @SuppressWarnings("unchecked")
    private static <T> HolderOwner<T> virtualOwner() {
        return (HolderOwner<T>) VIRTUAL_OWNER;
    }

    private <T> Holder<T> holder(ResourceKey<? extends Registry<? extends T>> registry, ResourceKey<T> key) {
        Optional<Holder.Reference<T>> reference = this.lookup(registry).get(key);
        return reference.<Holder<T>>map(h -> h).orElseGet(() -> Holder.Reference.createStandAlone(virtualOwner(), key));
    }

    public HolderSet<Item> items(String tagOrId) {
        return tagOrId.startsWith("#") ? this.lookup(Registries.ITEM).getOrThrow(itemTag(tagOrId)) : HolderSet.direct(this.item(tagOrId));
    }

    public HolderSet<Item> items(TagKey<Item> tag) {
        return this.items("#" + tag.location());
    }

    public Holder<Item> item(String id) {
        return this.lookup(Registries.ITEM).getOrThrow(ResourceKey.create(Registries.ITEM, ResourceLocation.parse(id)));
    }

    public HolderSet<Enchantment> enchantments(String... tagsOrIds) {
        if (tagsOrIds.length == 1 && tagsOrIds[0].startsWith("#")) {
            return this.lookup(Registries.ENCHANTMENT).getOrThrow(enchantmentTag(tagsOrIds[0]));
        }
        List<Holder<Enchantment>> holders = new ArrayList<>();
        for (String s : tagsOrIds) {
            holders.add(this.lookup(Registries.ENCHANTMENT).getOrThrow(key(s)));
        }
        return HolderSet.direct(holders);
    }

    public HolderSet<Enchantment> enchantments(TagKey<Enchantment> tag) {
        return this.enchantments("#" + tag.location());
    }

    public Holder<MobEffect> mobEffect(String id) {
        return this.holder(Registries.MOB_EFFECT, ResourceKey.create(Registries.MOB_EFFECT, ResourceLocation.parse(id)));
    }

    public Holder<MobEffect> mobEffect(Holder<MobEffect> effect) {
        return effect;
    }

    public HolderSet<MobEffect> mobEffects(String tagOrId) {
        if (tagOrId.startsWith("#")) {
            return this.lookup(Registries.MOB_EFFECT).getOrThrow(TagKey.create(Registries.MOB_EFFECT, ResourceLocation.parse(stripHash(tagOrId))));
        }
        List<Holder<MobEffect>> holders = new ArrayList<>();
        for (String s : tagOrId.split(",")) {
            holders.add(this.mobEffect(s.trim()));
        }
        return HolderSet.direct(holders);
    }

    @SafeVarargs
    public final HolderSet<MobEffect> mobEffects(Holder<MobEffect>... effects) {
        List<Holder<MobEffect>> holders = new ArrayList<>();
        for (Holder<MobEffect> effect : effects) {
            holders.add(this.mobEffect(effect));
        }
        return HolderSet.direct(holders);
    }

    public Holder<Attribute> attribute(String id) {
        return this.holder(Registries.ATTRIBUTE, ResourceKey.create(Registries.ATTRIBUTE, ResourceLocation.parse(id)));
    }

    public Holder<DamageType> damageType(String id) {
        return this.lookup(Registries.DAMAGE_TYPE).getOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse(id)));
    }

    public Holder<SoundEvent> sound(String id) {
        return this.lookup(Registries.SOUND_EVENT).getOrThrow(ResourceKey.create(Registries.SOUND_EVENT, ResourceLocation.parse(id)));
    }

    public Holder<SoundEvent> fixedRangeSound(String id, float range) {
        return Holder.direct(SoundEvent.createFixedRangeEvent(ResourceLocation.parse(id), range));
    }

    public Holder<GameEvent> gameEvent(String id) {
        return this.lookup(Registries.GAME_EVENT).getOrThrow(ResourceKey.create(Registries.GAME_EVENT, ResourceLocation.parse(id)));
    }

    public Holder<Block> block(String id) {
        return this.lookup(Registries.BLOCK).getOrThrow(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse(id)));
    }

    public HolderSet<Block> blocks(String tagOrId) {
        if (tagOrId.startsWith("#")) {
            return this.lookup(Registries.BLOCK).getOrThrow(blockTag(tagOrId));
        }
        return HolderSet.direct(this.block(tagOrId));
    }

    public HolderSet<Block> blocks(TagKey<Block> tag) {
        return this.blocks("#" + tag.location());
    }

    public HolderSet<Fluid> fluids(String tagOrId) {
        if (tagOrId.startsWith("#")) {
            return this.lookup(Registries.FLUID).getOrThrow(fluidTag(tagOrId));
        }
        return HolderSet.direct(this.lookup(Registries.FLUID).getOrThrow(ResourceKey.create(Registries.FLUID, ResourceLocation.parse(tagOrId))));
    }

    public HolderSet<Fluid> fluids(TagKey<Fluid> tag) {
        return this.fluids("#" + tag.location());
    }

    public Holder<EntityType<?>> entityType(String id) {
        return this.lookup(Registries.ENTITY_TYPE).getOrThrow(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse(id)));
    }

    public HolderSet<EntityType<?>> entityTypes(String tagOrId) {
        if (tagOrId.startsWith("#")) {
            return this.lookup(Registries.ENTITY_TYPE).getOrThrow(entityTypeTag(tagOrId));
        }
        return HolderSet.direct(this.entityType(tagOrId));
    }

    public HolderSet<EntityType<?>> entityTypes(TagKey<EntityType<?>> tag) {
        return this.entityTypes("#" + tag.location());
    }

    public static Enchantment.Cost constantCost(int value) {
        return Enchantment.constantCost(value);
    }

    public static Enchantment.Cost dynamicCost(int base, int perLevel) {
        return Enchantment.dynamicCost(base, perLevel);
    }

    public static EquipmentSlotGroup slot(String name) {
        return switch (name) {
            case "any" -> EquipmentSlotGroup.ANY;
            case "mainhand" -> EquipmentSlotGroup.MAINHAND;
            case "offhand" -> EquipmentSlotGroup.OFFHAND;
            case "hand" -> EquipmentSlotGroup.HAND;
            case "armor" -> EquipmentSlotGroup.ARMOR;
            case "head" -> EquipmentSlotGroup.HEAD;
            case "chest" -> EquipmentSlotGroup.CHEST;
            case "legs" -> EquipmentSlotGroup.LEGS;
            case "feet" -> EquipmentSlotGroup.FEET;
            case "body" -> EquipmentSlotGroup.BODY;
            default -> throw new IllegalArgumentException("Unknown slot: " + name);
        };
    }

    public Enchantment.Builder builder(String supported, String primary, int weight, int maxLevel,
                                       Enchantment.Cost minCost, Enchantment.Cost maxCost, int anvilCost, String... slots) {
        EquipmentSlotGroup[] groups = new EquipmentSlotGroup[slots.length];
        for (int i = 0; i < slots.length; i++) {
            groups[i] = slot(slots[i]);
        }
        return Enchantment.enchantment(Enchantment.definition(this.items(supported), this.items(primary), weight, maxLevel,
                minCost, maxCost, anvilCost, groups));
    }

    public Enchantment.Builder builder(TagKey<Item> supported, TagKey<Item> primary, int weight, int maxLevel,
                                       Enchantment.Cost minCost, Enchantment.Cost maxCost, int anvilCost, String... slots) {
        return this.builder("#" + supported.location(), "#" + primary.location(), weight, maxLevel,
                minCost, maxCost, anvilCost, slots);
    }

    public void register(Enchantment.Builder builder, ResourceKey<Enchantment> key, int color) {
        this.register(builder, key, color, key.location().getPath());
    }

    public void register(Enchantment.Builder builder, ResourceKey<Enchantment> key, int color, String namePath) {
        Enchantment built = builder.build(key.location());
        ResourceLocation nameId = ResourceLocation.fromNamespaceAndPath(key.location().getNamespace(), namePath);
        MutableComponent description = Component.translatable(Util.makeDescriptionId("enchantment", nameId))
                .withStyle(style -> style.withColor(TextColor.fromRgb(color)));
        this.context.register(key, new Enchantment(description, built.definition(), built.exclusiveSet(), built.effects()));
    }

    public static LevelBasedValue constant(float value) {
        return new LevelBasedValue.Constant(value);
    }

    public static LevelBasedValue linear(float base, float perLevel) {
        return new LevelBasedValue.Linear(base, perLevel);
    }

    public static LevelBasedValue clamped(LevelBasedValue value, float min, float max) {
        return new LevelBasedValue.Clamped(value, min, max);
    }

    public static LevelBasedValue lookup(float[] values, LevelBasedValue fallback) {
        List<Float> list = new ArrayList<>(values.length);
        for (float v : values) {
            list.add(v);
        }
        return new LevelBasedValue.Lookup(list, fallback);
    }

    public static LevelBasedValue fraction(LevelBasedValue numerator, LevelBasedValue denominator) {
        return new LevelBasedValue.Fraction(numerator, denominator);
    }

    public static AddValue add(LevelBasedValue value) {
        return new AddValue(value);
    }

    public static MultiplyValue multiply(LevelBasedValue factor) {
        return new MultiplyValue(factor);
    }

    public static SetValue set(LevelBasedValue value) {
        return new SetValue(value);
    }

    public static RemoveBinomial removeBinomial(LevelBasedValue chance) {
        return new RemoveBinomial(chance);
    }

    public static AllOf.ValueEffects allValues(EnchantmentValueEffect... effects) {
        return new AllOf.ValueEffects(List.of(effects));
    }

    public static ApplyMobEffect mobEffect(HolderSet<MobEffect> effects, LevelBasedValue duration, LevelBasedValue amplifier) {
        return new ApplyMobEffect(effects, duration, duration, amplifier, amplifier);
    }

    public static ApplyMobEffect mobEffect(HolderSet<MobEffect> effects, LevelBasedValue minDuration, LevelBasedValue maxDuration,
                                           LevelBasedValue minAmplifier, LevelBasedValue maxAmplifier) {
        return new ApplyMobEffect(effects, minDuration, maxDuration, minAmplifier, maxAmplifier);
    }

    public static Ignite ignite(LevelBasedValue duration) {
        return new Ignite(duration);
    }

    public static DamageItem damageItem(LevelBasedValue amount) {
        return new DamageItem(amount);
    }

    public static DamageEntity damageEntity(LevelBasedValue min, LevelBasedValue max, Holder<net.minecraft.world.damagesource.DamageType> type) {
        return new DamageEntity(min, max, type);
    }

    public static ExplodeEffect explode(boolean attributeToUser, Holder<net.minecraft.world.damagesource.DamageType> damageType,
                                        LevelBasedValue knockbackMultiplier, HolderSet<Block> immuneBlocks, Vec3 offset, LevelBasedValue radius,
                                        boolean createFire, Level.ExplosionInteraction interaction, ParticleOptions smallParticle, ParticleOptions largeParticle,
                                        Holder<SoundEvent> sound) {
        return new ExplodeEffect(attributeToUser, Optional.ofNullable(damageType), Optional.ofNullable(knockbackMultiplier),
                Optional.ofNullable(immuneBlocks), offset, radius, createFire, interaction, smallParticle, largeParticle, sound);
    }

    public static ReplaceBlock replaceBlock(Vec3i offset, BlockPredicate predicate, BlockStateProvider state, Holder<GameEvent> gameEvent) {
        return new ReplaceBlock(offset, Optional.ofNullable(predicate), state, Optional.ofNullable(gameEvent));
    }

    public static ReplaceDisk replaceDisk(LevelBasedValue radius, LevelBasedValue height, Vec3i offset, BlockPredicate predicate,
                                          BlockStateProvider state, Holder<GameEvent> gameEvent) {
        return new ReplaceDisk(radius, height, offset, Optional.ofNullable(predicate), state, Optional.ofNullable(gameEvent));
    }

    public static PlaySoundEffect playSound(Holder<SoundEvent> sound, FloatProvider volume, FloatProvider pitch) {
        return new PlaySoundEffect(sound, volume, pitch);
    }

    public static SpawnParticlesEffect particles(ParticleOptions particle, SpawnParticlesEffect.PositionSource horizontalPosition,
                                                 SpawnParticlesEffect.PositionSource verticalPosition, SpawnParticlesEffect.VelocitySource horizontalVelocity,
                                                 SpawnParticlesEffect.VelocitySource verticalVelocity, FloatProvider speed) {
        return new SpawnParticlesEffect(particle, horizontalPosition, verticalPosition, horizontalVelocity, verticalVelocity, speed);
    }

    public static SummonEntityEffect summon(HolderSet<EntityType<?>> types, boolean joinTeam) {
        return new SummonEntityEffect(types, joinTeam);
    }

    public static AllOf.EntityEffects allEntities(EnchantmentEntityEffect... effects) {
        return new AllOf.EntityEffects(List.of(effects));
    }

    public static ParticleOptions particle(String id) {
        return switch (id) {
            case "minecraft:explosion_emitter" -> ParticleTypes.EXPLOSION_EMITTER;
            case "minecraft:explosion" -> ParticleTypes.EXPLOSION;
            case "minecraft:gust_emitter_large" -> ParticleTypes.GUST_EMITTER_LARGE;
            case "minecraft:gust_emitter_small" -> ParticleTypes.GUST_EMITTER_SMALL;
            case "minecraft:snowflake" -> ParticleTypes.SNOWFLAKE;
            case "minecraft:ash" -> ParticleTypes.ASH;
            case "minecraft:soul" -> ParticleTypes.SOUL;
            case "minecraft:poof" -> ParticleTypes.POOF;
            case "minecraft:electric_spark" -> ParticleTypes.ELECTRIC_SPARK;
            default -> throw new IllegalArgumentException("Unknown particle: " + id);
        };
    }

    public static DustParticleOptions dust(float red, float green, float blue, float scale) {
        return new DustParticleOptions(new org.joml.Vector3f(red, green, blue), scale);
    }

    public static SpawnParticlesEffect.PositionSource position(String type, float offset, float scale) {
        return new SpawnParticlesEffect.PositionSource(
                "in_bounding_box".equals(type) ? SpawnParticlesEffect.PositionSourceType.BOUNDING_BOX
                        : SpawnParticlesEffect.PositionSourceType.ENTITY_POSITION,
                offset, scale);
    }

    public static SpawnParticlesEffect.VelocitySource velocity(float movementScale, FloatProvider base) {
        return new SpawnParticlesEffect.VelocitySource(movementScale, base);
    }

    public static FloatProvider constantFloat(float value) {
        return ConstantFloat.of(value);
    }

    public static FloatProvider uniformFloat(float minInclusive, float maxExclusive) {
        return UniformFloat.of(minInclusive, maxExclusive);
    }

    public static Vec3 vec(double x, double y, double z) {
        return new Vec3(x, y, z);
    }

    public static Vec3i vec3i(int x, int y, int z) {
        return new Vec3i(x, y, z);
    }

    public static BlockStateProvider simpleState(BlockState state) {
        return BlockStateProvider.simple(state);
    }

    public static BlockStateProvider weightedState(SimpleWeightedRandomList.Builder<BlockState> builder) {
        return new WeightedStateProvider(builder);
    }

    public static SimpleWeightedRandomList.Builder<BlockState> weightedStates() {
        return SimpleWeightedRandomList.builder();
    }

    public static BlockState state(String id) {
        ResourceLocation location = ResourceLocation.parse(id);
        Block block = net.minecraft.core.registries.BuiltInRegistries.BLOCK.get(location);
        return block.defaultBlockState();
    }

    public void postAttack(Enchantment.Builder builder, EnchantmentTarget enchanted, EnchantmentTarget affected,
                           EnchantmentEntityEffect effect, LootItemCondition.Builder requirements) {
        if (requirements == null) {
            builder.withEffect(EnchantmentEffectComponents.POST_ATTACK, enchanted, affected, effect);
        } else {
            builder.withEffect(EnchantmentEffectComponents.POST_ATTACK, enchanted, affected, effect, requirements);
        }
    }

    public void value(Enchantment.Builder builder, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>> component,
                      EnchantmentValueEffect effect, LootItemCondition.Builder requirements) {
        if (requirements == null) {
            builder.withEffect(component, effect);
        } else {
            builder.withEffect(component, effect, requirements);
        }
    }

    public void entity(Enchantment.Builder builder, DataComponentType<List<ConditionalEffect<EnchantmentEntityEffect>>> component,
                       EnchantmentEntityEffect effect, LootItemCondition.Builder requirements) {
        if (requirements == null) {
            builder.withEffect(component, effect);
        } else {
            builder.withEffect(component, effect, requirements);
        }
    }

    public void location(Enchantment.Builder builder,
                         DataComponentType<List<ConditionalEffect<EnchantmentLocationBasedEffect>>> component,
                         EnchantmentLocationBasedEffect effect, LootItemCondition.Builder requirements) {
        if (requirements == null) {
            builder.withEffect(component, effect);
        } else {
            builder.withEffect(component, effect, requirements);
        }
    }

    public void immunity(Enchantment.Builder builder, LootItemCondition.Builder requirements) {
        DamageImmunity effect = new DamageImmunity();
        if (requirements == null) {
            builder.withEffect(EnchantmentEffectComponents.DAMAGE_IMMUNITY, effect);
        } else {
            builder.withEffect(EnchantmentEffectComponents.DAMAGE_IMMUNITY, effect, requirements);
        }
    }

    public void attributes(Enchantment.Builder builder, EnchantmentAttributeEffect... effects) {
        for (EnchantmentAttributeEffect effect : effects) {
            builder.withEffect(EnchantmentEffectComponents.ATTRIBUTES, effect);
        }
    }

    public static EnchantmentAttributeEffect attributeEffect(String id, Holder<Attribute> attribute, AttributeModifier.Operation operation,
                                                             LevelBasedValue amount) {
        return new EnchantmentAttributeEffect(rl(id), attribute, amount, operation);
    }

    public static AttributeModifier.Operation operation(String name) {
        return switch (name) {
            case "add_value" -> AttributeModifier.Operation.ADD_VALUE;
            case "add_multiplied_base" -> AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
            case "add_multiplied_total" -> AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL;
            default -> throw new IllegalArgumentException("Unknown operation: " + name);
        };
    }

    public void special(Enchantment.Builder builder, DataComponentType<EnchantmentValueEffect> component, EnchantmentValueEffect effect) {
        builder.withSpecialEffect(component, effect);
    }

    public void unit(Enchantment.Builder builder, DataComponentType<net.minecraft.util.Unit> component) {
        builder.withEffect(component);
    }

    public static EnchantmentTarget target(String name) {
        return switch (name) {
            case "attacker" -> EnchantmentTarget.ATTACKER;
            case "victim" -> EnchantmentTarget.VICTIM;
            case "damaging_entity" -> EnchantmentTarget.DAMAGING_ENTITY;
            default -> throw new IllegalArgumentException("Unknown target: " + name);
        };
    }

    public static LootItemCondition.Builder req(LootItemCondition.Builder... conditions) {
        if (conditions.length == 0) {
            return null;
        }
        if (conditions.length == 1) {
            return conditions[0];
        }
        AllOfCondition.Builder builder = AllOfCondition.allOf(conditions[0]);
        for (int i = 1; i < conditions.length; i++) {
            builder.addTerm(conditions[i]);
        }
        return builder;
    }

    public static LootItemCondition.Builder allOf(LootItemCondition.Builder... conditions) {
        return req(conditions);
    }

    public static LootItemCondition.Builder anyOf(LootItemCondition.Builder... conditions) {
        AnyOfCondition.Builder builder = AnyOfCondition.anyOf(conditions[0]);
        for (int i = 1; i < conditions.length; i++) {
            builder.addTerm(conditions[i]);
        }
        return builder;
    }

    public static LootItemCondition.Builder inverted(LootItemCondition.Builder condition) {
        return InvertedLootItemCondition.invert(condition);
    }

    public static LootItemCondition.Builder randomChance(float chance) {
        return LootItemRandomChanceCondition.randomChance(chance);
    }

    public static LootItemCondition.Builder randomChance(NumberProvider chance) {
        return LootItemRandomChanceCondition.randomChance(chance);
    }

    public static LootItemCondition.Builder valueCheck(NumberProvider provider, IntRange range) {
        return ValueCheckCondition.hasValue(provider, range);
    }

    public static LootItemCondition.Builder valueCheck(NumberProvider provider, int exact) {
        return ValueCheckCondition.hasValue(provider, IntRange.exact(exact));
    }

    public static LootItemCondition.Builder valueCheck(NumberProvider provider, int min, int max) {
        return ValueCheckCondition.hasValue(provider, IntRange.range(min, max));
    }

    public static NumberProvider constantNumber(float value) {
        return new ConstantValue(value);
    }

    public static NumberProvider levelNumber(LevelBasedValue amount) {
        return new EnchantmentLevelProvider(amount);
    }

    public static LootItemCondition.Builder entityProperties(String target, EntityPredicate.Builder predicate) {
        return LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.getByName(target), predicate);
    }

    public static LootItemCondition.Builder entityProperties(String target, EntityPredicate predicate) {
        return LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.getByName(target), predicate);
    }

    public static LootItemCondition.Builder damageSource(DamageSourcePredicate.Builder predicate) {
        return DamageSourceCondition.hasDamageSource(predicate);
    }

    public static LootItemCondition.Builder locationCheck(LocationPredicate.Builder predicate) {
        return LocationCheck.checkLocation(predicate);
    }

    public static LootItemCondition.Builder enchantmentActive(boolean active) {
        return () -> new EnchantmentActiveCheck(active);
    }

    public static LootItemCondition.Builder timeCheck(long period, int min, int max) {
        return TimeCheck.time(IntRange.range(min, max)).setPeriod(period);
    }

    public static LootItemCondition.Builder blockStateProperty(Block block, StatePropertiesPredicate.Builder properties) {
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(properties);
    }

    public static LootItemCondition.Builder blockStateProperty(Block block) {
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block);
    }

    public static LootItemCondition.Builder weatherCheck(Boolean raining, Boolean thundering) {
        WeatherCheck.Builder builder = WeatherCheck.weather();
        if (raining != null) {
            builder = builder.setRaining(raining);
        }
        if (thundering != null) {
            builder = builder.setThundering(thundering);
        }
        return builder;
    }

    public static EntityPredicate.Builder entity() {
        return EntityPredicate.Builder.entity();
    }

    public static EntityFlagsPredicate.Builder flags() {
        return EntityFlagsPredicate.Builder.flags();
    }

    public BlockPredicate blockPredicate(String tagOrId, int x, int y, int z) {
        Vec3i offset = new Vec3i(x, y, z);
        if (tagOrId.startsWith("#")) {
            return BlockPredicate.matchesTag(offset, blockTag(tagOrId));
        }
        return BlockPredicate.matchesBlocks(offset,
                net.minecraft.core.registries.BuiltInRegistries.BLOCK.get(ResourceLocation.parse(tagOrId)));
    }

    public BlockPredicate blockPredicate(TagKey<Block> tag, int x, int y, int z) {
        return this.blockPredicate("#" + tag.location(), x, y, z);
    }

    public BlockPredicate fluidPredicate(String tagOrId, int x, int y, int z) {
        Vec3i offset = new Vec3i(x, y, z);
        java.util.List<Fluid> list = new ArrayList<>();
        for (Holder<Fluid> holder : this.fluids(tagOrId)) {
            list.add(holder.value());
        }
        return BlockPredicate.matchesFluids(offset, list);
    }

    public static BlockPredicate unobstructed(int x, int y, int z) {
        return BlockPredicate.unobstructed(new Vec3i(x, y, z));
    }

    public static BlockPredicate allBlockPredicates(BlockPredicate... predicates) {
        return BlockPredicate.allOf(java.util.List.of(predicates));
    }

    public static BlockPredicate anyBlockPredicates(BlockPredicate... predicates) {
        return BlockPredicate.anyOf(java.util.List.of(predicates));
    }

    public static BlockPredicate notBlockPredicate(BlockPredicate predicate) {
        return BlockPredicate.not(predicate);
    }

    public net.minecraft.advancements.critereon.BlockPredicate locBlock(String tagOrId) {
        return new net.minecraft.advancements.critereon.BlockPredicate(Optional.of(this.blocks(tagOrId)),
                Optional.empty(), Optional.empty());
    }

    public net.minecraft.advancements.critereon.BlockPredicate locBlock(TagKey<Block> tag) {
        return this.locBlock("#" + tag.location());
    }

    public EntityPredicate.Builder entityOf(String tagOrId) {
        return tagOrId.startsWith("#") ? EntityPredicate.Builder.entity().of(entityTypeTag(tagOrId))
                : EntityPredicate.Builder.entity().of(this.entityType(tagOrId).value());
    }

    public EntityPredicate.Builder entityOf(TagKey<EntityType<?>> tag) {
        return this.entityOf("#" + tag.location());
    }

    public static MobEffectsPredicate.Builder effects() {
        return MobEffectsPredicate.Builder.effects();
    }

    public static EntityEquipmentPredicate.Builder equipment() {
        return EntityEquipmentPredicate.Builder.equipment();
    }

    public static ItemPredicate.Builder itemPredicate() {
        return ItemPredicate.Builder.item();
    }

    public static LocationPredicate.Builder location() {
        return LocationPredicate.Builder.location();
    }

    public static DamageSourcePredicate.Builder damageSourcePredicate() {
        return DamageSourcePredicate.Builder.damageType();
    }

    public static net.minecraft.advancements.critereon.BlockPredicate.Builder locBlockBuilder(String tagOrId) {
        return tagOrId.startsWith("#")
                ? net.minecraft.advancements.critereon.BlockPredicate.Builder.block()
                  .of(blockTag(tagOrId))
                : net.minecraft.advancements.critereon.BlockPredicate.Builder.block()
                  .of(BuiltInRegistries.BLOCK.get(ResourceLocation.parse(tagOrId)));
    }

    public static net.minecraft.advancements.critereon.BlockPredicate.Builder locBlockBuilder(TagKey<Block> tag) {
        return locBlockBuilder("#" + tag.location());
    }

    public static MovementPredicate speed(MinMaxBounds.Doubles bounds) {
        return MovementPredicate.speed(bounds);
    }

    public static MovementPredicate horizontalSpeed(MinMaxBounds.Doubles bounds) {
        return MovementPredicate.horizontalSpeed(bounds);
    }

    public static MovementPredicate verticalSpeed(MinMaxBounds.Doubles bounds) {
        return MovementPredicate.verticalSpeed(bounds);
    }

    public static MovementPredicate fallDistance(MinMaxBounds.Doubles bounds) {
        return MovementPredicate.fallDistance(bounds);
    }

    public static MinMaxBounds.Doubles exactly(double value) {
        return MinMaxBounds.Doubles.exactly(value);
    }

    public static MinMaxBounds.Doubles atLeast(double value) {
        return MinMaxBounds.Doubles.atLeast(value);
    }

    public static MinMaxBounds.Doubles atMost(double value) {
        return MinMaxBounds.Doubles.atMost(value);
    }

    public static MinMaxBounds.Doubles between(double min, double max) {
        return MinMaxBounds.Doubles.between(min, max);
    }

    public static MinMaxBounds.Ints intsExactly(int value) {
        return MinMaxBounds.Ints.exactly(value);
    }

    public static MinMaxBounds.Ints intsAtLeast(int value) {
        return MinMaxBounds.Ints.atLeast(value);
    }

    public static MinMaxBounds.Ints intsAtMost(int value) {
        return MinMaxBounds.Ints.atMost(value);
    }

    public static MinMaxBounds.Ints intsBetween(int min, int max) {
        return MinMaxBounds.Ints.between(min, max);
    }

    public static IntRange exactRange(int value) {
        return IntRange.exact(value);
    }

    public static IntRange intRange(int min, int max) {
        return IntRange.range(min, max);
    }

    public static IntRange atLeastRange(int min) {
        return IntRange.lowerBound(min);
    }

    public static IntRange atMostRange(int max) {
        return IntRange.upperBound(max);
    }

    public static TagPredicate<DamageType> damageTag(String tag, boolean expected) {
        return new TagPredicate<>(TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse(stripHash(tag))),
                expected);
    }

    public static NbtPredicate nbt(String snbt) {
        try {
            return new NbtPredicate(TagParser.parseTag(snbt));
        } catch (CommandSyntaxException exception) {
            throw new IllegalArgumentException("Invalid nbt: " + snbt, exception);
        }
    }

    public static PlayerPredicate.Builder player() {
        return PlayerPredicate.Builder.player();
    }

    public static StatePropertiesPredicate.Builder properties() {
        return StatePropertiesPredicate.Builder.properties();
    }

    public BlockState state(String id, String... properties) {
        BlockState state = net.minecraft.core.registries.BuiltInRegistries.BLOCK.get(ResourceLocation.parse(id))
                .defaultBlockState();
        for (int i = 0; i + 1 < properties.length; i += 2) {
            Property<?> property = state.getBlock().getStateDefinition().getProperty(properties[i]);
            if (property != null) {
                state = setValue(state, property, properties[i + 1]);
            }
        }
        return state;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static BlockState setValue(BlockState state, Property property, String value) {
        return state.setValue(property, (Comparable) property.getValue(value).orElseThrow());
    }

    public static ResourceKey<Level> dimension(String id) {
        return ResourceKey.create(Registries.DIMENSION, rl(id));
    }

    public static Level.ExplosionInteraction interaction(String name) {
        return switch (name) {
            case "none" -> Level.ExplosionInteraction.NONE;
            case "block" -> Level.ExplosionInteraction.BLOCK;
            case "mob" -> Level.ExplosionInteraction.MOB;
            case "tnt" -> Level.ExplosionInteraction.TNT;
            case "trigger" -> Level.ExplosionInteraction.TRIGGER;
            default -> throw new IllegalArgumentException("Unknown explosion interaction: " + name);
        };
    }

    public static MovementPredicate allMovements(MovementPredicate... predicates) {
        MinMaxBounds.Doubles x = MinMaxBounds.Doubles.ANY;
        MinMaxBounds.Doubles y = MinMaxBounds.Doubles.ANY;
        MinMaxBounds.Doubles z = MinMaxBounds.Doubles.ANY;
        MinMaxBounds.Doubles speed = MinMaxBounds.Doubles.ANY;
        MinMaxBounds.Doubles horizontal = MinMaxBounds.Doubles.ANY;
        MinMaxBounds.Doubles vertical = MinMaxBounds.Doubles.ANY;
        MinMaxBounds.Doubles fall = MinMaxBounds.Doubles.ANY;
        for (MovementPredicate predicate : predicates) {
            x = orAny(x, predicate.x());
            y = orAny(y, predicate.y());
            z = orAny(z, predicate.z());
            speed = orAny(speed, predicate.speed());
            horizontal = orAny(horizontal, predicate.horizontalSpeed());
            vertical = orAny(vertical, predicate.verticalSpeed());
            fall = orAny(fall, predicate.fallDistance());
        }
        return new MovementPredicate(x, y, z, speed, horizontal, vertical, fall);
    }

    private static MinMaxBounds.Doubles orAny(MinMaxBounds.Doubles first, MinMaxBounds.Doubles second) {
        return first == MinMaxBounds.Doubles.ANY ? second : first;
    }
}
