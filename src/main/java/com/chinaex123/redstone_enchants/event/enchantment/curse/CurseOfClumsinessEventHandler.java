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

    /** 附魔 ID。 */
    private static final String ID = "curse_of_clumsiness";

    /** 脱手概率。 */
    private static final float CHANCE = 0.4F;
    /** 掉落物不可拾取的时间（tick）。 */
    private static final int PICKUP_DELAY = 60;
    /** 掉落物在视线方向上的水平偏移距离。 */
    private static final double DROP_DISTANCE = 2.0D;
    /** 掉落物相对实体脚部的抬高距离。 */
    private static final double DROP_HEIGHT_OFFSET = 1.0D;
    /** 掉落音效音量。 */
    private static final float SOUND_VOLUME = 1.0F;
    /** 掉落音效音调。 */
    private static final float SOUND_PITCH = 1.0F;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!(attacker.level() instanceof ServerLevel serverLevel)) return;
        int level = EnchantContext.level(attacker, ID);
        if (level <= 0) return;
        if (serverLevel.getRandom().nextFloat() >= CHANCE) return;

        ItemStack main = attacker.getMainHandItem();
        if (main.isEmpty()) return;

        // 掉落的就是手上这件物品本身
        ItemStack drop = main.copy();
        Vec3 look = attacker.getLookAngle();
        ItemEntity itemEntity = new ItemEntity(serverLevel,
                attacker.getX() + look.x * DROP_DISTANCE,
                attacker.getY() + DROP_HEIGHT_OFFSET,
                attacker.getZ() + look.z * DROP_DISTANCE,
                drop);
        itemEntity.setPickUpDelay(PICKUP_DELAY);
        serverLevel.addFreshEntity(itemEntity);

        attacker.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        attacker.playSound(SoundEvents.ITEM_PICKUP, SOUND_VOLUME, SOUND_PITCH);
    }
}
