package gg.aquatic.aquaticseries.lib

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

fun ItemStack.displayName(string: AquaticString) {
    AbstractAquaticSeriesLib.INSTANCE.adapter.itemStackAdapter.displayName(string, this)
}
fun ItemStack.lore(vararg strings: AquaticString) {
    AbstractAquaticSeriesLib.INSTANCE.adapter.itemStackAdapter.lore(strings = strings, this)
}
fun ItemStack.lore(strings: List<AquaticString>) {
    AbstractAquaticSeriesLib.INSTANCE.adapter.itemStackAdapter.lore(strings = strings, this)
}

fun ItemMeta.displayName(string: AquaticString) {
    AbstractAquaticSeriesLib.INSTANCE.adapter.itemStackAdapter.displayName(string, this)
}
fun ItemMeta.lore(vararg strings: AquaticString) {
    AbstractAquaticSeriesLib.INSTANCE.adapter.itemStackAdapter.lore(strings = strings, this)
}
fun ItemMeta.lore(strings: List<AquaticString>) {
    AbstractAquaticSeriesLib.INSTANCE.adapter.itemStackAdapter.lore(strings = strings, this)
}

fun ItemMeta.updatePlaceholders(player: Player, placeholders: Placeholders): ItemMeta {
    val aDiplayName = AbstractAquaticSeriesLib.INSTANCE.adapter.itemStackAdapter.getAquaticDisplayName(this)
    if (aDiplayName != null) {
        this.displayName(placeholders.replace(aDiplayName.string).toAquatic())
    }

    val aLore = AbstractAquaticSeriesLib.INSTANCE.adapter.itemStackAdapter.getAquaticLore(this)
    this.lore(aLore.map { placeholders.replace(it.string).toAquatic() })

    return this
}
fun ItemStack.updatePlaceholders(player: Player, placeholders: Placeholders): ItemStack {
    val im = this.itemMeta ?: return this
    im.updatePlaceholders(player, placeholders)
    this.itemMeta = im

    return this
}