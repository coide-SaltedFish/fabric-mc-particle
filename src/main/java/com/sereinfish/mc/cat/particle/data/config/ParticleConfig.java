package com.sereinfish.mc.cat.particle.data.config;

import com.sereinfish.mc.cat.particle.Start;
import com.sereinfish.mc.cat.particle.untils.Utils;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;

public class ParticleConfig {
    private final HashMap<String, ParticleConfigPlayerData> map = new HashMap<>();//玩家配置

    public ParticleConfigPlayerData get(String uuid){
        return map.computeIfAbsent(uuid, ParticleConfigPlayerData::new);
    }

    public ParticleConfigPlayerData get(ServerPlayerEntity player){
        return get(player.getUuidAsString());
    }

    public void save(){
        try {
            Utils.fileWrite(Utils.ConfigFile, Utils.toJson(this));
        }catch (Exception e){
            Start.logger.error("配置保存错误", e);
        }
    }
}
