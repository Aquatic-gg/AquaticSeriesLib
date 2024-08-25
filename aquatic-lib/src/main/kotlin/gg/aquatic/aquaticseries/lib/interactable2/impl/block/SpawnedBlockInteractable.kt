package gg.aquatic.aquaticseries.lib.interactable2.impl.block

import gg.aquatic.aquaticseries.lib.interactable2.AbstractSpawnedInteractable
import gg.aquatic.aquaticseries.lib.interactable2.InteractableInteractEvent
import gg.aquatic.aquaticseries.lib.interactable2.base.SpawnedInteractableBase
import org.bukkit.Location
import org.bukkit.Material

class SpawnedBlockInteractable(
    override val location: Location,
    override val base: BlockInteractable<*>,
    override val spawnedInteractableBase: SpawnedInteractableBase<*>
) : AbstractSpawnedInteractable<BlockInteractable<*>>() {


    override val associatedLocations = ArrayList<Location>()

    init {
        spawnBlocks()
    }

    private fun spawnBlocks() {
        base.multiBlock.spawn(location)
        val shape = base.multiBlock.shape
        base.multiBlock.processLayerCells(location) { char, loc ->
            val block = shape.blocks[char]
            if (block != null) {
                associatedLocations += loc
            }
        }
    }


    override fun despawn() {
        for (associatedLocation in associatedLocations) {
            associatedLocation.block.type = Material.AIR
        }
        associatedLocations.clear()
    }

    override fun onInteract(event: InteractableInteractEvent) {
        base.onInteract.accept(this, event)
    }
}