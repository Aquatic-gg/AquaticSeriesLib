package xyz.larkyy.aquaticseries.interactable.impl.block

import org.bukkit.Location
import xyz.larkyy.aquaticseries.interactable.AbstractSpawnedInteractable

class SpawnedBlockInteractable(
    override val location: Location,
    override val interactable: BlockInteractable,
    override val associatedLocations: List<Location>
) : AbstractSpawnedInteractable() {
    override fun despawn() {

    }
}