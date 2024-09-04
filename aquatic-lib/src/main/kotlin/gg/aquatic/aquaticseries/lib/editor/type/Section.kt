package gg.aquatic.aquaticseries.lib.editor.type

import gg.aquatic.aquaticseries.lib.editor.Edit
import gg.aquatic.aquaticseries.lib.editor.editable.Editable
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.concurrent.CompletableFuture

class Section<T>(
    val name: String,
    val item: ItemStack,
): Edit<T> {
    override fun handle(editable: Editable<T>, player: Player): CompletableFuture<T> {
        return CompletableFuture.completedFuture(editable.value)
    }
}