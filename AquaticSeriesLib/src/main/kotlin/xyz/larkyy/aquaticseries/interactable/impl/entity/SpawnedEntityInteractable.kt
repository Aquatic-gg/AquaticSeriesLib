package xyz.larkyy.aquaticseries.interactable.impl.entity

import com.jeff_media.customblockdata.CustomBlockData
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.persistence.PersistentDataType
import xyz.larkyy.aquaticseries.AquaticSeriesLib
import xyz.larkyy.aquaticseries.interactable.AbstractInteractable
import xyz.larkyy.aquaticseries.interactable.AbstractSpawnedInteractable
import xyz.larkyy.aquaticseries.interactable.InteractableData

class SpawnedEntityInteractable(
    override val location: Location,
    override val interactable: EntityInteractable,
) : AbstractSpawnedInteractable() {
    override var loaded: Boolean = false
    override val associatedLocations: ArrayList<Location> = ArrayList()
    val children = ArrayList<Entity>()

    var entity: Entity? = null
    init {
        AquaticSeriesLib.INSTANCE.interactableHandler.addWorkloadJob(location.chunk) {
            this.entity = location.world!!.spawnEntity(location,interactable.entityInfo.entityType)
            interactable.entityInfo.entityData.apply(entity!!)
            entity!!.isPersistent = false
            AquaticSeriesLib.INSTANCE.interactableHandler.addEntityChildren(entity!!,this)
            despawnChildren()
            for (child in interactable.children) {
                val loc = location.clone().add(child.value)
                val e = loc.world!!.spawnEntity(loc,child.key.entityType)
                child.key.entityData.apply(e)
                e.isPersistent = false
                children += e
                AquaticSeriesLib.INSTANCE.interactableHandler.addEntityChildren(e,this)
            }
            this.loaded = true
        }

        for (associatedLocation in associatedLocations) {
            AquaticSeriesLib.INSTANCE.interactableHandler.removeChildren(associatedLocation)
        }
        val nullChars = ArrayList<Char>()
        associatedLocations.clear()
        interactable.processLayerCells(interactable.shape.layers, location) { char, newLoc ->
            val block = interactable.shape.blocks[char]
            if (block == null) {
                nullChars += char
            } else {
                block.place(newLoc)
                associatedLocations += newLoc
            }
        }

        val cbd = CustomBlockData(location.block, AquaticSeriesLib.INSTANCE.plugin)
        val blockData =
            InteractableData(
                interactable.id,
                location.yaw,
                location.pitch,
                interactable.serializer.serialize(this),
                interactable.shape.layers,
                nullChars
            )
        cbd.set(
            AbstractInteractable.INTERACTABLE_KEY,
            PersistentDataType.STRING,
            AquaticSeriesLib.GSON.toJson(blockData)
        )

        AquaticSeriesLib.INSTANCE.interactableHandler.addParent(location,this)
        for (loc in associatedLocations) {
            AquaticSeriesLib.INSTANCE.interactableHandler.addChildren(loc,location)
        }
    }

    override fun unload() {
        despawnChildren()
        if (entity != null) {
            AquaticSeriesLib.INSTANCE.interactableHandler.removeEntityChildren(entity!!)
            entity?.remove()
        }
    }

    private fun despawnChildren() {
        for (child in children) {
            AquaticSeriesLib.INSTANCE.interactableHandler.removeEntityChildren(child)
            child.remove()
        }
        children.clear()
    }
}