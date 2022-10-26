package com.sereinfish.mc.cat.particle.mixin;

import com.sereinfish.mc.cat.particle.thread.ParticleManager;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(at = @At("RETURN"), method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;")
    public void dropItemMixin(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir){
        if (ServerPlayerEntity.class.isAssignableFrom(this.getClass())){
            ParticleManager.INSTANCE.update(ServerPlayerEntity.class.cast(this), EquipmentSlot.MAINHAND);
        }
    }

    @Inject(at = @At("RETURN"), method = "equipStack(Lnet/minecraft/entity/EquipmentSlot;Lnet/minecraft/item/ItemStack;)V")
    public void equipStackMixin(EquipmentSlot slot, ItemStack stack, CallbackInfo ci){
        if (ServerPlayerEntity.class.isAssignableFrom(this.getClass())) {
            ParticleManager.INSTANCE.update(ServerPlayerEntity.class.cast(this), null);
        }
    }
}
