package com.chinaex123.redstone_enchants.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.JsonOps;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

import com.chinaex123.redstone_enchants.RedstoneEnchants;

public class ModPredicates implements DataProvider {

    private final PackOutput.PathProvider pathProvider;

    public ModPredicates(PackOutput output) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "predicate");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(this.save(cache, "last_hope_target", LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().nbt(new NbtPredicate(snbt("{UUID: [I; 0, 0, 0, 0]}"))).build()).build()));
        tasks.add(this.save(cache, "percentages/10", LootItemRandomChanceCondition.randomChance(0.1F).build()));
        tasks.add(this.save(cache, "percentages/2-5", LootItemRandomChanceCondition.randomChance(0.025F).build()));
        tasks.add(this.save(cache, "percentages/20", LootItemRandomChanceCondition.randomChance(0.2F).build()));
        tasks.add(this.save(cache, "percentages/30", LootItemRandomChanceCondition.randomChance(0.3F).build()));
        tasks.add(this.save(cache, "percentages/45", LootItemRandomChanceCondition.randomChance(0.45F).build()));
        tasks.add(this.save(cache, "percentages/5", LootItemRandomChanceCondition.randomChance(0.05F).build()));
        tasks.add(this.save(cache, "percentages/60", LootItemRandomChanceCondition.randomChance(0.6F).build()));
        tasks.add(this.save(cache, "percentages/80", LootItemRandomChanceCondition.randomChance(0.8F).build()));
        return CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]));
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, LootItemCondition condition) {
        return DataProvider.saveStable(cache,
                LootItemCondition.DIRECT_CODEC.encodeStart(JsonOps.INSTANCE, condition).getOrThrow(),
                this.pathProvider.json(ResourceLocation.fromNamespaceAndPath(RedstoneEnchants.MODID, path)));
    }

    private static CompoundTag snbt(String snbt) {
        try {
            return TagParser.parseTag(snbt);
        } catch (CommandSyntaxException exception) {
            throw new IllegalArgumentException("Invalid SNBT: " + snbt, exception);
        }
    }

    @Override
    public String getName() {
        return "Redstone Enchants predicates";
    }
}
