package xyz.larkyy.aquaticseries.interactable

import java.util.UUID

class SpawnedRegistry {

    val parents = HashMap<String, AbstractSpawnedInteractable>()
    val children = HashMap<String,String>()
    val entityChildren = HashMap<UUID,String>()

}