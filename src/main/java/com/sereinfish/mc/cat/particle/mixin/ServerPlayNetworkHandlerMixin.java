package com.sereinfish.mc.cat.particle.mixin;

import com.sereinfish.mc.cat.particle.thread.ParticleManager;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Inject(at = @At("RETURN"), method = "onUpdateSelectedSlot(Lnet/minecraft/network/packet/c2s/play/UpdateSelectedSlotC2SPacket;)V")
    public void onUpdateSelectedSlotMixin(UpdateSelectedSlotC2SPacket packet, CallbackInfo ci){
        ParticleManager.INSTANCE.update(player, null);
    }

    @Inject(at = @At("RETURN"), method = "onPlayerAction(Lnet/minecraft/network/packet/c2s/play/PlayerActionC2SPacket;)V")
    public void onPlayerActionMixin(PlayerActionC2SPacket packet, CallbackInfo ci){
        ParticleManager.INSTANCE.update(player, null);
    }

    @Inject(at = @At("RETURN"), method = "onPlayerInteractEntity(Lnet/minecraft/network/packet/c2s/play/PlayerInteractEntityC2SPacket;)V")
    public void onPlayerInteractEntityMixin(PlayerInteractEntityC2SPacket packet, CallbackInfo ci){
        ParticleManager.INSTANCE.update(player, null);
    }
}
