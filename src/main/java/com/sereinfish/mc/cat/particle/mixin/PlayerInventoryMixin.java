package com.sereinfish.mc.cat.particle.mixin;

import com.sereinfish.mc.cat.particle.thread.ParticleManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {
    @Shadow
    @Final
    public PlayerEntity player;

    @Inject(at = @At("RETURN"), method = "setStack(ILnet/minecraft/item/ItemStack;)V")
    public void setStackMixin(int slot, ItemStack stack, CallbackInfo ci){
        ParticleManager.INSTANCE.update((ServerPlayerEntity) player, null);
    }

    @Inject(at = @At("RETURN"), method = "removeStack(I)Lnet/minecraft/item/ItemStack;")
    public void removeStackMixin(int slot, CallbackInfoReturnable<ItemStack> cir){
        ParticleManager.INSTANCE.update((ServerPlayerEntity) player, null);
    }

    @Inject(at = @At("RETURN"), method = "addStack(ILnet/minecraft/item/ItemStack;)I")
    public void addStackMixin(int slot, ItemStack stack, CallbackInfoReturnable<Integer> cir){
        ParticleManager.INSTANCE.update((ServerPlayerEntity) player, null);
    }
}
