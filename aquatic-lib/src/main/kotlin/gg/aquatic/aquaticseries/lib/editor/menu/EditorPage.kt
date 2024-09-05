package gg.aquatic.aquaticseries.lib.editor.menu

import gg.aquatic.aquaticseries.lib.betterinventory2.AquaticInventory
import gg.aquatic.aquaticseries.lib.toAquatic

class EditorPage(size: Int): AquaticInventory(
    "Ingame Editor".toAquatic(),
    size,
    null,
    { t,u -> },
    { t,u -> },
    { t,u -> }
) {

    init {

    }

}