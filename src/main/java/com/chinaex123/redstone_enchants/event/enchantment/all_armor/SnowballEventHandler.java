package com.chinaex123.redstone_enchants.event.enchantment.all_armor;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 雪球：佩戴时受伤会概率向攻击者扔雪球。
 */
@EventBusSubscriber(modid = RedstoneEnchants.MODID)
public class SnowballEventHandler {

    private static final String ID = "snowball";

    /** 1 级时的触发概率 */
    private static final double CHANCE_BASE = 0.3D;
    /** 每级额外增加的概率 */
    private static final double CHANCE_PER_LEVEL = 0.3D;
    /** 雪球搜寻目标的半径。 */
    private static final double SEARCH_RADIUS = 16.0D;
    /** 雪球生成位置相对受伤者向上的偏移 */
    private static final double SPAWN_Y_OFFSET = 1.0D;
    /** 目标位置相对实体向上的偏移 */
    private static final double TARGET_Y_OFFSET = 1.0D;
    /** 方向向量的最小平方长度，用于避免除零 */
    private static final double MIN_DIRECTION_LENGTH_SQR = 1.0E-6D;
    /** 雪球初速度 */
    private static final double SNOWBALL_SPEED = 0.5D;
    /** 搜寻目标时的最小距离（不设下限） */
    private static final double MIN_SEARCH_DISTANCE = 0.0D;
    /** 生成位置与瞄准位置的水平偏移（格，不做水平偏移） */
    private static final double HORIZONTAL_OFFSET = 0.0D;
    /** 投掷音效音量 */
    private static final float SOUND_VOLUME = 1.0F;
    /** 投掷音效音调 */
    private static final float SOUND_PITCH = 1.0F;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        LivingEntity victim = event.getEntity();
        if (!(victim.level() instanceof ServerLevel level)) return;

        int enchantLevel = EnchantContext.level(victim, ID);
        if (enchantLevel <= 0) return;
        double chance = Math.min(1.0D, CHANCE_BASE + CHANCE_PER_LEVEL * (enchantLevel - 1));
        if (level.getRandom().nextFloat() >= chance) return;

        EnchantContext context = new EnchantContext(level, victim, victim.position());
        Entity target = context.nearestEntity(MIN_SEARCH_DISTANCE, SEARCH_RADIUS, false,
                candidate -> !(candidate instanceof Player) && !context.isNonLiving(candidate));

        Vec3 spawn = victim.position().add(HORIZONTAL_OFFSET, SPAWN_Y_OFFSET, HORIZONTAL_OFFSET);
        Snowball snowball = new Snowball(level, spawn.x, spawn.y, spawn.z);
        snowball.setItem(new ItemStack(Items.SNOWBALL));
        context.spawn(snowball);

        if (target != null) {
            Vec3 direction = target.position().add(HORIZONTAL_OFFSET, TARGET_Y_OFFSET, HORIZONTAL_OFFSET).subtract(spawn);
            if (direction.lengthSqr() > MIN_DIRECTION_LENGTH_SQR) {
                context.motion(snowball, direction.normalize().scale(SNOWBALL_SPEED));
            }
        }
        context.soundMaster(SoundEvents.SNOWBALL_THROW, SOUND_VOLUME, SOUND_PITCH);
    }
}
