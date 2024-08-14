package gg.aquatic.aquaticseries.lib.inventory.lib.title

import gg.aquatic.aquaticseries.lib.inventory.lib.title.component.BasicTitleComponent

class TitleHandler {

    val registry = TitleComponentRegistry()

    fun getSpaceComponent(space: Int): BasicTitleComponent? {
        if (space == 0) {
            return null
        }
        return if (space < 0) {
            getNegativeSpace(space)
        } else {
            getPositiveSpace(space)
        }
    }

    private fun getPositiveSpace(space: Int): BasicTitleComponent? {
        val components = ArrayList<BasicTitleComponent?>()
        var spaceComponent = registry.getSpaceComponent(space)
        val foundSpace: Int
        if (spaceComponent == null) {
            val lowerKey = registry.spaceComponents.lowerKey(space) ?: return null
            if (lowerKey < 1) return null
            foundSpace = lowerKey
            spaceComponent = registry.spaceComponents[lowerKey]!!
        } else {
            foundSpace = space
        }
        components += spaceComponent
        if ((space-foundSpace) > 0) {
            components.add(getPositiveSpace(space-foundSpace))
        }

        var c = StringBuilder()
        for (component in components) {
            component ?: continue
            c = c.append(component.component)
        }
        return BasicTitleComponent(c.toString())
    }

    private fun getNegativeSpace(space: Int): BasicTitleComponent? {
        val components: ArrayList<BasicTitleComponent?> = ArrayList()
        var spaceComponent: BasicTitleComponent? = registry.getSpaceComponent(space)
        val foundSpace: Int
        if (spaceComponent == null) {
            val higherKey: Int = registry.spaceComponents.higherKey(space) ?: return null
            if (higherKey > -1) return null
            foundSpace = higherKey
            spaceComponent = registry.spaceComponents[higherKey]!!
        } else {
            foundSpace = space
        }
        components += spaceComponent
        if ((space - foundSpace) < 0) {
            components.add(getNegativeSpace(space - foundSpace))
        }

        var c = StringBuilder()
        for (component: BasicTitleComponent? in components) {
            component ?: continue
            c = c.append(component.component)
        }
        return BasicTitleComponent(c.toString())
    }

}