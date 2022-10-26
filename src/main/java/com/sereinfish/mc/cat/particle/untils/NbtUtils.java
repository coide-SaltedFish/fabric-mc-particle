package com.sereinfish.mc.cat.particle.untils;

import com.sereinfish.mc.cat.particle.Start;
import com.sereinfish.mc.cat.particle.data.ParticleNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class NbtUtils {
    private NbtUtils(){

    }

    public static ParticleNBT getParticleNBT(ItemStack itemStack){
        NbtCompound rootNbt = itemStack.getNbt();
        if (rootNbt == null) return null;
        if (!rootNbt.contains("SFParticle")) return null;
        NbtCompound nbt = rootNbt.getCompound("SFParticle");
        if (!nbt.contains("name") || !Utils.ParticleMapping.containsKey(NbtUtils.getStringOrDefault(nbt, "name", null))) {
            Start.logger.error("未知粒子效果：" + NbtUtils.getStringOrDefault(nbt, "name", null));
            return null;
        }
        return new ParticleNBT(rootNbt);
    }

    public static String getStringOrDefault(NbtCompound nbt, String key, String defaultValue){
        if(nbt.contains(key))
            return nbt.getString(key);
        else return defaultValue;
    }

    public static Long getLongOrDefault(NbtCompound nbt, String key, Long defaultValue){
        if(nbt.contains(key))
            return nbt.getLong(key);
        else return defaultValue;
    }

    public static Boolean getBooleanOrDefault(NbtCompound nbt, String key, Boolean defaultValue){
        if(nbt.contains(key))
            return nbt.getBoolean(key);
        else return defaultValue;
    }

    public static int getIntOrDefault(NbtCompound nbt, String key, int defaultValue){
        if(nbt.contains(key))
            return nbt.getInt(key);
        else return defaultValue;
    }

    public static Float getFloatOrDefault(NbtCompound nbt, String key, Float defaultValue){
        if(nbt.contains(key))
            return nbt.getFloat(key);
        else return defaultValue;
    }
}
