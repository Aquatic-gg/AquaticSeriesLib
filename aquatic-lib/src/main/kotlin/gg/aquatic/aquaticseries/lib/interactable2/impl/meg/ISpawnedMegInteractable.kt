package gg.aquatic.aquaticseries.lib.interactable2.impl.meg

import com.ticxo.modelengine.api.model.ActiveModel
import com.ticxo.modelengine.api.model.ModeledEntity

interface ISpawnedMegInteractable {

    val activeModel: ActiveModel?
    val modeledEntity: ModeledEntity?
    val dummy: MegInteractableDummy

}