package xyz.larkyy.aquaticfarming.harvestable

import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import xyz.larkyy.aquaticfarming.AbstractAquaticFarming
import xyz.larkyy.aquaticfarming.farmland.AbstractCropFarmland
import xyz.larkyy.aquaticfarming.harvestable.condition.growth.ConfiguredGrowthCondition
import xyz.larkyy.aquaticfarming.harvestable.condition.place.ConfiguredPlaceCondition
import xyz.larkyy.aquaticseries.interactable.AbstractSpawnedInteractable

abstract class Harvestable {

    abstract val type: Type
    abstract val id: String
    abstract val farmlands: ArrayList<AbstractCropFarmland>
    abstract val seed: HarvestableSeed
    abstract val sneakAcceleration: Double
    val sneakAccelerable: Boolean
        get() {
            return sneakAcceleration > 0
        }
    abstract val placeConditions: MutableList<ConfiguredPlaceCondition>
    abstract val growthConditions: MutableList<ConfiguredGrowthCondition>
    abstract val cropNamespace: NamespacedKey

    companion object {
        val namespace = NamespacedKey(AbstractAquaticFarming.instance!!.injection.plugin,"AquaticFarming_Type")
        fun get(interactable: AbstractSpawnedInteractable): Harvestable? {
            val id = interactable.data.get(namespace, PersistentDataType.STRING) ?: return null
            return AbstractAquaticFarming.instance!!.harvestableManager.harvestables[id]
        }
    }

    enum class Type {
        TREE,
        CROP
    }

    fun giveSeed(player: Player, amount: Int) {
        val item = seed.item.getItem()
        val im = item.itemMeta ?: return
        im.persistentDataContainer.set(namespace, PersistentDataType.STRING, id)
        item.itemMeta = im
        item.amount = amount

        player.inventory.addItem(item)
    }

    abstract fun spawn(location: Location): SpawnedHarvestable

    abstract fun canBeSpawned(location: Location): Boolean

    abstract fun fromInteractable(interactable: AbstractSpawnedInteractable): SpawnedHarvestable?


}