package com.sereinfish.mc.cat.particle.data;

import com.sereinfish.mc.cat.particle.Start;
import com.sereinfish.mc.cat.particle.untils.NbtUtils;
import com.sereinfish.mc.cat.particle.untils.Utils;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DefaultParticleType;

public class ParticleNBT {
    public DefaultParticleType name = null; //粒子名称
    public String prefix = null; //前缀
    public String prefixColor = null; //前缀颜色
    public Long prefixTimer = -1L; //前缀有效时间，-1为永久
    public Boolean longDistance = false; //远距离显示
    public Boolean force = false; //暴力显示
    public Long delay = 1000L; //间隔时间
    //偏移量，基于玩家坐标
    public Float offsetX = 0f;
    public Float offsetY = 0f;
    public Float offsetZ = 0f;
    //随机量
    public Float randomX = 0f;
    public Float randomY = 0f;
    public Float randomZ = 0f;

    public Float speed = 1f; //粒子速度
    public int count = 1; //粒子数量

    public ParticleNBT(NbtCompound rootNbt){
        NbtCompound nbt = rootNbt.getCompound("SFParticle");
        if (nbt == null || !nbt.contains("name")){
            Start.logger.error("错误的 SFParticle 物品，无法实现粒子效果：null");
            return;
        }
        name = Utils.ParticleMapping.get(NbtUtils.getStringOrDefault(nbt, "name", null));
        if (name == null){
            Start.logger.error("错误的 SFParticle 物品，未知的粒子效果：" + nbt.getString("name"));
            return;
        }
        prefix = NbtUtils.getStringOrDefault(nbt, "prefix", null);
        prefixColor = NbtUtils.getStringOrDefault(nbt, "prefixColor", null);
        prefixTimer = NbtUtils.getLongOrDefault(nbt, "prefixTimer", -1L);
        longDistance = NbtUtils.getBooleanOrDefault(nbt, "longDistance", false);
        force = NbtUtils.getBooleanOrDefault(nbt, "force", false);
        delay = NbtUtils.getLongOrDefault(nbt, "delay", null);
        if (delay == null){
            Long interval = NbtUtils.getLongOrDefault(nbt, "interval", null);
            if (interval != null)
                delay = interval * 50;
            else
                delay = 1000L;
        }
        offsetX = NbtUtils.getFloatOrDefault(nbt, "offsetX", 0f);
        offsetY = NbtUtils.getFloatOrDefault(nbt, "offsetY", 0f);
        offsetZ = NbtUtils.getFloatOrDefault(nbt, "offsetZ", 0f);
        randomX = NbtUtils.getFloatOrDefault(nbt, "randomX", 0f);
        randomY = NbtUtils.getFloatOrDefault(nbt, "randomY", 0f);
        randomZ = NbtUtils.getFloatOrDefault(nbt, "randomZ", 0f);
        speed = NbtUtils.getFloatOrDefault(nbt, "speed", 0f);
        count = NbtUtils.getIntOrDefault(nbt, "count", 1);
    }

    public ParticleAnimation.Frame toFrame(){
        ParticleAnimation.Frame frame = new ParticleAnimation.Frame(delay);
        frame.data.add(this);
        return frame;
    }

    public ParticleAnimation toParticleAnimation(){
        ParticleAnimation animation = new ParticleAnimation();
        animation.addFrame(this.toFrame());
        return animation;
    }

    public DefaultParticleType getName() {
        return name;
    }

    public void setName(DefaultParticleType name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefixColor() {
        return prefixColor;
    }

    public void setPrefixColor(String prefixColor) {
        this.prefixColor = prefixColor;
    }

    public Long getPrefixTimer() {
        return prefixTimer;
    }

    public void setPrefixTimer(Long prefixTimer) {
        this.prefixTimer = prefixTimer;
    }

    public Boolean getLongDistance() {
        return longDistance;
    }

    public void setLongDistance(Boolean longDistance) {
        this.longDistance = longDistance;
    }

    public Boolean getForce() {
        return force;
    }

    public void setForce(Boolean force) {
        this.force = force;
    }

    public Long getDelay() {
        return delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    public Float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(Float offsetX) {
        this.offsetX = offsetX;
    }

    public Float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(Float offsetY) {
        this.offsetY = offsetY;
    }

    public Float getOffsetZ() {
        return offsetZ;
    }

    public void setOffsetZ(Float offsetZ) {
        this.offsetZ = offsetZ;
    }

    public Float getRandomX() {
        return randomX;
    }

    public void setRandomX(Float randomX) {
        this.randomX = randomX;
    }

    public Float getRandomY() {
        return randomY;
    }

    public void setRandomY(Float randomY) {
        this.randomY = randomY;
    }

    public Float getRandomZ() {
        return randomZ;
    }

    public void setRandomZ(Float randomZ) {
        this.randomZ = randomZ;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
