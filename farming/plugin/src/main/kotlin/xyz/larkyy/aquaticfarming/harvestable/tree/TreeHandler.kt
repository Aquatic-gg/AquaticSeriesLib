package xyz.larkyy.aquaticfarming.harvestable.tree

import com.google.gson.Gson
import com.jeff_media.customblockdata.CustomBlockData
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import xyz.larkyy.aquaticfarming.AbstractAquaticFarming
import xyz.larkyy.aquaticfarming.event.TreeBlockBreakEvent
import xyz.larkyy.aquaticfarming.harvestable.TreeBlockData
import xyz.larkyy.aquaticseries.call
import xyz.larkyy.aquaticseries.chance.ChanceUtils

class TreeHandler {

    fun handleBlockBreak(data: TreeBlockData, event: BlockBreakEvent) {
        val tree = AbstractAquaticFarming.instance!!.harvestableManager.harvestables[data.treeId]
        if (tree !is TreeHarvestable) return
        TreeBlockBreakEvent(data, event).call()
        event.isDropItems = false
        Bukkit.broadcastMessage("You have broken the tree block! ${data.ingredient}")
        val cbd = CustomBlockData(event.block,AbstractAquaticFarming.instance!!.injection.plugin)
        cbd.remove(TreeBlockData.NAMESPACEKEY)

        val loottables = tree.loottables[data.ingredient] ?: return
        val loottable = ChanceUtils.getRandomItem(loottables) ?: return
        val dropsAmount = loottable.dropsAmount.getRandomAmount
        for (i in 0..<dropsAmount) {
            val drop = ChanceUtils.getRandomItem(loottable.drops) ?: continue
            val amt = drop.amount.getRandomAmount
            val item = drop.item.getItem().clone()
            item.amount = amt
            event.block.location.world!!.dropItem(event.block.location.clone().add(0.5,0.5,0.5),item)
        }
    }

    fun registerListeners(plugin: JavaPlugin) {
        plugin.server.pluginManager.registerEvents(Listeners(),plugin)
    }

    inner class Listeners : Listener {

        @EventHandler
        fun onBlockBreak(event: BlockBreakEvent) {
            val block = event.block
            val cbd = CustomBlockData(block, AbstractAquaticFarming.instance!!.injection.plugin)
            val treeData: TreeBlockData? = Gson().fromJson(
                cbd.get(TreeBlockData.NAMESPACEKEY, PersistentDataType.STRING),
                TreeBlockData::class.java
            )
            if (treeData != null) {
                handleBlockBreak(treeData, event)
            }
        }

    }

}