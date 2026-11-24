package com.chinaex123.redstone_enchants.event.enchantment.horse_armor;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

/**
 * 牧场：马在草方块上时会自然恢复生命
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
@SuppressWarnings("deprecation")
public class PastureEventHandler {
    private static final ResourceLocation PASTURE_ID = RedstoneEnchants.id("pasture");

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof AbstractHorse horse)) return;

        // 检查马是否有鞍（被骑乘）
        if (!horse.isSaddled()) return;

        // 检查马铠是否有牧场附魔
        ItemStack armor = horse.getItemBySlot(EquipmentSlot.BODY);
        if (armor.isEmpty()) return;

        Holder.Reference<Enchantment> pastureEnchant = horse.level()
                .registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(PASTURE_ID)
                .orElse(null);

        if (pastureEnchant == null) return;

        int enchantLevel = armor.getEnchantments().getLevel(pastureEnchant);
        if (enchantLevel <= 0) return;

        // 检查马是否在草方块上
        BlockPos pos = horse.blockPosition();
        BlockPos belowPos = pos.below();

        if (horse.level().getBlockState(belowPos).is(Blocks.GRASS_BLOCK)) {
            if (horse.tickCount % 20 == 0) {
                // 每级恢复0.5点生命值
                horse.heal(enchantLevel * 0.5f);
            }
        }
    }
}
