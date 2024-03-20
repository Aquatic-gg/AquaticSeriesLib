package xyz.larkyy.aquaticseries.interactable.impl.block

import com.jeff_media.customblockdata.CustomBlockData
import org.bukkit.Location
import org.bukkit.Material
import xyz.larkyy.aquaticseries.AquaticSeriesLib
import xyz.larkyy.aquaticseries.interactable.AbstractInteractable
import xyz.larkyy.aquaticseries.interactable.AbstractSpawnedInteractable
import xyz.larkyy.aquaticseries.toStringDetailed
import xyz.larkyy.aquaticseries.toStringSimple

class SpawnedBlockInteractable(
    override val location: Location,
    override val interactable: BlockInteractable,
    override val associatedLocations: List<Location>
) : AbstractSpawnedInteractable() {
    override fun despawn() {
        for (associatedLocation in associatedLocations) {
            associatedLocation.block.type = Material.AIR
            AquaticSeriesLib.INSTANCE.interactableHandler.spawnedChildren.remove(associatedLocation.toStringSimple())
        }
        val cbd = CustomBlockData(location.block,AquaticSeriesLib.INSTANCE.plugin)
        cbd.remove(AbstractInteractable.INTERACTABLE_KEY)
        AquaticSeriesLib.INSTANCE.interactableHandler.spawnedIntectables.remove(location.toStringDetailed())
    }
}