package com.sereinfish.mc.cat.particle.data

import com.sereinfish.mc.cat.particle.data.ParticleAnimation.Frame
import com.sereinfish.mc.cat.particle.untils.isNotNull

/**
 * 动画配置文件
 */
data class ParticleAnimationData(
    var map: HashMap<String, ParticleAnimationData>? = null, //效果列表
    val animation: ArrayList<AnimationData>? = null
){

    operator fun get(key: String): ParticleAnimationData? =
        map?.let {
            if (it.containsKey(key))
                it[key]
            else {
                it.forEach { (_, u) ->
                    val data = u[key]
                    if (data.isNotNull())
                        return@let data
                }
                null
            }
        }
    fun toParticleAnimation(): ParticleAnimation =
        ParticleAnimation(this.toFrames())

    fun toFrames(): ArrayList<Frame>{
        val list = ArrayList<Frame>()
        animation?.forEach { data ->
            data.particleNBT?.let {
                list.add(it.toFrame())
            } ?: kotlin.run {
                data.config?.let {
                    this[it]?.let { data ->
                        list.addAll(data.toFrames())
                    }
                }
            }
        }
        return list
    }

    data class AnimationData(
        val particleNBT: ParticleNBT? = null,
        val config: String? = null
    )
}