package com.chinaex123.redstone_enchants.event.enchantment.wolf_armor;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

/**
 * 尸体回收：狼击杀生物后回复最大生命值
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class CarrionEaterEventHandler {
    private static final ResourceLocation CARRION_EATER_ID = RedstoneEnchants.id("carrion_eater");

    /** 1 级时回复的最大生命值比例 */
    private static final float HEAL_RATIO_BASE = 0.25F;
    /** 每级额外增加的比例 */
    private static final float HEAL_RATIO_PER_LEVEL = 0.25F;

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getDirectEntity() instanceof Wolf wolf)) return;

        // 检查狼是否有主人
        if (!wolf.isTame()) return;

        // 检查狼铠是否有尸体回收附魔
        ItemStack armor = wolf.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.BODY);
        if (armor.isEmpty()) return;

        ResourceKey<Enchantment> carrionEaterEnchant = EnchantContext.key(CARRION_EATER_ID);

        int enchantLevel = EnchantContext.level(armor, carrionEaterEnchant);
        if (enchantLevel <= 0) return;

        // 计算治疗量：每级回复一定比例的最大生命值
        float maxHealth = wolf.getMaxHealth();
        float healRatio = HEAL_RATIO_BASE + HEAL_RATIO_PER_LEVEL * (enchantLevel - 1);
        float healAmount = maxHealth * healRatio;

        // 应用治疗
        wolf.heal(healAmount);
    }
}
