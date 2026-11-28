package com.chinaex123.redstone_enchants.event.enchantment.all_shear;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Sheep;
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
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class EndlessWoolEventHandler {
    private static final ResourceLocation ENDLESS_WOOL_ID = RedstoneEnchants.id("endless_wool");

    /** 1 级时让羊重新长出羊毛的概率 */
    private static final double REGROW_CHANCE_BASE = 0.10D;
    /** 每级额外增加的概率 */
    private static final double REGROW_CHANCE_PER_LEVEL = 0.10D;
    /** 概率上限 */
    private static final double REGROW_CHANCE_MAX = 1.0D;
    /** 粒子数量 */
    private static final int PARTICLE_COUNT = 10;
    /** 粒子生成位置相对羊身的高度偏移 */
    private static final double PARTICLE_Y_OFFSET = 1.0D;
    /** 粒子散布范围 */
    private static final double PARTICLE_SPREAD = 0.5D;
    /** 粒子速度 */
    private static final double PARTICLE_SPEED = 0.1D;

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

        ResourceKey<Enchantment> endlessWoolEnchant = EnchantContext.key(ENDLESS_WOOL_ID);

        int enchantLevel = EnchantContext.level(stack, endlessWoolEnchant);
        if (enchantLevel <= 0) return;

        // 1 级 10% 概率让羊重新长出羊毛，每级再增加 10%
        RandomSource random = level.getRandom();
        double probability = Math.min(REGROW_CHANCE_MAX,
                REGROW_CHANCE_BASE + REGROW_CHANCE_PER_LEVEL * (enchantLevel - 1));

        if (probability >= REGROW_CHANCE_MAX || random.nextDouble() < probability) {
            // 延迟执行，确保剪切完成后再生长的
            Objects.requireNonNull(level.getServer()).execute(() -> {
                sheep.setSheared(false);

                // 发送数据包让客户端显示粒子
                ((ServerLevel) level).sendParticles(
                        ParticleTypes.HAPPY_VILLAGER,
                        sheep.getX(), sheep.getY() + PARTICLE_Y_OFFSET, sheep.getZ(),
                        PARTICLE_COUNT, PARTICLE_SPREAD, PARTICLE_SPREAD, PARTICLE_SPREAD, PARTICLE_SPEED
                );
            });
        }
    }
}
