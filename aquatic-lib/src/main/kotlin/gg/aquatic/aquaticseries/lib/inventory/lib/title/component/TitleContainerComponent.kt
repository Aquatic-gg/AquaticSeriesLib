package gg.aquatic.aquaticseries.lib.inventory.lib.title.component

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.inventory.lib.InventoryHandler
import org.bukkit.entity.Player
import gg.aquatic.aquaticseries.lib.inventory.lib.inventory.CustomInventory

class TitleContainerComponent : TitleComponent() {

    val components = ArrayList<TitleComponent>()
    var alignment: Alignment = Alignment.LEFT

    override fun generate(inventory: CustomInventory, player: Player): List<AquaticString> {
        val generated = ArrayList<AquaticString>()
        var length = 0
        for (component in components) {
            val generatedComponent = component.generate(inventory, player)
            length += component.length
            generated.addAll(generatedComponent)
        }

        when (alignment) {
            Alignment.CENTER -> {
                this.length = length / 2

                val negativeSpace =
                    InventoryHandler.titleHandler.getSpaceComponent(-length + this.length)
                if (negativeSpace != null) {
                    generated.add(0,negativeSpace.component)
                }
            }

            else -> {
                this.length = length
            }
        }
        return generated
    }

    enum class Alignment {
        CENTER,
        LEFT
    }

}