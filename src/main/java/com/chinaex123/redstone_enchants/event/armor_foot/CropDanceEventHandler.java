package com.chinaex123.redstone_enchants.event.armor_foot;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 庄稼舞：蹲下时走过农田会加速周围作物生长
 */
@EventBusSubscriber(modid = RedstoneEnchants.MOD_ID)
public class CropDanceEventHandler {
    private static final Identifier CROP_DANCE_ID = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "crop_dance");
    private static final Random RANDOM = new Random();

    private static final int GROWTH_RANGE = 3; // 生效范围
    private static final double BASE_GROWTH_CHANCE = 0.1; // 基础生长概率 10%
    private static final double GROWTH_INCREMENT = 0.1; // 每级增加

    // 记录每个玩家上一次的潜行状态
    private static final Map<Player, Boolean> lastSneakingMap = new ConcurrentHashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!(player.level() instanceof ServerLevel serverLevel)) return;

        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        if (boots.isEmpty()) return;

        Holder.Reference<Enchantment> cropDanceEnchant = player.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(CROP_DANCE_ID)
                .orElse(null);

        if (cropDanceEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = boots.getEnchantments().getLevel(cropDanceEnchant);
        if (level <= 0) return;

        boolean isSneaking = player.isShiftKeyDown();
        boolean lastSneaking = lastSneakingMap.getOrDefault(player, false);

        // 检查是否刚刚开始潜行（从非潜行状态变为潜行状态）
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
        double growthChance = Math.min(0.99, BASE_GROWTH_CHANCE + enchantLevel * GROWTH_INCREMENT);

        BlockPos.betweenClosedStream(area).forEach(pos -> {
            BlockState state = serverLevel.getBlockState(pos);

            if (state.getBlock() instanceof BonemealableBlock bonemealable) {
                // 使用百分比判断
                if (RANDOM.nextDouble() < growthChance) {
                    if (bonemealable.isValidBonemealTarget(serverLevel, pos, state)) {
                        bonemealable.performBonemeal(serverLevel, serverLevel.getRandom(), pos, state);

                        double particleX = pos.getX() + 0.5;
                        double particleY = pos.getY() + 0.5;
                        double particleZ = pos.getZ() + 0.5;
                        serverLevel.sendParticles(
                                ParticleTypes.HAPPY_VILLAGER,
                                particleX, particleY, particleZ,
                                5,
                                0.3, 0.3, 0.3,
                                0.02
                        );
                    }
                }
            }
        });
    }
}