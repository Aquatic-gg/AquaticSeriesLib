package gg.aquatic.aquaticseries.lib.editor

import gg.aquatic.aquaticseries.lib.betterinventory2.SlotSelection
import gg.aquatic.aquaticseries.lib.betterinventory2.component.ButtonComponent
import gg.aquatic.aquaticseries.lib.editor.editable.Editable
import gg.aquatic.aquaticseries.lib.editor.menu.EditorPage
import gg.aquatic.aquaticseries.lib.editor.type.Chat
import gg.aquatic.aquaticseries.lib.util.Config
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import kotlin.math.ceil

class IngameEditor(val item: SerializableYml, val config: Config, val section: ConfigurationSection) {

    val page: EditorPage

    init {
        val buttons = ArrayList<ButtonComponent>()
        item::class.java.declaredFields.forEach { field ->
            if (field.type.isAssignableFrom(Editable::class.java)) {
                field.isAccessible = true
                val editable = field.get(item) as Editable<*>

                val type = editable.editType
                when (type) {
                    is Chat -> {
                        ButtonComponent(
                            field.name,
                            1,
                            SlotSelection.of(buttons.size),
                            HashMap(),
                            null,
                            { e -> },
                            5,
                            { p, s -> s },
                            type.item
                        )
                    }
                }
            }
        }
        val rows = ceil(buttons.size / 9.0).toInt() * 9
        page = EditorPage(rows)
        for (button in buttons) {
            page.addComponent(
                button
            )
        }
    }

    fun open(player: Player) {
        page.open(player)
    }

}