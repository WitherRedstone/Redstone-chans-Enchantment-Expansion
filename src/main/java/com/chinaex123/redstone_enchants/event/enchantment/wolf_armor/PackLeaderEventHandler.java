package com.chinaex123.redstone_enchants.event.enchantment.wolf_armor;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;

/**
 * 狼群领袖：附近每多一只狼，增加你狼的伤害
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class PackLeaderEventHandler {
    private static final ResourceLocation PACK_LEADER_ID = RedstoneEnchants.id("pack_leader");

    /** 每级每只狼增加的伤害 */
    private static final double DAMAGE_BONUS_PER_WOLF_PER_LEVEL = 0.5;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getDirectEntity() instanceof Wolf wolf)) return;

        // 检查狼是否有主人
        if (!wolf.isTame()) return;

        Player owner = (Player) wolf.getOwner();
        if (owner == null) return;

        // 检查狼铠是否有狼群领袖附魔
        ItemStack armor = wolf.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.BODY);
        if (armor.isEmpty()) return;

        ResourceKey<Enchantment> packLeaderEnchant = EnchantContext.key(PACK_LEADER_ID);

        int enchantLevel = EnchantContext.level(armor, packLeaderEnchant);
        if (enchantLevel <= 0) return;

        // 计算附近的狼数量（16格范围内）
        double range = 16.0;
        List<Wolf> nearbyWolves = wolf.level().getEntitiesOfClass(Wolf.class, wolf.getBoundingBox().inflate(range));

        // 排除自己，计算其他狼的数量
        int wolfCount = nearbyWolves.size() - 1;
        if (wolfCount <= 0) return;

        // 计算伤害加成：每级每只狼+5%
        double damageBonus = wolfCount * enchantLevel * DAMAGE_BONUS_PER_WOLF_PER_LEVEL;

        // 应用伤害加成
        event.setNewDamage((float) (event.getNewDamage() * (1 + damageBonus)));
    }
}
