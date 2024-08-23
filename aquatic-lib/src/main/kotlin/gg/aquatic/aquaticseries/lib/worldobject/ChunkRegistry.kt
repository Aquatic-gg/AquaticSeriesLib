package gg.aquatic.aquaticseries.lib.worldobject

import gg.aquatic.aquaticseries.lib.interactable.AbstractSpawnedInteractable

class ChunkRegistry {
    val parents = HashMap<String, AbstractSpawnedInteractable>()
    val children = HashMap<String,String>()
}