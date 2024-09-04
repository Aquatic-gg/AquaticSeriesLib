package gg.aquatic.aquaticseries.lib.editor.type

import gg.aquatic.aquaticseries.lib.editor.Edit
import gg.aquatic.aquaticseries.lib.editor.editable.Editable
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier

class MultiChoice<T>(
    val choices: List<Choice<T>>
): Edit<T> {

    class Choice<T>(
        val name: String,
        val item: ItemStack,
        val factory: Supplier<out T>
    )

    override fun handle(editable: Editable<T>, player: Player): CompletableFuture<T> {
        TODO("Not yet implemented")
    }

}