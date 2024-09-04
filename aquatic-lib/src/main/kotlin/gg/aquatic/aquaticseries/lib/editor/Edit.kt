package gg.aquatic.aquaticseries.lib.editor

import gg.aquatic.aquaticseries.lib.editor.editable.Editable
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.concurrent.CompletableFuture

interface Edit<T> {

    fun handle(editable: Editable<T>, player: Player): CompletableFuture<T>

}