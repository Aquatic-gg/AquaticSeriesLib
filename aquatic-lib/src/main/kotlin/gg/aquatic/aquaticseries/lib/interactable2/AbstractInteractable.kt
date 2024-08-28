package gg.aquatic.aquaticseries.lib.interactable2

import gg.aquatic.aquaticseries.lib.interactable2.base.InteractableBase
import gg.aquatic.aquaticseries.lib.interactable2.base.PersistentInteractableBase
import gg.aquatic.aquaticseries.lib.interactable2.base.SpawnedInteractableBase
import gg.aquatic.aquaticseries.lib.interactable2.base.TempInteractableBase
import gg.aquatic.aquaticseries.lib.util.AudienceList
import gg.aquatic.aquaticseries.lib.worldobject.WorldObjectHandler
import gg.aquatic.aquaticseries.lib.worldobject.WorldObjectSerializer
import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldObject
import gg.aquatic.aquaticseries.lib.worldobject.`object`.WorldObject
import org.bukkit.Location
import java.util.function.BiConsumer

abstract class AbstractInteractable<B>(
    val base: B
) where B : WorldObject, B: InteractableBase {

    abstract val onInteract: BiConsumer<SpawnedInteractable<*>,InteractableInteractEvent>

    fun register() {
        if (base is PersistentInteractableBase) {
            base.initialize(this)
        } else if (base is TempInteractableBase) {
            base.initialize(this)
        }
    }

    protected fun <T : SpawnedInteractable<*>> spawn(
        spawnedBase: SpawnedInteractableBase<*>,
        spawnedInteractable: T,
        register: Boolean
    ): T {
        spawnedBase.appliedInteractables += id to spawnedInteractable
        val wo = spawnedBase.worldObject
        if (register) {
            save(wo, spawnedBase, spawnedInteractable)
        }
        registerChildren(spawnedInteractable, spawnedBase)

        WorldObjectHandler.registerSpawnedObject(spawnedBase)
        return spawnedInteractable
    }

    private fun save(wo: WorldObject, spawnedBase: SpawnedWorldObject<*>, spawnedInteractable: SpawnedInteractable<*>) {
        if (wo !is PersistentInteractableBase) return
        val serializer = wo.serializer
        (serializer as WorldObjectSerializer<PersistentInteractableBase>).save(
            spawnedBase.customData,
            spawnedInteractable.spawnedInteractableBase as SpawnedWorldObject<PersistentInteractableBase>
        )
    }

    private fun registerChildren(spawnedInteractable: SpawnedInteractable<*>, spawnedBase: SpawnedInteractableBase<*>) {
        for (associatedLocation in spawnedInteractable.associatedLocations) {
            val persistentBase = spawnedBase as? SpawnedInteractableBase<PersistentInteractableBase>
            if (persistentBase != null) {
                val children = SpawnedInteractableBaseChildren(
                    persistentBase, associatedLocation
                )
                persistentBase.children.add(children)
            } else {
                val tempBase = spawnedBase as? SpawnedInteractableBase<TempInteractableBase>
                if (tempBase != null) {
                    val children = SpawnedInteractableBaseChildren(
                        tempBase, associatedLocation
                    )
                    tempBase.children.add(children)
                }
            }
        }
    }

    abstract fun fits(location: Location): Boolean

    abstract val id: String

    abstract fun spawn(location: Location, register: Boolean): AbstractSpawnedInteractable<*>
    abstract fun spawnPacket(location: Location, audienceList: AudienceList, register: Boolean): AbstractSpawnedPacketInteractable<*>

}