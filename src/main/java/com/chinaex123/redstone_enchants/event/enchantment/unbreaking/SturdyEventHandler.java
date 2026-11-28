package com.chinaex123.redstone_enchants.event.enchantment.unbreaking;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

/**
 * 坚固：装备免疫爆炸、闪电和岩浆
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class SturdyEventHandler {
    private static final ResourceKey<Enchantment> STURDY = EnchantContext.key("sturdy");

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (event.getEntity().level().isClientSide()) return;
        LivingEntity entity = event.getEntity();

        // 检查所有装备栏
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = entity.getItemBySlot(slot);
            if (stack.isEmpty()) continue;
            if (EnchantContext.level(stack, STURDY) <= 0) continue;

            var source = event.getSource();

            // 免疫爆炸
            if (source.is(DamageTypeTags.IS_EXPLOSION)) {
                event.setNewDamage(0);
                return;
            }

            // 免疫闪电
            if (source.is(DamageTypes.LIGHTNING_BOLT)) {
                event.setNewDamage(0);
                return;
            }

            // 免疫岩浆/火焰
            if (source.is(DamageTypeTags.IS_FIRE)) {
                event.setNewDamage(0);
                return;
            }
        }
    }

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getEntity() instanceof ItemEntity itemEntity)) return;

        ItemStack stack = itemEntity.getItem();
        boolean hasSturdy = EnchantContext.level(stack, STURDY) > 0;

        if (hasSturdy) {
            // 设置防火
            if (!stack.has(DataComponents.FIRE_RESISTANT)) {
                stack.set(DataComponents.FIRE_RESISTANT, Unit.INSTANCE);
            }
        } else {
            // 移除防火
            if (stack.has(DataComponents.FIRE_RESISTANT)) {
                stack.remove(DataComponents.FIRE_RESISTANT);
            }
        }
    }

    @SubscribeEvent
    public static void onExplosion(ExplosionEvent.Detonate event) {
        if (event.getLevel().isClientSide()) return;
        event.getAffectedEntities().removeIf(entity -> {
            if (entity instanceof ItemEntity itemEntity) {
                return hasSturdyEnchant(itemEntity.getItem());
            }
            return false;
        });
    }

    @SubscribeEvent
    public static void onLightningStrike(EntityStruckByLightningEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        if (event.getEntity() instanceof ItemEntity itemEntity) {
            if (hasSturdyEnchant(itemEntity.getItem())) {
                event.setCanceled(true);
            }
        }
    }

    private static boolean hasSturdyEnchant(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return EnchantContext.level(stack, STURDY) > 0;
    }
}
