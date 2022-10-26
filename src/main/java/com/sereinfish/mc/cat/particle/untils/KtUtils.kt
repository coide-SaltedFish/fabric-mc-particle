package com.sereinfish.mc.cat.particle.untils

import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import com.sereinfish.mc.cat.particle.Start
import com.sereinfish.mc.cat.particle.data.ParticleAnimationData
import kotlinx.coroutines.CoroutineScope
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.render.entity.animation.Animation
import java.io.*
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.HashMap
import kotlin.coroutines.CoroutineContext

const val encoding = "UTF-8" //文件编码

//动画配置文件路径
val AnimationConfigFile = File(FabricLoader.getInstance().configDir.toFile(), "/animation/").also {
    if (!it.exists())
        it.mkdirs()
}

val particleAnimationMap = HashMap<String, ParticleAnimationData>().also { map ->
    AnimationConfigFile.listFiles()?.forEach { file ->
        println(file.extension)
        if(file.extension.lowercase() == "json"){
            //尝试读取
            try {
                file.readString().toClass<ParticleAnimationData>(ParticleAnimationData::class.java)?.let { particleAnimationData ->
                    particleAnimationData.animation?.let {
                        if (map.containsKey(file.nameWithoutExtension))
                            Start.logger.error("已存在动画效果：${file.nameWithoutExtension}, 错误：$file")
                        else
                            map[file.nameWithoutExtension] = particleAnimationData
                    }
                }
            }catch (e: Exception){
                Start.logger.warn("无法读取的配置文件：${file}")
            }
        }
    }
}

/**
 * 得到一个可控范围随机数
 * @param start
 * @param end
 * @return
 */
fun getRandomFloat(start: Float, end: Float): Float {
    var start = start
    var end = end
    if (start < 0) {
        start = 0f
    }
    if (end < 0) {
        end = 0f
    }
    if (start == end) {
        return start
    }
    val s = (start * 100).toInt()
    val e = (end * 100).toInt()
    val random = Random()
    val v = random.nextInt(e - s + 1) + s
    return v.toFloat() / 100
}

/**
 * 得到一个可控范围随机数
 * @param start
 * @param end
 * @return
 */
fun getRandom(start: Int, end: Int): Int {
    val random = Random()
    return random.nextInt(end - start + 1) + start
}

/**
 * 写入文件
 */
fun write(file: File, str: String) {
    if (!file.exists()) {
        file.parentFile.mkdirs()
        file.createNewFile()
    }
    // 创建临时文件
    OutputStreamWriter(FileOutputStream(file), encoding).use {
        it.write(str)
    }
}

/**
 * 文件读取
 */
fun read(file: File): String {
    if (!file.exists()) {
        file.parentFile.mkdirs()
        file.createNewFile()
    }
    val stringBuilder = StringBuilder()
    FileInputStream(file).use {
        InputStreamReader(it, encoding).use { read ->
            val bufferedReader = BufferedReader(read)
            var lineTxt = bufferedReader.readLine()
            while (lineTxt != null) {
                stringBuilder.append(lineTxt)
                lineTxt = bufferedReader.readLine()
                if (lineTxt != null) {
                    stringBuilder.append("\n")
                }
            }
        }
    }
    return stringBuilder.toString()
}

/**
 * 对象转json文本
 * @return
 */
fun Any.toJson(): String {
    return GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create().toJson(this, this.javaClass)
}

/**
 * 文本转json对象
 * @param json
 * @param type
 * @param <T>
 * @return
</T> */
fun <T> String.toClass(type: Type): T? {
    if (this.isEmpty())
        return null
    val jsonReader = JsonReader(StringReader(this))
    jsonReader.isLenient = true
    return GsonBuilder().setPrettyPrinting().create().fromJson(jsonReader, type)
}

fun File.readString(): String =
    read(this)

fun String.writeFile(file: File){
    write(file, this)
}

class ContextScope(context: CoroutineContext) : CoroutineScope {
    override val coroutineContext: CoroutineContext = context
    // CoroutineScope is used intentionally for user-friendly representation
    override fun toString(): String = "CoroutineScope(coroutineContext=$coroutineContext)"
}

fun Any?.isNull() = this == null

fun Any?.isNotNull() = this != null