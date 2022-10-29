package com.sereinfish.mc.cat.particle.event;

import com.sereinfish.mc.cat.particle.Start;
import com.sereinfish.mc.cat.particle.thread.ParticleManager;
import com.sereinfish.mc.cat.particle.untils.Utils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class ModEvents {
    private ModEvents(){

    }

    public static void init(){
        //玩家登入事件
        ServerPlayConnectionEvents.JOIN.register((handler, sender, minecraftServer)->{
            try {
                Start.logger.info("玩家：[" + handler.player.getEntityName() + "(" + handler.player.getIp() + ")(" + handler.player.getUuidAsString() + "})]登入游戏");
                ParticleManager.INSTANCE.add(handler.player);
            }catch (Exception e){
                Start.logger.error("在登入事件", e);
            }
        });
        //玩家离线事件
        ServerPlayConnectionEvents.DISCONNECT.register((handler, minecraftServer)->{
            try {
                Start.logger.info("玩家：[" + handler.player.getEntityName() + "(" + handler.player.getIp() + ")(" + handler.player.getUuidAsString() + "})]离开游戏");
                ParticleManager.INSTANCE.remove(handler.player);
            }catch (Exception e){
                Start.logger.error("在登出事件", e);
            }
        });
        //服务器关闭事件
        ServerLifecycleEvents.SERVER_STOPPING.register((server)->{
            //关闭所有粒子效果
            try {
                ParticleManager.INSTANCE.close();
            }catch (Exception e){
                Start.logger.error("在关闭所有粒子效果时出现异常", e);
            }
            //保存配置
            try {
                Utils.particleConfig.save();
                Start.logger.info("配置保存完成");
            }catch (Exception e){
                Start.logger.error("配置保存时出现异常", e);
            }
        });
    }
}
