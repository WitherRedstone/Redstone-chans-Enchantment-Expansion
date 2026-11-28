package com.chinaex123.redstone_enchants.config;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class RECommonConfig {
    public static final ModConfigSpec SPEC;

    private static final Map<String, ModConfigSpec.BooleanValue> ENCHANTMENTS = new LinkedHashMap<>();

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("[诅咒]类型附魔").push("[Curse]Type Enchantments");
        toggle(builder, "腐蚀", "curse_of_rust");
        toggle(builder, "自爆", "curse_of_blast");
        toggle(builder, "静止诅咒", "curse_of_stillness");
        toggle(builder, "饥饿诅咒", "curse_of_hunger");
        toggle(builder, "破损诅咒", "curse_of_breaking");
        toggle(builder, "失明诅咒", "curse_of_blindness");
        toggle(builder, "恐高症", "curse_of_acrophobia");
        toggle(builder, "低洼恐惧症", "curse_of_basiphobia");
        toggle(builder, "死亡诅咒", "curse_of_death");
        toggle(builder, "重力诅咒", "curse_of_gravity");
        toggle(builder, "隐形诅咒", "curse_of_hiding");
        toggle(builder, "倒霉诅咒", "curse_of_unlucky");
        toggle(builder, "短手诅咒", "curse_of_reach");
        toggle(builder, "笨拙诅咒", "curse_of_clumsiness");
        toggle(builder, "双刃剑诅咒", "curse_of_double_edge");
        toggle(builder, "水源诅咒", "curse_of_water_source");
        toggle(builder, "脆弱诅咒", "curse_of_vulnerability");
        builder.pop();

        builder.comment("[通用]类型附魔").push("[Unbreaking]Type Enchantments");
        toggle(builder, "牺牲", "sacrifice");
        toggle(builder, "高级耐久", "advanced_unbreaking");
        toggle(builder, "坚固", "sturdy");
        toggle(builder, "保全", "preservation");
        toggle(builder, "坚不可摧", "indestructible");
        builder.pop();

        builder.comment("[武器]类型附魔").push("[Weapon]Type Enchantments");
        toggle(builder, "第一印象", "first_impression");
        toggle(builder, "影袭", "shadow_assault");
        toggle(builder, "经验利刃", "xp_blade");
        toggle(builder, "承诺", "committed");
        toggle(builder, "驱魔", "exorcism");
        toggle(builder, "背刺", "backstab");
        toggle(builder, "处决", "execution");
        toggle(builder, "沉重", "weighted");
        toggle(builder, "屠夫", "butcher");
        builder.pop();

        builder.comment("[剑]类型附魔").push("[Swords]Type Enchantments");
        toggle(builder, "Boss克星", "bane_boss");
        toggle(builder, "微小克星", "bane_bady");
        toggle(builder, "灾厄克星", "bane_illager");
        toggle(builder, "下界克星", "bane_nether");
        toggle(builder, "末地克星", "bane_end");
        toggle(builder, "幻翼克星", "bane_phantom");
        toggle(builder, "毒触之刃", "touch_poison");
        toggle(builder, "凋零之触", "touch_wither");
        toggle(builder, "流血之触", "touch_bleeding");
        toggle(builder, "经验收割者", "xp_reaper_mobs");
        toggle(builder, "吞噬", "devouring");
        toggle(builder, "锁链", "chains");
        toggle(builder, "发电机", "dynamo");
        toggle(builder, "迅捷斩影", "swift_shadowcutter");
        toggle(builder, "越战越勇", "under_pressure");
        toggle(builder, "抗击之盾", "resilience_shield");
        toggle(builder, "激发潜能", "unleash_potential");
        toggle(builder, "水瓶投射", "water_bottle_projection");
        toggle(builder, "最后的希望", "last_hope");
        toggle(builder, "暗流涌动", "undercurrent");
        toggle(builder, "影刺", "shadow_pierce");
        toggle(builder, "生命吸取", "life_steal");
        toggle(builder, "斩首", "decapitation");
        toggle(builder, "福佑", "boons");
        toggle(builder, "灾厄", "calamity");
        toggle(builder, "消解", "nullify");
        toggle(builder, "伏击", "ambush");
        toggle(builder, "均衡器", "equalizer");
        builder.pop();

        builder.comment("[弓]类型附魔").push("[Bow]Type Enchantments");
        toggle(builder, "迅捷蔓延", "splash_speed");
        toggle(builder, "缓慢蔓延", "splash_slowness");
        toggle(builder, "治疗蔓延", "splash_regeneration");
        toggle(builder, "饥饿蔓延", "splash_hunger");
        toggle(builder, "失明蔓延", "splash_blindness");
        toggle(builder, "发光蔓延", "splash_glowing");
        toggle(builder, "中毒蔓延", "splash_poison");
        toggle(builder, "凋零蔓延", "splash_wither");
        toggle(builder, "缓降蔓延", "splash_slow_falling");
        toggle(builder, "蓄风蔓延", "splash_wind_charged");
        toggle(builder, "寄生蔓延", "splash_infested");
        toggle(builder, "渗浆蔓延", "splash_oozing");
        toggle(builder, "盘丝蔓延", "splash_weaving");
        toggle(builder, "延迟爆破", "splash_delayed_explosion");
        toggle(builder, "铁砧雨", "rain_forge");
        toggle(builder, "石锥雨", "rain_dripstone");
        toggle(builder, "冰霜箭", "ice_arrows");
        toggle(builder, "火焰箭", "fire_arrows");
        toggle(builder, "精准射击", "accuracy_shot");
        toggle(builder, "爆裂箭矢", "blast_arrows");
        toggle(builder, "爆炸箭", "bomb_arrows");
        toggle(builder, "子弹时间", "bullet_time");
        toggle(builder, "绝命箭矢", "fatal_arrow");
        toggle(builder, "玻璃破坏者", "glass_breaker");
        toggle(builder, "霰弹枪", "shotgun");
        toggle(builder, "分散", "scatter");
        toggle(builder, "焦点", "focus");
        toggle(builder, "连锁反应", "chain_reaction");
        toggle(builder, "跳弹", "ricochet");
        toggle(builder, "速射", "rapid");
        toggle(builder, "伏特", "volt");
        toggle(builder, "狙击", "snipe");
        builder.pop();

        builder.comment("[三叉戟]类型附魔").push("[Trident]Type Enchantments");
        toggle(builder, "水生克星", "bane_water");
        toggle(builder, "海风", "sea_breeze");
        toggle(builder, "雷鸣", "thundering");
        toggle(builder, "水下爆破", "underwater_blasting");
        toggle(builder, "投掷强化", "throwing_enhancement");
        builder.pop();

        builder.comment("[重锤]类型附魔").push("[Mace]Type Enchantments");
        toggle(builder, "闪电使者", "boltbringer");
        toggle(builder, "俯冲炸弹", "dive_bomb");
        toggle(builder, "重击手", "slugger");
        toggle(builder, "风力推进", "wind_propulsion");
        toggle(builder, "势能转化", "potential_conversion");
        toggle(builder, "轻重两仪", "lightness-heavy_dualism");
        builder.pop();

        builder.comment("[工具]类型附魔").push("[Tool]Type Enchantments");
        toggle(builder, "基岩破坏者", "bedrock_breaker");
        toggle(builder, "幻岩转化", "rock_illusion");
        toggle(builder, "急速", "haste");
        toggle(builder, "经验源泉", "xp_spring_block");
        toggle(builder, "地质学", "geology");
        toggle(builder, "点石成金", "goldfinger");
        toggle(builder, "磁力", "magnet");
        toggle(builder, "自动熔炼", "auto_smelt");
        toggle(builder, "挖掘机", "excavator");
        toggle(builder, "湿润", "moist");
        toggle(builder, "连锁效率", "chain_haste");
        toggle(builder, "伐木", "timber");
        toggle(builder, "精通采集", "master_gatherer");
        builder.pop();

        builder.comment("[特殊工具]类型附魔").push("[Special Tools]Type Enchantments");
        builder.pop();

        builder.comment("[剪刀]类型附魔").push("[Shear]Type Enchantments");
        toggle(builder, "牧羊人", "shepherd");
        toggle(builder, "绵延不绝", "endless_wool");
        toggle(builder, "经验修剪", "experience_shear");
        toggle(builder, "收获回响", "harvest_echo");
        builder.pop();

        builder.comment("[刷子]类型附魔").push("[Brush]Type Enchantments");
        builder.pop();

        builder.comment("[打火石]类型附魔").push("[Flint and Steel]Type Enchantments");
        toggle(builder, "灼烧", "searing");
        builder.pop();

        builder.comment("[鱼竿]类型附魔").push("[Fishing]Type Enchantments");
        toggle(builder, "渔夫", "angler");
        toggle(builder, "导电鱼线", "conductive_line");
        toggle(builder, "潮汐感应", "tide_sense");
        builder.pop();

        builder.comment("[盾牌]类型附魔").push("[Shield]Type Enchantments");
        toggle(builder, "吸收护盾", "absorbent_shield");
        toggle(builder, "盾甲", "shield_armor");
        toggle(builder, "抗退盾牌", "stable_shield");
        toggle(builder, "力量之盾", "strength_shield");
        toggle(builder, "战斗回响", "echoes_battle");
        builder.pop();

        builder.comment("[武器+工具]类型附魔").push("[Weapon+Tool]Type Enchantments");
        toggle(builder, "延伸", "extend");
        toggle(builder, "轻便", "lightweight");
        builder.pop();

        builder.comment("[剑+弓]类型附魔").push("[Sword+Bow]Type Enchantments");
        toggle(builder, "空中抛掷", "air_toss");
        toggle(builder, "赌徒", "gambler");
        builder.pop();

        builder.comment("[三叉戟+弓]类型附魔").push("[Trident+Bow]Type Enchantments");
        toggle(builder, "永恒冰霜", "eternal_frost");
        toggle(builder, "传送", "teleport");
        builder.pop();

        builder.comment("[鞘翅]类型附魔").push("[Elytra]Type Enchantments");
        toggle(builder, "末影之心", "ender_heart");
        toggle(builder, "月球漫步", "moonwalk");
        toggle(builder, "安全着陆", "safe_landing");
        builder.pop();

        builder.comment("[护甲通用]类型附魔").push("[Armor]Type Enchantments");
        toggle(builder, "日光防护", "protection_day");
        toggle(builder, "夜间防护", "protection_night");
        toggle(builder, "末地防护", "protection_end");
        toggle(builder, "下界防护", "protection_nether");
        toggle(builder, "锋芒之险", "dangerous_edge");
        toggle(builder, "狂怒", "fury");
        toggle(builder, "外骨骼", "exoskeleton");
        toggle(builder, "消防", "fire_protection");
        toggle(builder, "脆弱护甲", "weak_armor");
        toggle(builder, "雪球", "snowball");
        toggle(builder, "坚定", "fortitude");
        toggle(builder, "重生护盾", "revive_ward");
        toggle(builder, "昼夜流转", "daynight_cycle");
        builder.pop();

        builder.comment("[头盔]类型附魔").push("[Helmet]Type Enchantments");
        toggle(builder, "[移动轨迹]试炼之兆", "trail_trial_omen");
        toggle(builder, "[移动轨迹]樱花", "trail_cherry_leaves");
        toggle(builder, "[移动轨迹]龙息", "trail_dragon_breath");
        toggle(builder, "[移动轨迹]幽匿", "trail_sculk_soul");
        toggle(builder, "[移动轨迹]烟花火箭", "trail_firework");
        toggle(builder, "[移动轨迹]荧光", "trail_glow");
        toggle(builder, "[移动轨迹]雪花", "trail_snowflake");
        toggle(builder, "[移动轨迹]橙色星星", "trail_wax_on");
        toggle(builder, "[移动轨迹]白色星星", "trail_wax_off");
        toggle(builder, "迷你化", "minify");
        toggle(builder, "巨大化", "maximization");
        toggle(builder, "幸运之光", "lucky_light");
        toggle(builder, "幸运增幅", "lucky_boost");
        toggle(builder, "反伪装", "anti_camouflage");
        toggle(builder, "绝境逆袭", "desperate_counter");
        toggle(builder, "以寡敌众", "against_all_odds");
        toggle(builder, "矿工", "adaptive");
        builder.pop();

        builder.comment("[胸甲]类型附魔").push("[Chestplate]Type Enchantments");
        toggle(builder, "防弹", "bulletproof");
        toggle(builder, "坚毅壁垒", "resilient_bastion");
        toggle(builder, "检索", "retrieval");
        toggle(builder, "活力", "vitality");
        toggle(builder, "双重暴击", "dual_critical_boost");
        toggle(builder, "法术增幅", "spell_power");
        toggle(builder, "法术防护", "spell_magic_resist");
        toggle(builder, "狂战士", "berserk");
        toggle(builder, "冰霜荆棘", "frost_thorn");
        builder.pop();

        builder.comment("[护腿]类型附魔").push("[Leggings]Type Enchantments");
        toggle(builder, "快速游泳", "fast_swim");
        toggle(builder, "潜行庇佑", "fortress_stance");
        toggle(builder, "隐身斗篷", "invisibility_cloak");
        toggle(builder, "抗性守护", "resilience_sentinel");
        toggle(builder, "安全坠落", "safe_fall");
        toggle(builder, "跨步", "striding");
        toggle(builder, "战术护膝", "tactical_knee");
        builder.pop();

        builder.comment("[靴子]类型附魔").push("[Boots]Type Enchantments");
        toggle(builder, "迅捷光环", "aura_speed");
        toggle(builder, "缓慢光环", "aura_slowness");
        toggle(builder, "急迫光环", "aura_haste");
        toggle(builder, "力量光环", "aura_strength");
        toggle(builder, "跳跃光环", "aura_jump_boost");
        toggle(builder, "生命光环", "aura_regeneration");
        toggle(builder, "抗性光环", "aura_resistance");
        toggle(builder, "虚弱光环", "aura_weakness");
        toggle(builder, "中毒光环", "aura_poison");
        toggle(builder, "凋零光环", "aura_wither");
        toggle(builder, "发光光环", "aura_glowing");
        toggle(builder, "燃烧光环", "aura_burning");
        toggle(builder, "寄生光环", "aura_infested");
        toggle(builder, "熔岩行者", "walker_magma");
        toggle(builder, "草皮行者", "walker_grass");
        toggle(builder, "融雪行者", "walker_snowmelt");
        toggle(builder, "疾驰", "gallop");
        toggle(builder, "沙地疾行", "gallop_sand");
        toggle(builder, "末路疾行", "gallop_end");
        toggle(builder, "庄稼舞", "crop_dance");
        toggle(builder, "弹跳强化", "jump_amplifier");
        toggle(builder, "飞马座", "pegasus");
        toggle(builder, "踏浪者", "wave_walker");
        toggle(builder, "踏焰者", "flame_walker");
        builder.pop();

        builder.comment("[马铠]类型附魔").push("[Horse Armor]Type Enchantments");
        toggle(builder, "寒霜蹄", "frost_hooves");
        toggle(builder, "我的小马驹", "my_little_pony");
        toggle(builder, "牧场", "pasture");
        toggle(builder, "精神", "spirit");
        builder.pop();

        builder.comment("[马铠+狼铠]类型附魔").push("[Horse+Wolf Armor]Type Enchantments");
        toggle(builder, "强化铠甲", "enhanced_armor");
        toggle(builder, "信使", "messenger");
        builder.pop();

        builder.comment("[狼铠]类型附魔").push("[Wolf Armor]Type Enchantments");
        toggle(builder, "狼魂守护", "wolf_spirit_shield");
        toggle(builder, "追踪者", "tracker");
        toggle(builder, "狼群领袖", "pack_leader");
        toggle(builder, "尸体回收", "carrion_eater");
        builder.pop();

        SPEC = builder.build();
    }

    private RECommonConfig() {
    }

    private static void toggle(ModConfigSpec.Builder builder, String name, String id) {
        ENCHANTMENTS.put(id, builder
                .comment(name + "（" + id + "）：是否在游戏中注册该附魔")
                .define(id, true));
    }

    public static Set<String> enchantmentIds() {
        return Collections.unmodifiableSet(ENCHANTMENTS.keySet());
    }

    public static boolean isEnabled(String id) {
        ModConfigSpec.BooleanValue value = ENCHANTMENTS.get(id);
        if (value == null) {
            return true;
        }
        try {
            return value.get();
        } catch (IllegalStateException e) {
            return true;
        }
    }

    public static boolean isEnabled(ResourceKey<Enchantment> key) {
        return isEnabled(key.location().getPath());
    }
}
