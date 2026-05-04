package com.chinaex123.redstone_enchants.event.armor_wolf;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 追踪者：狼攻击时，标记它攻击过的敌人
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class TrackerEventHandler {
    private static final Identifier TRACKER_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "tracker");

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        Entity directEntity = event.getSource().getDirectEntity();

        if (!(directEntity instanceof Wolf wolf)) return;

        LivingEntity target = event.getEntity();

        // 检查狼铠是否有追踪者附魔（狼铠在BODY槽位）
        ItemStack armor = wolf.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.BODY);
        if (armor.isEmpty()) return;

        Holder.Reference<Enchantment> trackerEnchant = wolf.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(TRACKER_ID)
                .orElse(null);

        if (trackerEnchant == null) return;

        @SuppressWarnings("deprecation")
        int enchantLevel = armor.getEnchantments().getLevel(trackerEnchant);
        if (enchantLevel <= 0) return;

        // 给目标施加发光效果
        int duration = 80 + (enchantLevel * 20);
        target.addEffect(new MobEffectInstance(
                MobEffects.GLOWING, duration, 0, false, false
        ));
    }
}
