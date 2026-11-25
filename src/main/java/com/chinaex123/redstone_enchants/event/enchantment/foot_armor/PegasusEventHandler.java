package com.chinaex123.redstone_enchants.event.enchantment.foot_armor;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantEffectTracker;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
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
 * <p>
 * 只移除本附魔自己给的缓降，玩家或坐骑从其它来源获得的缓降不会被误删。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class PegasusEventHandler {
    private static final ResourceLocation PEGASUS_ID = RedstoneEnchants.id("pegasus");

    /** 持续时间 */
    private static final int EFFECT_DURATION = 80;

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ResourceKey<Enchantment> pegasusEnchant = EnchantContext.key(PEGASUS_ID);

        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        int level = boots.isEmpty() ? 0 : EnchantContext.level(boots, pegasusEnchant);

        Entity vehicle = player.getVehicle();

        if (level > 0 && vehicle != null) {
            // 给玩家添加缓降
            EnchantEffectTracker.apply(player, MobEffects.SLOW_FALLING, EFFECT_DURATION, 0);

            // 给骑乘的生物添加缓降
            if (vehicle instanceof LivingEntity livingVehicle) {
                EnchantEffectTracker.apply(livingVehicle, MobEffects.SLOW_FALLING, EFFECT_DURATION, 0);
            }
        } else {
            // 移除玩家自己给的缓降
            EnchantEffectTracker.removeIfTracked(player, MobEffects.SLOW_FALLING);

            // 移除骑乘生物身上自己给的缓降
            if (vehicle instanceof LivingEntity livingVehicle) {
                EnchantEffectTracker.removeIfTracked(livingVehicle, MobEffects.SLOW_FALLING);
            }
        }
    }
}
