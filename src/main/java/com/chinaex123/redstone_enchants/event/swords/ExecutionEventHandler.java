package com.chinaex123.redstone_enchants.event.swords;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 处决：对生命值低于25%的目标直接秒杀
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class ExecutionEventHandler {
    private static final Identifier EXECUTION_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "execution");

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;

        LivingEntity target = event.getEntity();

        ItemStack weapon = attacker.getMainHandItem();
        Holder.Reference<Enchantment> executionEnchant = attacker.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(EXECUTION_ID)
                .orElse(null);

        if (executionEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = weapon.getEnchantments().getLevel(executionEnchant);
        if (level <= 0) return;

        // 检查目标生命值是否低于25%
        float healthPercent = target.getHealth() / target.getMaxHealth();
        if (healthPercent < 0.25f) {
            // 直接设置为0，秒杀
            event.setNewDamage(target.getHealth());
        }
    }
}
