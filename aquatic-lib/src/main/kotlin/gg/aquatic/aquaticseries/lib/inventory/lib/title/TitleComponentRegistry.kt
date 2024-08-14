package gg.aquatic.aquaticseries.lib.inventory.lib.title

import gg.aquatic.aquaticseries.lib.inventory.lib.title.component.BasicTitleComponent
import gg.aquatic.aquaticseries.lib.inventory.lib.title.component.TitleComponent
import java.util.TreeMap

class TitleComponentRegistry {

    val titleComponents = HashMap<String, TitleComponent>()
    val spaceComponents = TreeMap<Int, BasicTitleComponent>()
    val numberComponents = HashMap<String,HashMap<Int, BasicTitleComponent>>()

    fun addComponent(id: String, component: TitleComponent) {
        titleComponents[id] = component
    }

    fun addSpaceComponent(space: Int, component: BasicTitleComponent) {
        spaceComponents[space] = component
    }

    fun addNumberComponent(style: String, number: Int, component: BasicTitleComponent) {
        val styleComponents = numberComponents.getOrPut(style) { hashMapOf() }
        styleComponents[number] = component
    }

    fun getTitleComponent(id: String): TitleComponent? {
        return titleComponents[id]
    }

    fun getSpaceComponent(length: Int): BasicTitleComponent? {
        return spaceComponents[length]
    }
    fun getNumberComponent(style: String, number: Int): BasicTitleComponent? {
        return numberComponents[style]?.get(number)
    }
}