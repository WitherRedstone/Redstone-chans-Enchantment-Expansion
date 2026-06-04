package com.chinaex123.redstone_enchants.event.armor_foot;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

/**
 * 飞马座：骑乘时获得缓降效果
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class PegasusEventHandler {
    private static final Identifier PEGASUS_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "pegasus");

    private static final int EFFECT_DURATION = 80; // 持续时间

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        if (boots.isEmpty()) return;

        Holder.Reference<Enchantment> pegasusEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(PEGASUS_ID)
                .orElse(null);

        if (pegasusEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = boots.getEnchantments().getLevel(pegasusEnchant);

        if (level > 0 && player.getVehicle() != null) {
            // 给玩家添加缓降
            if (!player.hasEffect(MobEffects.SLOW_FALLING)) {
                player.addEffect(new MobEffectInstance(
                        MobEffects.SLOW_FALLING,
                        EFFECT_DURATION,
                        0,
                        false,
                        false
                ));
            }

            // 给骑乘的生物添加缓降
            Entity vehicle = player.getVehicle();
            if (vehicle instanceof LivingEntity livingVehicle) {
                if (!livingVehicle.hasEffect(MobEffects.SLOW_FALLING)) {
                    livingVehicle.addEffect(new MobEffectInstance(
                            MobEffects.SLOW_FALLING,
                            EFFECT_DURATION,
                            0,
                            false,
                            false
                    ));
                }
            }
        } else {
            // 移除玩家的缓降
            if (player.hasEffect(MobEffects.SLOW_FALLING)) {
                player.removeEffect(MobEffects.SLOW_FALLING);
            }

            // 移除骑乘生物的缓降
            Entity vehicle = player.getVehicle();
            if (vehicle instanceof LivingEntity livingVehicle) {
                if (livingVehicle.hasEffect(MobEffects.SLOW_FALLING)) {
                    livingVehicle.removeEffect(MobEffects.SLOW_FALLING);
                }
            }
        }
    }
}