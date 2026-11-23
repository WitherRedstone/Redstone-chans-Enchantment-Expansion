package com.chinaex123.redstone_enchants.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DeathMessageType;

import com.chinaex123.redstone_enchants.RedstoneEnchants;

/** 由 _gen_data.py 生成。 */
public final class ModDamageTypes {

    private ModDamageTypes() {
    }

    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(RedstoneEnchants.MODID, "water_block")),
                new DamageType("water_block", DamageScaling.ALWAYS, 0.0F, DamageEffects.HURT, DeathMessageType.DEFAULT));
        context.register(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(RedstoneEnchants.MODID, "water_rain")),
                new DamageType("water_rain", DamageScaling.ALWAYS, 0.0F, DamageEffects.HURT, DeathMessageType.DEFAULT));
    }
}
