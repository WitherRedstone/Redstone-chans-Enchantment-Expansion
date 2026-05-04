package com.chinaex123.redstone_enchants.event.curse;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 爆炸诅咒：受伤时在装备者身上触发爆炸
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class CurseOfBlastEventHandler {
    private static final Identifier CURSE_OF_BLAST_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "curse_of_blast");

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        LivingEntity victim = event.getEntity();

        boolean hasCurse = false;
        EquipmentSlot[] armorSlots = {
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET
        };

        for (EquipmentSlot slot : armorSlots) {
            ItemStack armor = victim.getItemBySlot(slot);
            if (armor.isEmpty()) continue;

            Holder.Reference<Enchantment> curseEnchant = victim.level()
                    .registryAccess()
                    .lookupOrThrow(Registries.ENCHANTMENT)
                    .get(CURSE_OF_BLAST_ID)
                    .orElse(null);

            if (curseEnchant == null) continue;

            @SuppressWarnings("deprecation")
            int level = armor.getEnchantments().getLevel(curseEnchant);
            if (level > 0) {
                hasCurse = true;
                break;
            }
        }

        if (!hasCurse) return;

        if (victim.getRandom().nextFloat() < 0.3f) {
            victim.level().explode(null, victim.getX(), victim.getY() + 1, victim.getZ(),
                    3.0F, false, Level.ExplosionInteraction.TNT
            );
        }
    }
}
