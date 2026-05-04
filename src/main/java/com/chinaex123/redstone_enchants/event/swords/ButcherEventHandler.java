package com.chinaex123.redstone_enchants.event.swords;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
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
import java.util.Random;

/**
 * 屠夫：击杀动物时增加其掉落物
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class ButcherEventHandler {
    private static final Random RANDOM = new Random();
    private static final Identifier BUTCHER_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "butcher");

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;

        LivingEntity entity = event.getEntity();

        // 只处理动物（包括猪、牛、羊等）
        if (!(entity instanceof Animal)) return;

        ItemStack weapon = attacker.getMainHandItem();
        Holder.Reference<Enchantment> butcherEnchant = attacker.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(BUTCHER_ID)
                .orElse(null);

        if (butcherEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = weapon.getEnchantments().getLevel(butcherEnchant);
        if (level <= 0) return;

        ServerLevel serverLevel = (ServerLevel) entity.level();

        // 为每个掉落物额外增加原数量的一半（每级）
        List<ItemEntity> additionalDrops = new ArrayList<>();
        for (ItemEntity drop : event.getDrops()) {
            ItemStack stack = drop.getItem();

            // 计算额外数量：原数量 * 0.5 * 等级，向上取整
            int extraCount = Math.max(1, (int) Math.ceil(stack.getCount() * 0.5 * level));

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
