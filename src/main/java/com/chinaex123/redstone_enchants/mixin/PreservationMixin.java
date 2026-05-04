package com.chinaex123.redstone_enchants.mixin;

import com.chinaex123.redstone_enchants.RedstoneEnchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
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
 * 附魔 - 保全
 */
@Mixin(ItemStack.class)
public class PreservationMixin {
    @Unique
    private static final Identifier PRESERVATION_KEY = Identifier.fromNamespaceAndPath(RedstoneEnchants.MOD_ID, "preservation");

    @Inject(method = "hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)V", at = @At("HEAD"), cancellable = true)
    public void preservation_preventBreak(int amount, LivingEntity entity, EquipmentSlot slot, CallbackInfo ci) {
        ItemStack stack = (ItemStack) (Object) this;

        Holder.Reference<Enchantment> preservationEnchant = entity.level()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(PRESERVATION_KEY)
                .orElse(null);

        if (preservationEnchant == null) return;

        @SuppressWarnings("deprecation")
        int level = stack.getEnchantments().getLevel(preservationEnchant);
        if (level <= 0) return;

        // 检查是否会损坏
        int newDamage = stack.getDamageValue() + amount;
        if (newDamage >= stack.getMaxDamage()) {
            // 阻止损坏，设置耐久为最大值（显示为红色损坏状态，无法使用）
            stack.setDamageValue(stack.getMaxDamage());
            ci.cancel();
        }
    }
}
