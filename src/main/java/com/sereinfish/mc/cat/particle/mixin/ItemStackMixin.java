package com.sereinfish.mc.cat.particle.mixin;

import com.sereinfish.mc.cat.particle.thread.ParticleManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(at = @At("RETURN"), method = "useOnBlock(Lnet/minecraft/item/ItemUsageContext;)Lnet/minecraft/util/ActionResult;")
    public void useOnBlockMixin(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir){
        ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();
        if(player != null)
            ParticleManager.INSTANCE.update(player, null);
    }
}
