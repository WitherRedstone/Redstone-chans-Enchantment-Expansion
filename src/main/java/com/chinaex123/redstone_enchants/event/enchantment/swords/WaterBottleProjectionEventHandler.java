package com.chinaex123.redstone_enchants.event.enchantment.swords;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 水瓶投掷：攻击时向目标投掷水瓶。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class WaterBottleProjectionEventHandler {

    private static final String ID = "water_bottle_projection";

    /** 1 级时投掷的水瓶数量 */
    private static final int COUNT_BASE = 1;
    /** 每级额外增加的投掷数量 */
    private static final int COUNT_PER_LEVEL = 1;
    /** 投掷数量上限 */
    private static final int MAX_COUNT = 2;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!(attacker.level() instanceof ServerLevel level)) return;
        if (event.getSource().getDirectEntity() != attacker) return;

        int enchantLevel = EnchantContext.level(attacker, ID);
        if (enchantLevel <= 0) return;

        int count = (int) Math.min(MAX_COUNT, COUNT_BASE + COUNT_PER_LEVEL * (enchantLevel - 1));
        EnchantContext context = new EnchantContext(level, event.getEntity(), event.getEntity().position());
        for (int index = 0; index < count; index++) {
            ThrownPotion potion = new ThrownPotion(level, context.pos().x, context.pos().y, context.pos().z);
            ItemStack stack = new ItemStack(Items.SPLASH_POTION);
            stack.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER));
            potion.setItem(stack);
            context.spawn(potion);
        }
    }
}
