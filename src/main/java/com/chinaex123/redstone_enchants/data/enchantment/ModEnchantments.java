package com.chinaex123.redstone_enchants.data.enchantment;

import com.chinaex123.redstone_enchants.config.ConfigEnabledCondition;
import com.chinaex123.redstone_enchants.config.RECommonConfig;
import com.chinaex123.redstone_enchants.data.ModDamageTypes;
import com.chinaex123.redstone_enchants.data.enchantment.type.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.minecraft.core.RegistrySetBuilder;

import java.util.ArrayList;
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

        for (String id : RECommonConfig.enchantmentIds()) {
            conditions.put(EnchantmentConstructorHelper.key(id), new ArrayList<>(List.of(new ConfigEnabledCondition(id))));
        }

        // 依赖其它模组的附魔，再叠加「模组已加载」条件
        requireMod(conditions, "dual_critical_boost", "apothic_attributes");
        requireMod(conditions, "rapid", "apothic_attributes");
        requireMod(conditions, "spell_magic_resist", "irons_spellbooks");
        requireMod(conditions, "spell_power", "irons_spellbooks");
        requireMod(conditions, "splash_delayed_explosion", "ars_nouveau");
        requireMod(conditions, "touch_bleeding", "apothic_attributes");
        requireMod(conditions, "undercurrent", "apothic_attributes", "twilightforest");

        return conditions;
    }

    private static void requireMod(Map<ResourceKey<?>, List<ICondition>> conditions, String id, String... modIds) {
        List<ICondition> list = conditions.computeIfAbsent(EnchantmentConstructorHelper.key(id),
                key -> new ArrayList<>());
        for (String modId : modIds) {
            list.add(new ModLoadedCondition(modId));
        }
    }
}
