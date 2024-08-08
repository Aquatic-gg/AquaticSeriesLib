package gg.aquatic.aquaticseries.lib.interactable.impl.meg

import com.jeff_media.customblockdata.CustomBlockData
import com.ticxo.modelengine.api.ModelEngineAPI
import com.ticxo.modelengine.api.model.ActiveModel
import com.ticxo.modelengine.api.model.ModeledEntity
import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.interactable.AbstractInteractable
import gg.aquatic.aquaticseries.lib.interactable.AbstractSpawnedInteractable
import org.bukkit.Location
import kotlin.jvm.optionals.getOrNull

class SpawnedMegInteractable(
    override val location: Location,
    override val interactable: MEGInteractable,
    override val associatedLocations: List<Location>
) : AbstractSpawnedInteractable() {

    val dummy = MegInteractableDummy(this)
    override var loaded = false

    init {
        AquaticSeriesLib.INSTANCE.interactableHandler.addWorkloadJob(location.chunk) {
            dummy.location = this.location
            dummy.bodyRotationController.yBodyRot = location.yaw
            dummy.bodyRotationController.xHeadRot = location.pitch
            dummy.bodyRotationController.yHeadRot = location.yaw
            dummy.yHeadRot = location.yaw
            dummy.yBodyRot = location.yaw
            val me = ModelEngineAPI.createModeledEntity(dummy)
            val am = ModelEngineAPI.createActiveModel(interactable.modelId)
            me.addModel(am,true)
            this.loaded = true
        }
    }

    val modeledEntity: ModeledEntity?
        get() {
            if (!loaded) return null
            return ModelEngineAPI.getModeledEntity(dummy.entityId)
        }

    val activeModel: ActiveModel?
        get() {
            if (!loaded) return null
            return modeledEntity?.getModel(interactable.modelId)?.getOrNull()
        }

    override fun despawn() {
        destroyEntity()
        for (associatedLocation in associatedLocations) {
            AquaticSeriesLib.INSTANCE.interactableHandler.removeChildren(associatedLocation)
        }
        val cbd = CustomBlockData(location.block, AquaticSeriesLib.INSTANCE.plugin)
        cbd.remove(AbstractInteractable.INTERACTABLE_KEY)
        AquaticSeriesLib.INSTANCE.interactableHandler.removeParent(location)
        cbd.clear()
    }

    fun destroyEntity() {
        if (!loaded) return
        modeledEntity?.destroy()
    }
}