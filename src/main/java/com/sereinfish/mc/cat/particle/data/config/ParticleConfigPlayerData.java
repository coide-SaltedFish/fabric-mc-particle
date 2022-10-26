package com.sereinfish.mc.cat.particle.data.config;

public class ParticleConfigPlayerData {
    public String uuid; //玩家标识
    public Boolean enable = true;//全局开关
    public Boolean otherEnable = true;//显示其他玩家特效
    public Boolean onlyOwnEnable = false;//只显示给自己看
    public Boolean onlyOtherEnable = false;//关闭自己的特效
    public Boolean prefixEnable = true;//启用自己的前缀
    public Boolean suffixEnable = true;//启用自己的后缀

    public ParticleConfigPlayerData(String uuid) {
        this.uuid = uuid;
    }
}
