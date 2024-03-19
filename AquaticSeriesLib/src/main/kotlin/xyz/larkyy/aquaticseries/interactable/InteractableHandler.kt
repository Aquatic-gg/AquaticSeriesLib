package xyz.larkyy.aquaticseries.interactable

import xyz.larkyy.aquaticseries.interactable.impl.block.BlockInteractable
import xyz.larkyy.aquaticseries.interactable.impl.block.BlockInteractableSerializer

class InteractableHandler {

    val registry = HashMap<String,AbstractInteractable>()
    val spawnedIntectables = HashMap<String,AbstractSpawnedInteractable>()
    val serializers = HashMap<Class<out AbstractInteractable>,AbstractInteractableSerializer<*>>().apply {
        this[BlockInteractable::class.java] = BlockInteractableSerializer()
    }

}