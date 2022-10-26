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
        ServerPlayConnectionEvents.JOIN.register((hander, sender, minecraftServer)->{
            Start.logger.info("玩家：[" + hander.player.getEntityName() + "(" + hander.player.getIp() + ")(" + hander.player.getUuidAsString() + "})]登入游戏");
            ParticleManager.INSTANCE.add(hander.player);
        });
        //玩家离线事件
        ServerPlayConnectionEvents.DISCONNECT.register((hander, minecraftServer)->{
            Start.logger.info("玩家：[" + hander.player.getEntityName() + "(" + hander.player.getIp() + ")(" + hander.player.getUuidAsString() + "})]离开游戏");
            ParticleManager.INSTANCE.remove(hander.player);
        });
        //服务器关闭事件
        ServerLifecycleEvents.SERVER_STOPPING.register((server)->{
            //关闭所有粒子效果
            ParticleManager.INSTANCE.close();
            //保存配置
            Utils.particleConfig.save();
            Start.logger.info("配置保存完成");
        });
    }
}
