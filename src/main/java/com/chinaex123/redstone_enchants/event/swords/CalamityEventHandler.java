package com.chinaex123.redstone_enchants.event.swords;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 灾厄：攻击时有概率让目标获得随机一个负面效果
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class CalamityEventHandler {
    private static final Identifier CALAMITY_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "calamity");
    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getDirectEntity() instanceof Player attacker)) return;

        LivingEntity target = event.getEntity();
        ItemStack weapon = attacker.getMainHandItem();

        if (weapon.isEmpty()) return;

        Holder.Reference<Enchantment> calamityEnchant = attacker.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(CALAMITY_ID)
                .orElse(null);

        if (calamityEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = weapon.getEnchantments().getLevel(calamityEnchant);
        if (level <= 0) return;

        // 每级5%概率触发
        float chance = level * 0.05F;
        if (RANDOM.nextFloat() < chance) {
            // 获取所有负面效果
            List<Holder.Reference<MobEffect>> negativeEffects = new ArrayList<>();
            attacker.level().registryAccess()
                    .lookupOrThrow(Registries.MOB_EFFECT)
                    .listElements()
                    .forEach(effect -> {
                        if (!effect.value().isBeneficial()) {
                            // 黑名单：排除试炼之兆和不祥之兆
                            if (effect.is(MobEffects.BAD_OMEN) || effect.is(MobEffects.TRIAL_OMEN)) {
                                return;
                            }
                            negativeEffects.add(effect);
                        }
                    });

            if (!negativeEffects.isEmpty()) {
                // 随机选择一个负面效果
                Holder.Reference<MobEffect> effect = negativeEffects.get(RANDOM.nextInt(negativeEffects.size()));

                // 持续时间：4-10秒
                int duration = (4 + RANDOM.nextInt(7)) * 20;
                int amplifier = 0; // 1级效果

                target.addEffect(new MobEffectInstance(effect, duration, amplifier));
            }
        }
    }
}
