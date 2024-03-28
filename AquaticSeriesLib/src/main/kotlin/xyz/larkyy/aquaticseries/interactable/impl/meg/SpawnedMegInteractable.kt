package xyz.larkyy.aquaticseries.interactable.impl.meg

import com.jeff_media.customblockdata.CustomBlockData
import com.ticxo.modelengine.api.ModelEngineAPI
import com.ticxo.modelengine.api.model.ActiveModel
import com.ticxo.modelengine.api.model.ModeledEntity
import org.bukkit.Location
import org.bukkit.Material
import xyz.larkyy.aquaticseries.AquaticSeriesLib
import xyz.larkyy.aquaticseries.interactable.AbstractInteractable
import xyz.larkyy.aquaticseries.interactable.AbstractSpawnedInteractable
import xyz.larkyy.aquaticseries.toStringDetailed
import xyz.larkyy.aquaticseries.toStringSimple
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

    override fun unload() {
        if (!loaded) return
        modeledEntity?.destroy()
    }
}