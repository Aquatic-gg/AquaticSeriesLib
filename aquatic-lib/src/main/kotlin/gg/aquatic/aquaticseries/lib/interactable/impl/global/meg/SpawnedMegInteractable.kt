package gg.aquatic.aquaticseries.lib.interactable.impl.global.meg

import com.jeff_media.customblockdata.CustomBlockData
import com.ticxo.modelengine.api.ModelEngineAPI
import com.ticxo.modelengine.api.model.ActiveModel
import com.ticxo.modelengine.api.model.ModeledEntity
import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.interactable.AbstractInteractable
import gg.aquatic.aquaticseries.lib.interactable.AbstractSpawnedInteractable
import gg.aquatic.aquaticseries.lib.interactable.event.MegInteractableInteractEvent
import org.bukkit.Location
import org.bukkit.util.Consumer
import kotlin.jvm.optionals.getOrNull

class SpawnedMegInteractable(
    override val location: Location,
    override val interactable: MEGInteractable,
    override val associatedLocations: List<Location>
) : AbstractSpawnedInteractable() {

    var onInteract: Consumer<MegInteractableInteractEvent>? = null

    val dummy = MegInteractableDummy(this)
    override var loaded = false

    init {
        AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.addWorkloadJob(location.chunk) {
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
            AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.removeChildren(associatedLocation)
        }
        if (interactable.persistent) {
            val cbd = CustomBlockData(location.block, AbstractAquaticSeriesLib.INSTANCE.plugin)
            cbd.remove(AbstractInteractable.INTERACTABLE_KEY)
        }
        AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.removeParent(location)
    }

    fun destroyEntity() {
        if (!loaded) return
        modeledEntity?.destroy()
    }
}