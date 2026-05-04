package com.chinaex123.redstone_enchants.event.swords;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

import java.util.Random;

/**
 * 斩首：增加有头颅生物的头颅掉落概率
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class DecapitationEventHandler {
    private static final Random RANDOM = new Random();
    private static final Identifier DECAPITATION_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "decapitation");

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        LivingEntity attacker = event.getSource().getEntity() instanceof LivingEntity living ? living : null;
        if (attacker == null) return;

        ItemStack weapon = attacker.getMainHandItem();
        Holder.Reference<Enchantment> decapitationEnchant = attacker.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(DECAPITATION_ID)
                .orElse(null);

        if (decapitationEnchant == null) return;

        int level = EnchantmentHelper.getEnchantmentLevel(decapitationEnchant, attacker);
        if (level <= 0) return;

        LivingEntity entity = event.getEntity();
        String entityType = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getPath();

        // 查找对应的头颅物品（支持所有模组）
        ItemStack skullStack = findSkullForEntity(entityType);
        if (skullStack.isEmpty()) return;

        // 计算掉落概率：5级100%，线性增长（1级=20%, 2级=40%, 3级=60%, 4级=80%, 5级=100%）
        float dropChance = level * 0.2f;

        if (RANDOM.nextFloat() < dropChance) {
            event.getDrops().add(new net.minecraft.world.entity.item.ItemEntity(
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
            Identifier skullId = Identifier.fromNamespaceAndPath("minecraft", name);
            var itemOpt = BuiltInRegistries.ITEM.get(skullId);
            if (itemOpt.isPresent()) {
                ItemStack stack = new ItemStack(itemOpt.get());
                if (!stack.isEmpty()) return stack;
            }

            for (Identifier itemId : BuiltInRegistries.ITEM.keySet()) {
                if (itemId.getPath().equals(name)) {
                    var itemOpt2 = BuiltInRegistries.ITEM.get(itemId);
                    if (itemOpt2.isPresent()) {
                        ItemStack stack = new ItemStack(itemOpt2.get());
                        if (!stack.isEmpty()) return stack;
                    }
                }
            }
        }

        return ItemStack.EMPTY;
    }
}
