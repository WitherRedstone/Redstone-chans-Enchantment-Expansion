package com.chinaex123.redstone_enchants.event.all_fishing;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 导电鱼线：雷雨天时，勾住生物产生闪电
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class ConductiveLineEventHandler {
    private static final Identifier CONDUCTIVE_LINE_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "conductive_line");
    private static final Set<UUID> struckEntities = new HashSet<>();

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Pre event) {
        if (!(event.getEntity() instanceof FishingHook hook)) return;

        Player owner = hook.getPlayerOwner();
        if (owner == null) return;

        ItemStack rod = owner.getMainHandItem();
        if (rod.isEmpty() || !rod.is(Items.FISHING_ROD)) return;

        if (!owner.level().isThundering()) return;

        Holder.Reference<Enchantment> conductiveLineEnchant = owner.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(CONDUCTIVE_LINE_ID)
                .orElse(null);

        if (conductiveLineEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = rod.getEnchantments().getLevel(conductiveLineEnchant);
        if (level <= 0) return;

        // 检查是否勾住生物
        Entity hookedEntity = hook.getHookedIn();
        if (hookedEntity instanceof LivingEntity livingEntity) {
            UUID entityId = livingEntity.getUUID();

            // 防止重复召唤
            if (!struckEntities.contains(entityId)) {
                struckEntities.add(entityId);

                LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(livingEntity.level(), null);
                if (lightning != null) {
                    lightning.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                    livingEntity.level().addFreshEntity(lightning);
                }
            }
        } else {
            // 没有勾住时清除所有记录
            struckEntities.clear();
        }
    }
}
