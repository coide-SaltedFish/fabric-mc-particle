package com.sereinfish.mc.cat.particle.untils;

import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.sereinfish.mc.cat.particle.Start;
import com.sereinfish.mc.cat.particle.data.config.ParticleConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;

public class Utils {
    public static final String version = "1.0.3-Beta"; //版本名称
    private static final String encoding = "UTF-8"; //文件编码
    public static final File ConfigFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "sereinfish_particle_config.json"); //配置文件路径
    public static final HashMap<String, DefaultParticleType> ParticleMapping = new HashMap<>();
    public static ParticleConfig particleConfig;

    public static void init() {
        //初始化配置文件
        if (!ConfigFile.exists()) {
            ConfigFile.getParentFile().mkdirs();
        }
        try {
            particleConfig = toClass(ParticleConfig.class, fileRead(ConfigFile));
            if (particleConfig == null) particleConfig = new ParticleConfig();
        } catch (IOException e) {
            Start.logger.error("配置文件初始化失败", e);
        }
        //初始化粒子类型映射表
        try {
            for (Field declaredField : ParticleTypes.class.getDeclaredFields()) {
                if (DefaultParticleType.class == declaredField.getType()){
                    DefaultParticleType defaultParticleType = ((DefaultParticleType) declaredField.get(ParticleTypes.class));
                    String name = defaultParticleType.asString();
                    String[] names = defaultParticleType.asString().split(":");
                    if (names.length >= 2){
                        name = names[1];
                    }
                    ParticleMapping.put(name, defaultParticleType);
                }
            }
        }catch (Exception e){
            Start.logger.error("粒子类型映射表初始化失败", e);
        }
    }

    private Utils(){}

    public static String toJson(Object obj){
        return new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create().toJson(obj, obj.getClass());
    }

    public static  <T> T toClass(Type type, String json){
        if (strIsEmpty(json)) return null;
        JsonReader jsonReader = new JsonReader(new StringReader(json));
        return new GsonBuilder().setPrettyPrinting().create().fromJson(jsonReader, type);
    }

    public static boolean strIsEmpty(String str){
        return str == null || str.length() == 0;
    }

    public static String fileRead(File file) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        StringBuilder stringBuilder = new StringBuilder();
        try (
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, encoding);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
        ) {
            String lineTxt = bufferedReader.readLine();
            while (lineTxt != null) {
                stringBuilder.append(lineTxt);
                lineTxt = bufferedReader.readLine();
                if (lineTxt != null) {
                    stringBuilder.append("\n");
                }
            }
        }

        return stringBuilder.toString();
    }

    public static void fileWrite(File file, String str) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        try(
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, encoding);
        ) {
            outputStreamWriter.write(str);
        }
    }
}
