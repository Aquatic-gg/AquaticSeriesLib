package gg.aquatic.aquaticseries.lib.interactable2.impl.meg

import gg.aquatic.aquaticseries.lib.audience.AquaticAudience
import gg.aquatic.aquaticseries.lib.block.AquaticMultiBlock
import gg.aquatic.aquaticseries.lib.interactable2.*
import gg.aquatic.aquaticseries.lib.interactable2.base.InteractableBase
import gg.aquatic.aquaticseries.lib.interactable2.base.SpawnedInteractableBase
import gg.aquatic.aquaticseries.lib.worldobject.WorldObjectHandler
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
                val value = WorldObjectHandler.getSpawnedObject(location)
                if (value is InteractableBase) {
                    toReturn = false
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
        audienceList: AquaticAudience,
        register: Boolean
    ): AbstractSpawnedPacketInteractable<*> {
        val spawnedBase = base.create(location)
        val spawnedInteractable =
            SpawnedPacketMegInteractable(audienceList, location, this, spawnedBase as SpawnedInteractableBase<*>)
        return spawn(spawnedBase, spawnedInteractable, register)
    }
}