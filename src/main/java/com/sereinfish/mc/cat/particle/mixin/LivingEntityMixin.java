package com.sereinfish.mc.cat.particle.mixin;

import com.sereinfish.mc.cat.particle.thread.ParticleManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(at = @At("HEAD"), method = "setStackInHand(Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;)V")
    public void setStackInHandMixin(Hand hand, ItemStack stack, CallbackInfo ci){
        if (ServerPlayerEntity.class.isAssignableFrom(this.getClass())) {
            ParticleManager.INSTANCE.update(ServerPlayerEntity.class.cast(this), null);
        }
    }
}
