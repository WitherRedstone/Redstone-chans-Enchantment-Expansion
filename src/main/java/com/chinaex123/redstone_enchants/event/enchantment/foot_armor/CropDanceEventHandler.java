package com.chinaex123.redstone_enchants.event.enchantment.foot_armor;

import net.minecraft.resources.ResourceKey;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 庄稼舞：蹲下时走过农田会加速周围作物生长
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class CropDanceEventHandler {
    private static final ResourceLocation CROP_DANCE_ID = RedstoneEnchants.id("crop_dance");

    /** 生效范围 */
    private static final int GROWTH_RANGE = 3;
    /** 基础生长概率 */
    private static final double BASE_GROWTH_CHANCE = 0.1;
    /** 每级增加 */
    private static final double GROWTH_INCREMENT = 0.1;
    /** 生长概率上限 */
    private static final double MAX_GROWTH_CHANCE = 0.99;
    /** 催熟粒子数量 */
    private static final int PARTICLE_COUNT = 5;
    /** 催熟粒子扩散范围 */
    private static final double PARTICLE_SPREAD = 0.3;
    /** 催熟粒子速度 */
    private static final double PARTICLE_SPEED = 0.02;
    /** 方块中心偏移 */
    private static final double BLOCK_CENTER_OFFSET = 0.5;

    // 记录每个玩家上一次的潜行状态
    private static final Map<Player, Boolean> lastSneakingMap = new ConcurrentHashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!(player.level() instanceof ServerLevel serverLevel)) return;

        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        if (boots.isEmpty()) return;

        ResourceKey<Enchantment> cropDanceEnchant = EnchantContext.key(CROP_DANCE_ID);

        int level = EnchantContext.level(boots, cropDanceEnchant);
        if (level <= 0) return;

        boolean isSneaking = player.isShiftKeyDown();
        boolean lastSneaking = lastSneakingMap.getOrDefault(player, false);

        // 检查是否刚刚开始潜行
        if (isSneaking && !lastSneaking) {
            // 执行一次催熟
            executeCropGrowth(player, serverLevel, level);
        }

        // 更新潜行状态记录
        lastSneakingMap.put(player, isSneaking);
    }

    private static void executeCropGrowth(Player player, ServerLevel serverLevel, int enchantLevel) {
        BlockPos playerPos = player.blockPosition();
        int actualRange = GROWTH_RANGE + enchantLevel;
        AABB area = new AABB(playerPos).inflate(actualRange);

        // 计算实际生长概率
        double growthChance = Math.min(MAX_GROWTH_CHANCE, BASE_GROWTH_CHANCE + enchantLevel * GROWTH_INCREMENT);

        BlockPos.betweenClosedStream(area).forEach(pos -> {
            BlockState state = serverLevel.getBlockState(pos);

            if (state.getBlock() instanceof BonemealableBlock bonemealable) {
                // 使用百分比判断
                if (RedstoneEnchants.RANDOM.nextDouble() < growthChance) {
                    if (bonemealable.isValidBonemealTarget(serverLevel, pos, state)) {
                        bonemealable.performBonemeal(serverLevel, serverLevel.random, pos, state);

                        double particleX = pos.getX() + BLOCK_CENTER_OFFSET;
                        double particleY = pos.getY() + BLOCK_CENTER_OFFSET;
                        double particleZ = pos.getZ() + BLOCK_CENTER_OFFSET;
                        serverLevel.sendParticles(
                                ParticleTypes.HAPPY_VILLAGER,
                                particleX, particleY, particleZ,
                                PARTICLE_COUNT,
                                PARTICLE_SPREAD, PARTICLE_SPREAD, PARTICLE_SPREAD,
                                PARTICLE_SPEED
                        );
                    }
                }
            }
        });
    }
}
