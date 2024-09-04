package gg.aquatic.aquaticseries.lib.editor

import gg.aquatic.aquaticseries.lib.Config
import gg.aquatic.aquaticseries.lib.editor.editable.Editable
import org.bukkit.configuration.ConfigurationSection

class IngameEditor(val item: SerializableYml, val config: Config, val section: ConfigurationSection) {

    init {
        item::class.java.declaredFields.forEach { field ->
            if (field.type.isAssignableFrom(Editable::class.java)) {
                field.isAccessible = true
                val editable = field.get(item) as Editable<*>
            }
        }
    }

}