package xyz.larkyy.aquaticfarming.harvestable

import org.bukkit.NamespacedKey
import xyz.larkyy.aquaticfarming.AbstractAquaticFarming

class TreeBlockData(
    val treeId: String,
    val ingredient: Char
) {

    companion object {
        val NAMESPACEKEY = NamespacedKey(AbstractAquaticFarming.instance!!.injection.plugin,"aquaticfarming_tree_block")
    }

}