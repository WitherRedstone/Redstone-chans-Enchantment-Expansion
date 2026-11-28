package com.chinaex123.redstone_enchants.event.enchantment.swords;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

import java.util.Random;

/**
 * 斩首：增加生物头颅的掉落概率
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class DecapitationEventHandler {
    private static final Random RANDOM = new Random();
    private static final ResourceLocation DECAPITATION_ID = RedstoneEnchants.id("decapitation");

    /** 1 级时的头颅掉落概率 */
    private static final double DROP_CHANCE_BASE = 0.2D;
    /** 每级额外增加的头颅掉落概率 */
    private static final double DROP_CHANCE_PER_LEVEL = 0.2D;

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        LivingEntity attacker = event.getSource().getEntity() instanceof LivingEntity living ? living : null;
        if (attacker == null) return;

        ItemStack weapon = attacker.getMainHandItem();
        ResourceKey<Enchantment> decapitationEnchant = EnchantContext.key(DECAPITATION_ID);

        int level = EnchantContext.level(attacker, decapitationEnchant);
        if (level <= 0) return;

        LivingEntity entity = event.getEntity();
        String entityType = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getPath();

        // 查找对应的头颅物品
        ItemStack skullStack = findSkullForEntity(entityType);
        if (skullStack.isEmpty()) return;

        // 计算掉落概率（1 级 20%，每级额外 +20%）
        float dropChance = (float) Math.min(1.0D, DROP_CHANCE_BASE + DROP_CHANCE_PER_LEVEL * (level - 1));

        if (RANDOM.nextFloat() < dropChance) {
            event.getDrops().add(new ItemEntity(
                    entity.level(),
                    entity.getX(),
                    entity.getY(),
                    entity.getZ(),
                    skullStack
            ));
        }
    }

    private static ItemStack findSkullForEntity(String entityType) {
        // 尝试多种命名格式
        String[] possibleNames = {
                entityType + "_head",
                entityType + "_skull",
                "head_" + entityType,
                "skull_" + entityType
        };

        for (String name : possibleNames) {
            // 检查 Minecraft 原版
            ResourceLocation skullId = ResourceLocation.fromNamespaceAndPath("minecraft", name);
            if (BuiltInRegistries.ITEM.containsKey(skullId)) {
                ItemStack stack = new ItemStack(BuiltInRegistries.ITEM.get(skullId));
                if (!stack.isEmpty()) return stack;
            }

            // 检查所有模组的物品
            for (ResourceLocation itemId : BuiltInRegistries.ITEM.keySet()) {
                if (itemId.getPath().equals(name)) {
                    ItemStack stack = new ItemStack(BuiltInRegistries.ITEM.get(itemId));
                    if (!stack.isEmpty()) return stack;
                }
            }
        }

        return ItemStack.EMPTY;
    }
}
