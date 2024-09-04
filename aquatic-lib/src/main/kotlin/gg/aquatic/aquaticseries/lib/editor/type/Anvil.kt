package gg.aquatic.aquaticseries.lib.editor.type

import gg.aquatic.aquaticseries.lib.editor.Edit
import gg.aquatic.aquaticseries.lib.editor.editable.Editable
import org.bukkit.entity.Player
import java.util.concurrent.CompletableFuture

class Anvil: Edit<String> {
    override fun handle(editable: Editable<String>, player: Player): CompletableFuture<String> {
        TODO("Not yet implemented")
    }
}