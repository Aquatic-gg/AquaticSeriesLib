package gg.aquatic.aquaticseries.lib.interactable2.base

import com.google.gson.Gson
import com.jeff_media.customblockdata.CustomBlockData
import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.audience.WhitelistAudience
import gg.aquatic.aquaticseries.lib.interactable2.AbstractInteractable
import gg.aquatic.aquaticseries.lib.interactable2.AbstractSpawnedPacketInteractable
import gg.aquatic.aquaticseries.lib.interactable2.InteractableData
import gg.aquatic.aquaticseries.lib.worldobject.WorldObjectSerializer
import gg.aquatic.aquaticseries.lib.worldobject.`object`.PersistentWorldObject
import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldObject
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataType

class PersistentInteractableBase : InteractableBase, PersistentWorldObject() {
    override val serializer: WorldObjectSerializer<*> = Serializer(this)
    override lateinit var id: String
    lateinit var interactable: AbstractInteractable<*>

    internal fun initialize(interactable: AbstractInteractable<*>) {
        id = interactable.id
        this.interactable = interactable
        register()
    }

    override fun onChunkLoad(chunk: Chunk) {

    }

    override fun onChunkUnload(chunk: Chunk) {

    }

    override fun onLoad(location: Location, spawned: SpawnedWorldObject<*>) {

    }

    override fun onUnload(location: Location, spawned: SpawnedWorldObject<*>) {

    }

    override fun create(location: Location): SpawnedWorldObject<*> {
        val spawned = SpawnedInteractableBase(location, this)
        return spawned
    }

    class Serializer(val base: PersistentInteractableBase) : WorldObjectSerializer<PersistentInteractableBase>() {

        companion object {
            val namespacedKey = NamespacedKey(AquaticSeriesLib.INSTANCE.plugin, "aquatic-interactable-data")
            val GSON = Gson()
        }

        override fun load(blockData: CustomBlockData): SpawnedWorldObject<PersistentInteractableBase> {
            val data = GSON.fromJson(
                blockData.getOrDefault(
                    namespacedKey,
                    PersistentDataType.STRING,
                    GSON.toJson(InteractableData(0f, false))
                ),
                InteractableData::class.java
            )
            val location = blockData.block!!.location.clone()
            location.yaw = data.yaw

            val spawned = if (data.packetBased) {
                base.interactable.spawnPacket(location, WhitelistAudience(mutableListOf()), false, true)
            } else {
                base.interactable.spawn(location, false, true)
            }
            return spawned.spawnedInteractableBase as SpawnedWorldObject<PersistentInteractableBase>
        }

        override fun save(blockData: CustomBlockData, spawned: SpawnedWorldObject<PersistentInteractableBase>) {
            spawned as SpawnedInteractableBase<*>
            val spawnedInteractable = spawned.appliedInteractables.values.firstOrNull() ?: return
            val data = InteractableData(
                spawnedInteractable.location.yaw,
                (spawnedInteractable is AbstractSpawnedPacketInteractable<*>)
            )
            blockData.set(namespacedKey, PersistentDataType.STRING, GSON.toJson(data))
        }
    }
}