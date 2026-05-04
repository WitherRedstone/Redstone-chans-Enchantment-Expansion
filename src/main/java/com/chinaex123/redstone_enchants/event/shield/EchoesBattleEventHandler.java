package com.chinaex123.redstone_enchants.event.shield;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

/**
 * 战斗回响：盾牌格挡后，提升下一次攻击的伤害
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class EchoesBattleEventHandler {
    private static final Identifier ECHOES_BATTLE_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "echoes_battle");
    private static final Identifier ECHOES_BATTLE_MODIFIER_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "echoes_battle_bonus");

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();

        // 检测玩家格挡（伤害被盾牌减免时触发）
        if (target instanceof Player player && player.isBlocking()) {
            ItemStack shield = player.getUseItem();
            if (!shield.isEmpty()) {
                Holder.Reference<Enchantment> echoesBattleEnchant = player.level()
                        .registryAccess()
                        .lookupOrThrow(Registries.ENCHANTMENT)
                        .get(ECHOES_BATTLE_ID)
                        .orElse(null);

                if (echoesBattleEnchant != null) {
                    @SuppressWarnings("deprecation")
                    int level = shield.getEnchantments().getLevel(echoesBattleEnchant);
                    if (level > 0) {
                        applyAttackDamageBonus(player, level);
                        return;
                    }
                }
            }
        }

        // 检测玩家攻击，移除属性加成
        if (event.getSource().getDirectEntity() instanceof Player attacker) {
            removeAttackDamageBonus(attacker);
        }
    }

    private static void applyAttackDamageBonus(Player player, int level) {
        AttributeInstance attribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attribute == null) return;

        attribute.removeModifier(ECHOES_BATTLE_MODIFIER_ID);

        double bonus = level * 0.2;
        AttributeModifier modifier = new AttributeModifier(
                Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "echoes_battle_modifier"),
                bonus,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
        attribute.addTransientModifier(modifier);
    }

    private static void removeAttackDamageBonus(Player player) {
        AttributeInstance attribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attribute == null) return;

        attribute.removeModifier(ECHOES_BATTLE_MODIFIER_ID);
    }
}
