package com.sereinfish.mc.cat.particle.data

/**
 * 动画配置文件
 */
data class ParticleAnimationData(
    var map: HashMap<String, ParticleAnimationData>? = null, //效果列表
    val animation: ArrayList<AnimationData>? = null
){
    data class AnimationData(
        val particleNBT: ParticleNBT? = null,
        val config: String? = null
    )
}