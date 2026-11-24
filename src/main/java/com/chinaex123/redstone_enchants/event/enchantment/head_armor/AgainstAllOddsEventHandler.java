package com.chinaex123.redstone_enchants.event.enchantment.head_armor;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.List;

/**
 * 以寡敌众：根据周围敌人数目，每多一个敌人伤害/护甲+2%
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
@SuppressWarnings("deprecation")
public class AgainstAllOddsEventHandler {
    private static final ResourceLocation AGAINST_ALL_ODDS_ID = RedstoneEnchants.id("against_all_odds");
    private static final ResourceLocation DAMAGE_MODIFIER_ID = RedstoneEnchants.id("against_all_odds_damage");
    private static final ResourceLocation ARMOR_MODIFIER_ID = RedstoneEnchants.id("against_all_odds_armor");

    /** 探测半径 */
    private static final double DETECTION_RANGE = 8.0;

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;

        // 检查头盔是否有以寡敌众附魔
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.isEmpty()) return;

        Holder.Reference<Enchantment> againstAllOddsEnchant = player.level()
                .registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(AGAINST_ALL_ODDS_ID)
                .orElse(null);

        if (againstAllOddsEnchant == null) return;

        int enchantLevel = helmet.getEnchantments().getLevel(againstAllOddsEnchant);
        if (enchantLevel <= 0) return;

        // 获取范围内的敌对生物数量
        List<Monster> nearbyEnemies = player.level().getEntitiesOfClass(
                Monster.class,
                player.getBoundingBox().inflate(DETECTION_RANGE)
        );
        int enemyCount = nearbyEnemies.size();

        // 获取属性
        var attackDamageAttribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
        var armorAttribute = player.getAttribute(Attributes.ARMOR);

        if (attackDamageAttribute != null && armorAttribute != null) {
            // 移除旧的修饰符
            attackDamageAttribute.removeModifier(DAMAGE_MODIFIER_ID);
            armorAttribute.removeModifier(ARMOR_MODIFIER_ID);

            if (enemyCount > 0) {
                // 每多一个敌人，伤害/护甲+2%
                double bonus = enemyCount * 0.02 * enchantLevel;

                AttributeModifier damageModifier = new AttributeModifier(
                        DAMAGE_MODIFIER_ID,
                        bonus,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                );
                attackDamageAttribute.addPermanentModifier(damageModifier);

                AttributeModifier armorModifier = new AttributeModifier(
                        ARMOR_MODIFIER_ID,
                        bonus,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                );
                armorAttribute.addPermanentModifier(armorModifier);
            }
        }
    }

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        // 只处理头部槽位的变化
        if (event.getSlot() != EquipmentSlot.HEAD) return;

        // 移除属性加成
        var attackDamageAttribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
        var armorAttribute = player.getAttribute(Attributes.ARMOR);

        if (attackDamageAttribute != null) {
            attackDamageAttribute.removeModifier(DAMAGE_MODIFIER_ID);
        }
        if (armorAttribute != null) {
            armorAttribute.removeModifier(ARMOR_MODIFIER_ID);
        }
    }
}
