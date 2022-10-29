package com.sereinfish.mc.cat.particle.thread

import com.sereinfish.mc.cat.particle.untils.ContextScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import net.minecraft.entity.EquipmentSlot
import net.minecraft.server.network.ServerPlayerEntity

/**
 * 粒子效果管理器
 */
object ParticleManager {
    var particleContextScope = ContextScope(Job() + Dispatchers.Default + CoroutineName("ParticleContext"))
    val map = HashMap<String, HashMap<EquipmentSlot, PlayerParticleSlotRunnable>>()

    fun add(playerEntity: ServerPlayerEntity){
        map.computeIfAbsent(playerEntity.uuidAsString){ HashMap() }.let {
            it.computeIfAbsent(EquipmentSlot.MAINHAND){ PlayerParticleSlotRunnable(playerEntity, EquipmentSlot.MAINHAND) }
            it.computeIfAbsent(EquipmentSlot.OFFHAND){ PlayerParticleSlotRunnable(playerEntity, EquipmentSlot.OFFHAND) }
            it.computeIfAbsent(EquipmentSlot.HEAD){ PlayerParticleSlotRunnable(playerEntity, EquipmentSlot.HEAD) }
            it.computeIfAbsent(EquipmentSlot.CHEST){ PlayerParticleSlotRunnable(playerEntity, EquipmentSlot.CHEST) }
            it.computeIfAbsent(EquipmentSlot.FEET){ PlayerParticleSlotRunnable(playerEntity, EquipmentSlot.FEET) }
            it.computeIfAbsent(EquipmentSlot.LEGS){ PlayerParticleSlotRunnable(playerEntity, EquipmentSlot.LEGS) }
        }
    }

    fun remove(playerEntity: ServerPlayerEntity){
        map[playerEntity.uuidAsString]?.let {
            it.forEach { (_, u) ->
                u.close()
            }
            map.remove(playerEntity.uuidAsString)
        }

    }

    /**
     * 状态更新
     */
    fun update(playerEntity: ServerPlayerEntity, slot: EquipmentSlot? = null){
        map.computeIfAbsent(playerEntity.uuidAsString){ HashMap() }.let { mmap ->
            slot?.let {
                mmap.computeIfAbsent(slot){ PlayerParticleSlotRunnable(playerEntity, slot) }.update()
            } ?: kotlin.run {
                mmap.computeIfAbsent(EquipmentSlot.MAINHAND){ PlayerParticleSlotRunnable(playerEntity, EquipmentSlot.MAINHAND) }.update()
                mmap.computeIfAbsent(EquipmentSlot.OFFHAND){ PlayerParticleSlotRunnable(playerEntity, EquipmentSlot.OFFHAND) }.update()
                mmap.computeIfAbsent(EquipmentSlot.HEAD){ PlayerParticleSlotRunnable(playerEntity, EquipmentSlot.HEAD) }.update()
                mmap.computeIfAbsent(EquipmentSlot.CHEST){ PlayerParticleSlotRunnable(playerEntity, EquipmentSlot.CHEST) }.update()
                mmap.computeIfAbsent(EquipmentSlot.FEET){ PlayerParticleSlotRunnable(playerEntity, EquipmentSlot.FEET) }.update()
                mmap.computeIfAbsent(EquipmentSlot.LEGS){ PlayerParticleSlotRunnable(playerEntity, EquipmentSlot.LEGS) }.update()
            }
        }
    }

    fun particleContextScopeIsActive() = particleContextScope.isActive

    fun close(){
        map.forEach { (_, u) ->
            u.forEach { (_, runnable) ->
                runnable.close()
            }
        }
    }
}