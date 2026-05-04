package com.chinaex123.redstone_enchants.event.all_shear;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.Objects;

/**
 * 绵延不绝：剪羊毛时，有概率让羊立刻重新长出羊毛
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class EndlessWoolEventHandler {
    private static final Identifier ENDLESS_WOOL_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "endless_wool");

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Level level = player.level();

        ItemStack stack = event.getItemStack();
        Entity target = event.getTarget();

        // 检查是否是剪刀剪羊毛
        if (!(target instanceof Sheep sheep)) return;
        if (!stack.is(Items.SHEARS)) return;

        // 只在服务端处理逻辑
        if (level.isClientSide()) return;

        // 检查羊是否有羊毛（未被剪过）
        if (sheep.isSheared()) return;

        Holder.Reference<Enchantment> endlessWoolEnchant = level
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(ENDLESS_WOOL_ID)
                .orElse(null);

        if (endlessWoolEnchant == null) return;

        @SuppressWarnings("deprecation")
        int enchantLevel = stack.getEnchantments().getLevel(endlessWoolEnchant);
        if (enchantLevel <= 0) return;

        // 每级10%概率让羊重新长出羊毛
        RandomSource random = level.getRandom();
        double probability = enchantLevel * 0.10;

        if (probability >= 1.0 || random.nextDouble() < probability) {
            ServerLevel serverLevel = (ServerLevel) level;

            int currentTick = serverLevel.getServer().getTickCount();
            serverLevel.getServer().schedule(new net.minecraft.server.TickTask(currentTick + 1, () -> {
                sheep.setSheared(false);

                serverLevel.sendParticles(
                        ParticleTypes.HAPPY_VILLAGER,
                        sheep.getX(), sheep.getY() + 1, sheep.getZ(),
                        10, 0.5, 0.5, 0.5, 0.1
                );
            }));
        }
    }
}
