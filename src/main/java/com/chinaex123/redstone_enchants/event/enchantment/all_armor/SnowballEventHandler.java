package com.chinaex123.redstone_enchants.event.enchantment.all_armor;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.event.EnchantContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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

    private static final String SNOWBALL = "{id:\"minecraft:snowball\",Item:{id:\"minecraft:snowball\",count:1},"
            + "pickup:2b,player:1b,life:600,damage:1.0d,crit:1b}";

    /** 雪球搜寻目标的半径。 */
    private static final double SEARCH_RADIUS = 16.0D;

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        LivingEntity victim = event.getEntity();
        if (!(victim.level() instanceof ServerLevel level)) return;

        int enchantLevel = EnchantContext.level(victim, ID);
        if (enchantLevel <= 0) return;
        if (level.getRandom().nextFloat() >= enchantLevel * 0.3F) return;

        EnchantContext context = new EnchantContext(level, victim, victim.position());
        Entity target = context.nearestEntity(0.0D, SEARCH_RADIUS, false,
                candidate -> !(candidate instanceof Player) && !context.isNonLiving(candidate));

        Vec3 spawn = victim.position().add(0.0D, 1.0D, 0.0D);
        Entity snowball = context.summon(SNOWBALL);
        if (snowball == null) return;

        snowball.setPos(spawn.x, spawn.y, spawn.z);
        if (target != null) {
            Vec3 direction = target.position().add(0.0D, 1.0D, 0.0D).subtract(spawn);
            if (direction.lengthSqr() > 1.0E-6D) {
                context.motion(snowball, direction.normalize().scale(0.5D));
            }
        }
        context.soundMaster(SoundEvents.SNOWBALL_THROW, 1.0F, 1.0F);
    }
}
