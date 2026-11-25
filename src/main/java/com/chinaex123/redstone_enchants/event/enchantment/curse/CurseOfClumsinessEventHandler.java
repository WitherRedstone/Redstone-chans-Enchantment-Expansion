package com.chinaex123.redstone_enchants.event.enchantment.curse;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 笨拙诅咒：使用时有几率把主手物品脱手掉落。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class CurseOfClumsinessEventHandler {

    private static final String ID = "curse_of_clumsiness";

    /** 脱手概率。 */
    private static final float CHANCE = 0.4F;

    /** 掉落物不可拾取的时间（tick）。 */
    private static final int PICKUP_DELAY = 60;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!(attacker.level() instanceof ServerLevel level)) return;
        if (EnchantContext.level(attacker, ID) <= 0) return;
        if (level.getRandom().nextFloat() >= CHANCE) return;

        ItemStack main = attacker.getMainHandItem();
        if (main.isEmpty()) return;

        // 掉落的就是手上这件物品本身
        ItemStack drop = main.copy();
        Vec3 look = attacker.getLookAngle();
        ItemEntity itemEntity = new ItemEntity(level,
                attacker.getX() + look.x * 2.0D,
                attacker.getY() + 1.0D,
                attacker.getZ() + look.z * 2.0D,
                drop);
        itemEntity.setPickUpDelay(PICKUP_DELAY);
        level.addFreshEntity(itemEntity);

        attacker.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        attacker.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
    }
}
