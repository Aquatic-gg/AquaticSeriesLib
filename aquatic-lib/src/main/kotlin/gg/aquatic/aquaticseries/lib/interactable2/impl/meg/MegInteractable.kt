package gg.aquatic.aquaticseries.lib.interactable2.impl.meg

import gg.aquatic.aquaticseries.lib.block.AquaticMultiBlock
import gg.aquatic.aquaticseries.lib.interactable2.*
import gg.aquatic.aquaticseries.lib.interactable2.base.InteractableBase
import gg.aquatic.aquaticseries.lib.interactable2.base.PersistentInteractableBase
import gg.aquatic.aquaticseries.lib.interactable2.base.SpawnedInteractableBase
import gg.aquatic.aquaticseries.lib.interactable2.base.TempInteractableBase
import gg.aquatic.aquaticseries.lib.worldobject.WorldObjectHandler
import gg.aquatic.aquaticseries.lib.worldobject.WorldObjectSerializer
import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldObject
import gg.aquatic.aquaticseries.lib.worldobject.`object`.WorldObject
import org.bukkit.Location
import org.bukkit.Material
import java.util.function.BiConsumer

class MegInteractable<B>(
    base: B, override val id: String,
    val multiBlock: AquaticMultiBlock,
    val modelId: String,
    override val onInteract: BiConsumer<SpawnedInteractable<*>, InteractableInteractEvent>
) : AbstractInteractable<B>(base) where B : WorldObject, B : InteractableBase {
    override fun fits(location: Location): Boolean {
        var toReturn = true
        multiBlock.processLayerCells(location) { _, loc ->
            if (toReturn) {
                if (loc.block.type != Material.AIR) {
                    toReturn = false
                }
                for (value in WorldObjectHandler.getSpawnedObject(location).values) {
                    if (value is InteractableBase) {
                        toReturn = false
                        break
                    }
                }
            }
        }
        return toReturn
    }

    override fun spawn(location: Location, register: Boolean): AbstractSpawnedInteractable<*> {
        val spawnedBase = base.create(location)
        val spawnedInteractable = SpawnedMegInteractable(location, this, spawnedBase as SpawnedInteractableBase<*>)
        return spawn(spawnedBase, spawnedInteractable, register)
    }

    override fun spawnPacket(
        location: Location,
        audienceList: AudienceList,
        register: Boolean
    ): AbstractSpawnedPacketInteractable<*> {
        val spawnedBase = base.create(location)
        val spawnedInteractable =
            SpawnedPacketMegInteractable(audienceList, location, this, spawnedBase as SpawnedInteractableBase<*>)
        return spawn(spawnedBase, spawnedInteractable, register)
    }

    private fun <T : SpawnedInteractable<*>> spawn(
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
}