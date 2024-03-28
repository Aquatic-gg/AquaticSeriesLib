package xyz.larkyy.aquaticfarming.harvestable

import com.google.gson.JsonParser
import org.bukkit.persistence.PersistentDataType
import xyz.larkyy.aquaticfarming.AbstractAquaticFarming
import xyz.larkyy.aquaticseries.interactable.AbstractSpawnedInteractable
import java.util.UUID

abstract class SpawnedHarvestable {

    abstract val uuid: UUID
    abstract fun tick()

    companion object {

        fun getUUID(interactable: AbstractSpawnedInteractable): UUID? {
            val harvestable = Harvestable.get(interactable) ?: return null
            val obj = interactable.data.get(harvestable.cropNamespace, PersistentDataType.STRING) ?: return null
            val jsonObj = JsonParser().parse(obj).asJsonObject
            return UUID.fromString(jsonObj.get("uuid").asString)
        }

        fun getSpawned(interactable: AbstractSpawnedInteractable): SpawnedHarvestable? {
            val uuid = getUUID(interactable) ?: return null
            return AbstractAquaticFarming.instance!!.harvestableManager.spawnedHarvestables[uuid]
        }

    }

}