package com.chinaex123.redstone_enchants.data.enchantment;

import com.chinaex123.redstone_enchants.data.ModDamageTypes;
import com.chinaex123.redstone_enchants.data.enchantment.type.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.minecraft.core.RegistrySetBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 附魔注册 */
public final class ModEnchantments {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.ENCHANTMENT, ModEnchantments::bootstrap)
            .add(Registries.DAMAGE_TYPE, ModDamageTypes::bootstrap);

    public static final Map<ResourceKey<?>, List<ICondition>> CONDITIONS = createConditions();

    private ModEnchantments() {
    }

    private static void bootstrap(BootstrapContext<Enchantment> context) {
        EnchantmentConstructorHelper e = new EnchantmentConstructorHelper(context);
        CurseEnchantments.bootstrap(e);
        UnbreakingEnchantments.bootstrap(e);
        WeaponEnchantments.bootstrap(e);
        SwordsEnchantments.bootstrap(e);
        AllBowEnchantments.bootstrap(e);
        TridentEnchantments.bootstrap(e);
        MaceEnchantments.bootstrap(e);
        AllToolsEnchantments.bootstrap(e);
        AllShearEnchantments.bootstrap(e);
        AllFlintAndSteelEnchantments.bootstrap(e);
        AllFishingEnchantments.bootstrap(e);
        ShieldEnchantments.bootstrap(e);
        AllWeaponAndToolsEnchantments.bootstrap(e);
        SwordsAndBowEnchantments.bootstrap(e);
        TridentAndBowEnchantments.bootstrap(e);
        ElytraEnchantments.bootstrap(e);
        AllArmorEnchantments.bootstrap(e);
        HeadArmorEnchantments.bootstrap(e);
        ChestArmorEnchantments.bootstrap(e);
        LegArmorEnchantments.bootstrap(e);
        FootArmorEnchantments.bootstrap(e);
        HorseArmorEnchantments.bootstrap(e);
        AnimalArmorEnchantments.bootstrap(e);
        WolfArmorEnchantments.bootstrap(e);
    }

    private static Map<ResourceKey<?>, List<ICondition>> createConditions() {
        Map<ResourceKey<?>, List<ICondition>> conditions = new HashMap<>();
        conditions.put(EnchantmentConstructorHelper.key("dual_critical_boost"), List.of(new ModLoadedCondition("apothic_attributes")));
        conditions.put(EnchantmentConstructorHelper.key("rapid"), List.of(new ModLoadedCondition("apothic_attributes")));
        conditions.put(EnchantmentConstructorHelper.key("spell_magic_resist"), List.of(new ModLoadedCondition("irons_spellbooks")));
        conditions.put(EnchantmentConstructorHelper.key("spell_power"), List.of(new ModLoadedCondition("irons_spellbooks")));
        conditions.put(EnchantmentConstructorHelper.key("splash_delayed_explosion"), List.of(new ModLoadedCondition("ars_nouveau")));
        conditions.put(EnchantmentConstructorHelper.key("touch_bleeding"), List.of(new ModLoadedCondition("apothic_attributes")));
        conditions.put(EnchantmentConstructorHelper.key("undercurrent"), List.of(new ModLoadedCondition("apothic_attributes"), new ModLoadedCondition("twilightforest")));
        return conditions;
    }
}
