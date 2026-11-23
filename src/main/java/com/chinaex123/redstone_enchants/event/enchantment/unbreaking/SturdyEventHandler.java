package com.chinaex123.redstone_enchants.event.enchantment.unbreaking;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
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
    private static final ResourceLocation STURDY_ID = ResourceLocation.fromNamespaceAndPath(RedstoneEnchants.MODID, "sturdy");
    private static final String STURDY_TAG = "redstone_enchants:sturdy";

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        LivingEntity entity = event.getEntity();

        // 检查所有装备槽
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = entity.getItemBySlot(slot);
            if (stack.isEmpty()) continue;

            Holder.Reference<Enchantment> sturdyEnchant = entity.level()
                    .registryAccess()
                    .registryOrThrow(Registries.ENCHANTMENT)
                    .getHolder(STURDY_ID)
                    .orElse(null);

            if (sturdyEnchant == null) continue;

            @SuppressWarnings("deprecation")
            int level = stack.getEnchantments().getLevel(sturdyEnchant);
            if (level <= 0) continue;

            // 检查伤害来源
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
        if (!(event.getEntity() instanceof ItemEntity itemEntity)) return;

        ItemStack stack = itemEntity.getItem();

        // 检查是否有坚固附魔
        Holder.Reference<Enchantment> sturdyEnchant = event.getEntity().level()
                .registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(STURDY_ID)
                .orElse(null);

        boolean hasSturdy = false;
        if (sturdyEnchant != null) {
            @SuppressWarnings("deprecation")
            int level = stack.getEnchantments().getLevel(sturdyEnchant);
            hasSturdy = level > 0;
        }

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
        // 从受影响的实体列表中移除带坚固附魔的物品
        event.getAffectedEntities().removeIf(entity -> {
            if (entity instanceof ItemEntity itemEntity) {
                return hasSturdyEnchant(itemEntity.getItem(), itemEntity.level());
            }
            return false;
        });
    }

    @SubscribeEvent
    public static void onLightningStrike(EntityStruckByLightningEvent event) {
        // 如果被击中的实体是带坚固附魔的物品，则取消事件
        if (event.getEntity() instanceof ItemEntity itemEntity) {
            if (hasSturdyEnchant(itemEntity.getItem(), itemEntity.level())) {
                event.setCanceled(true);
            }
        }
    }

    private static boolean hasSturdyEnchant(ItemStack stack, net.minecraft.world.level.Level level) {
        if (stack.isEmpty()) return false;

        Holder.Reference<Enchantment> sturdyEnchant = level
                .registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(STURDY_ID)
                .orElse(null);

        if (sturdyEnchant == null) return false;

        @SuppressWarnings("deprecation")
        int level_val = stack.getEnchantments().getLevel(sturdyEnchant);
        return level_val > 0;
    }
}
