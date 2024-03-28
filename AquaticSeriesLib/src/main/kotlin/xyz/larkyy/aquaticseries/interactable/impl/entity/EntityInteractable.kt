package xyz.larkyy.aquaticseries.interactable.impl.entity

import org.bukkit.Location
import org.bukkit.util.Consumer
import org.bukkit.util.Vector
import xyz.larkyy.aquaticseries.AquaticSeriesLib
import xyz.larkyy.aquaticseries.interactable.AbstractInteractable
import xyz.larkyy.aquaticseries.interactable.InteractableData
import xyz.larkyy.aquaticseries.interactable.event.EntityInteractableDamageEvent
import xyz.larkyy.aquaticseries.interactable.event.EntityInteractableInteractEvent
import xyz.larkyy.aquaticseries.interactable.impl.block.BlockShape

class EntityInteractable(
    override val id: String,
    override val shape: BlockShape,
    val entityInfo: EntityInfo,
    val children: MutableMap<EntityInfo,Vector>,
    val onInteract: Consumer<EntityInteractableInteractEvent>,
    val onDamage: Consumer<EntityInteractableDamageEvent>,
) : AbstractInteractable() {

    init {
        AquaticSeriesLib.INSTANCE.interactableHandler.registry[id] = this
    }

    override val serializer: EntityInteractableSerializer
        get() {
            return AquaticSeriesLib.INSTANCE.interactableHandler.serializers[EntityInteractable::class.java] as EntityInteractableSerializer
        }

    override fun spawn(location: Location): SpawnedEntityInteractable {
        val spawned = SpawnedEntityInteractable(location,this)
        return spawned
    }

    override fun onChunkLoad(data: InteractableData, location: Location) {
        serializer.deserialize(data, location, this)
    }

    override fun onChunkUnload(data: InteractableData) {

    }

    fun onInteract(event: EntityInteractableInteractEvent) {
        this.onInteract.accept(event)
    }
    fun onDamage(event: EntityInteractableDamageEvent) {
        this.onDamage.accept(event)
    }

}