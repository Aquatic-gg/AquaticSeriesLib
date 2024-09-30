package gg.aquatic.aquaticseries.lib.worldobject.`object`

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.worldobject.WorldObjectHandler
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.NamespacedKey

abstract class WorldObject {

    abstract val id: String

    abstract fun onChunkLoad(chunk: Chunk)
    abstract fun onChunkUnload(chunk: Chunk)

    abstract fun onLoad(location: Location, spawned: SpawnedWorldObject<*>)
    abstract fun onUnload(location: Location, spawned: SpawnedWorldObject<*>)

    abstract fun create(location: Location): SpawnedWorldObject<*>

    val namespace: String
        get() {
            return WorldObjectHandler.namespacedKeyPrefix + "_" + id
        }
    val namespaceKey: NamespacedKey
        get() {
            return NamespacedKey(AquaticSeriesLib.INSTANCE.plugin, namespace)
        }

    fun register() {
        WorldObjectHandler.registry += id to this
    }
}