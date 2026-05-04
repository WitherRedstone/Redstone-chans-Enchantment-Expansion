package com.chinaex123.redstone_enchants.event.unbreaking;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * 保全：装备不会因无耐久而损坏消失
 * 主功能在PreservationMixin
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class PreservationEventHandler {
    private static final Identifier PRESERVATION_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "preservation");
    private static final Map<String, Integer> lastDamageMap = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        Holder.Reference<Enchantment> preservationEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(PRESERVATION_ID)
                .orElse(null);

        if (preservationEnchant == null) return;

        // 检查所有装备槽位
        for (ItemStack stack : player.getInventory().getNonEquipmentItems()) {
            if (stack.isEmpty()) continue;

            @SuppressWarnings("deprecation")
            int level = stack.getEnchantments().getLevel(preservationEnchant);
            if (level <= 0) continue;

            int currentDamage = stack.getDamageValue();
            int maxDamage = stack.getMaxDamage();

            String stackKey = player.getUUID().toString() + "_" + System.identityHashCode(stack);

            // 检测是否刚达到最大耐久
            if (currentDamage >= maxDamage) {
                Integer lastDamage = lastDamageMap.get(stackKey);
                if (lastDamage == null || lastDamage < maxDamage) {
                    // 刚达到最大耐久，播放音效和粒子
                    player.playSound(SoundEvents.ITEM_BREAK.value(), 1.0F, 1.0F);

                    ItemParticleOption particle = new ItemParticleOption(ParticleTypes.ITEM, stack.getItem());
                    for (int i = 0; i < 5; i++) {
                        double dx = (player.getRandom().nextDouble() - 0.5) * 0.5;
                        double dy = player.getRandom().nextDouble() * 0.5;
                        double dz = (player.getRandom().nextDouble() - 0.5) * 0.5;
                        player.level().addParticle(particle,
                                player.getX(), player.getY() + 1.5, player.getZ(),
                                dx, dy, dz);
                    }
                }
            }

            lastDamageMap.put(stackKey, currentDamage);
        }
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();

        ItemStack stack = player.getMainHandItem();
        if (stack.isEmpty()) return;

        Holder.Reference<Enchantment> preservationEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(PRESERVATION_ID)
                .orElse(null);

        if (preservationEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = stack.getEnchantments().getLevel(preservationEnchant);
        if (level <= 0) return;

        // 如果耐久为最大值（0耐久状态），阻止挖掘
        if (stack.getDamageValue() >= stack.getMaxDamage()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        // getEntity() 可能为 null（如 JEI 搜索时）
        if (event.getEntity() == null) return;

        Holder.Reference<Enchantment> preservationEnchant = event.getEntity().level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(PRESERVATION_ID)
                .orElse(null);

        if (preservationEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = stack.getEnchantments().getLevel(preservationEnchant);
        if (level <= 0) return;

        // 如果耐久为最大值，显示已损坏提示
        if (stack.getDamageValue() >= stack.getMaxDamage()) {
            event.getToolTip().add(Component.translatable("tooltip.redstone_enchants.preservation_broken"));
        }
    }
}
