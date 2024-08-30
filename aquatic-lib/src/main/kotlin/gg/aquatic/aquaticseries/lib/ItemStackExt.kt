package gg.aquatic.aquaticseries.lib

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.util.itemencode.ItemEncoder
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import org.bukkit.block.CreatureSpawner
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BlockStateMeta
import org.bukkit.inventory.meta.ItemMeta

fun ItemStack.encode(): String {
    return ItemEncoder.encode(this)
}

fun String.decodeToItemStack(): ItemStack {
    return ItemEncoder.decode(this)
}

fun ItemStack.displayName(string: AquaticString) {
    AbstractAquaticSeriesLib.INSTANCE.adapter.itemStackAdapter.displayName(string, this)
}
fun ItemStack.lore(vararg strings: AquaticString) {
    AbstractAquaticSeriesLib.INSTANCE.adapter.itemStackAdapter.lore(strings = strings, this)
}
fun ItemStack.lore(strings: List<AquaticString>) {
    AbstractAquaticSeriesLib.INSTANCE.adapter.itemStackAdapter.lore(strings = strings, this)
}
fun ItemStack.setSpawnerType(type: EntityType) {
    val meta = itemMeta ?: return
    meta.setSpawnerType(type)
    itemMeta = meta
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
fun ItemMeta.setSpawnerType(type: EntityType) {
    if (this !is BlockStateMeta) return
    val blockState = this.blockState as? CreatureSpawner ?: return
    blockState.spawnedType = type
    this.blockState = blockState
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