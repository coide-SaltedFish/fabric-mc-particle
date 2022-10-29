package com.sereinfish.mc.cat.particle.thread

import com.sereinfish.mc.cat.particle.Start
import com.sereinfish.mc.cat.particle.data.ParticleAnimation
import com.sereinfish.mc.cat.particle.data.ParticleNBT
import com.sereinfish.mc.cat.particle.untils.*
import kotlinx.coroutines.*
import net.fabricmc.fabric.api.networking.v1.PlayerLookup
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.AirBlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.LiteralTextContent
import net.minecraft.text.MutableText
import net.minecraft.util.Formatting
import net.minecraft.util.math.BlockPos

class PlayerParticleSlotRunnable(
    val player: ServerPlayerEntity,
    val slot: EquipmentSlot
) {
    var job: Job? = null
    var animation: ParticleAnimation? = null
    var item: ItemStack? = null

    init {
        update()
    }

    fun update(){
        if (!ParticleManager.particleContextScope.isActive) {
            ParticleManager.particleContextScope = ContextScope(Job() + Dispatchers.Default + CoroutineName("ParticleContext"))
        }

        player.getEquippedStack(slot).let { item ->
            this.item = item
            animation = if (item.isOf(Items.AIR))
                null
            else
                getParticleAnimation(item)
        }

        if (animation.isNotNull()) {
            if (job == null || job?.isActive != true){
                job = ParticleManager.particleContextScope.launch(
                    CoroutineExceptionHandler { _, throwable ->
                        Start.logger.error("粒子线程错误", throwable)
                        player.sendMessage(MutableText.of(LiteralTextContent("粒子效果错误，请联系服务器管理员解决")).formatted(Formatting.RED))
                    }
                ) {
                    while (!player.isDisconnected && animation.isNotNull() && player.getEquippedStack(slot) == item){
                        animation?.frames?.forEach { frame ->
                            //发送效果
                            frame.data.forEach { particleNBT ->
                                if (particleNBT.force){
                                    player.server.playerManager.playerList.forEach { otherPlayer ->
                                        if (isReceive(player, otherPlayer)){
                                            otherPlayer.networkHandler.sendPacket(buildPack(particleNBT))
                                        }
                                    }
                                }else {
                                    PlayerLookup.tracking(player.getWorld(), BlockPos(player.x, player.y, player.z)).forEach { otherPlayer ->
                                        if (isReceive(player, otherPlayer)){
                                            otherPlayer.networkHandler.sendPacket(buildPack(particleNBT))
                                        }
                                    }
                                }
                            }
                            delay(frame.delay)
                        }
                    }
                    job = null
                }
            }
        }else {
            job?.cancel()
            job = null
        }
    }

    private fun buildPack(particleNBT: ParticleNBT): ParticleS2CPacket {
        //随机范围
        var randomX: Float = getRandomFloat(0f, particleNBT.randomX)
        var randomY: Float = getRandomFloat(0f, particleNBT.randomY)
        var randomZ: Float = getRandomFloat(0f, particleNBT.randomZ)
        if (getRandom(0, 1) == 1) {
            randomX *= -1
        }
        if (getRandom(0, 1) == 1) {
            randomY *= -1
        }
        if (getRandom(0, 1) == 1) {
            randomZ *= -1
        }
        val x = player.x + randomX
        val y = player.y + randomY
        val z = player.z + randomZ
        //构建包
        return ParticleS2CPacket(particleNBT.name, particleNBT.longDistance, x, y, z,
            particleNBT.offsetX, particleNBT.offsetY, particleNBT.offsetZ, particleNBT.speed, particleNBT.count)
    }

    /**
     * 判断是否接收
     * @param source
     * @param receive
     * @return
     */
    fun isReceive(source: ServerPlayerEntity, receive: ServerPlayerEntity): Boolean {
        //总开关
        if (!Utils.particleConfig.get(source).enable) {
            return false
        }
        if (!Utils.particleConfig.get(receive).enable) {
            return false
        }
        //不给别人看
        if (Utils.particleConfig.get(source).onlyOwnEnable) {
            if (source.uuidAsString != receive.uuidAsString) {
                return false
            }
        }

        //不看别人
        if (!Utils.particleConfig.get(source).otherEnable) {
            if (source.uuidAsString != receive.uuidAsString) {
                return false
            }
        }

        //关闭自己的
        if (Utils.particleConfig.get(source).onlyOtherEnable)
            return false

        return true
    }

    fun debugInfo()=
        "${slot.getName()}:\n    [Job: ${job?.isActive}]\n    [item:${item?.name?.string}, animation:${if(animation.isNull()) "null" else "animation"}]"

    /**
     * 取消
     */
    fun close(){
        job?.cancel()
        job = null
        ParticleManager.particleContextScope.cancel()
    }
}