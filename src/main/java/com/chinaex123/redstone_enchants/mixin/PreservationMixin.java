package com.chinaex123.redstone_enchants.mixin;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import com.chinaex123.redstone_enchants.util.PreservationFeedback;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 保全：装备不会因无耐久而损坏消失。
 * <p>
 * 耐久最多消耗到 0，之后不再掉耐久、也不会损坏；第一次到达 0 耐久时播放一次损坏音效与碎屑粒子
 */
@Mixin(ItemStack.class)
@SuppressWarnings("deprecation")
public class PreservationMixin {
    @Unique
    private static final ResourceLocation PRESERVATION_ID = RedstoneEnchants.id("preservation");

    @Inject(method = "hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)V", at = @At("HEAD"), cancellable = true)
    public void preservation_preventBreak(int amount, LivingEntity entity, EquipmentSlot slot, CallbackInfo ci) {
        ItemStack stack = (ItemStack) (Object) this;

        if (entity == null) return;
        if (!stack.isDamageableItem()) return;

        Holder.Reference<Enchantment> preservationEnchant = entity.level()
                .registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(PRESERVATION_ID)
                .orElse(null);

        if (preservationEnchant == null) return;

        int level = stack.getEnchantments().getLevel(preservationEnchant);
        if (level <= 0) return;

        int maxDamage = stack.getMaxDamage();
        if (maxDamage <= 0) return;

        if (stack.getDamageValue() + amount < maxDamage) {
            // 还有耐久：被修好过就清掉标记，下次耗尽能再提示一次
            PreservationFeedback.clearWarned(stack);
            return;
        }

        // 耐久耗尽：固定在 0 耐久并拦截，物品不会损坏
        stack.setDamageValue(maxDamage);
        PreservationFeedback.warnIfNeeded(stack, entity);
        ci.cancel();
    }
}
