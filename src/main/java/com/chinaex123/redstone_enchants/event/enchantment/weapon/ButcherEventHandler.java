package com.chinaex123.redstone_enchants.event.enchantment.weapon;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 屠夫：击杀动物时增加其掉落物
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class ButcherEventHandler {
    private static final ResourceLocation BUTCHER_ID = RedstoneEnchants.id("butcher");

    /** 1 级时额外掉落数量的比例 */
    private static final double EXTRA_DROP_RATIO_BASE = 0.5D;
    /** 每级额外增加的比例 */
    private static final double EXTRA_DROP_RATIO_PER_LEVEL = 0.5D;
    /** 额外掉落的最小数量 */
    private static final int MIN_EXTRA_DROP_COUNT = 1;

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;

        LivingEntity entity = event.getEntity();

        // 只处理动物
        if (!(entity instanceof Animal)) return;

        ItemStack weapon = attacker.getMainHandItem();
        ResourceKey<Enchantment> butcherEnchant = EnchantContext.key(BUTCHER_ID);

        int level = EnchantContext.level(weapon, butcherEnchant);
        if (level <= 0) return;

        ServerLevel serverLevel = (ServerLevel) entity.level();

        // 为每个掉落物额外增加原数量的一半（每级）
        List<ItemEntity> additionalDrops = new ArrayList<>();
        for (ItemEntity drop : event.getDrops()) {
            ItemStack stack = drop.getItem();

            // 计算额外数量：原数量 * 比例，向上取整
            double extraRatio = EXTRA_DROP_RATIO_BASE + EXTRA_DROP_RATIO_PER_LEVEL * (level - 1);
            int extraCount = Math.max(MIN_EXTRA_DROP_COUNT, (int) Math.ceil(stack.getCount() * extraRatio));

            ItemStack extraStack = stack.copy();
            extraStack.setCount(extraCount);

            ItemEntity extraDrop = new ItemEntity(
                    serverLevel,
                    entity.getX(),
                    entity.getY(),
                    entity.getZ(),
                    extraStack
            );
            additionalDrops.add(extraDrop);
        }

        event.getDrops().addAll(additionalDrops);
    }
}
