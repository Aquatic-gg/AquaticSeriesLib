package xyz.larkyy.aquaticskyblock

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Consumer
import xyz.larkyy.aquaticfarming.AquaticFarmingInject
import xyz.larkyy.aquaticseries.AquaticSeriesLib
import xyz.larkyy.aquaticseries.Utils
import xyz.larkyy.aquaticseries.block.AquaticBlock
import xyz.larkyy.aquaticseries.block.impl.VanillaBlock
import xyz.larkyy.aquaticseries.interactable.impl.block.BlockInteractable
import xyz.larkyy.aquaticseries.interactable.impl.block.BlockLayer
import xyz.larkyy.aquaticseries.interactable.impl.block.BlockShape
import java.io.File

class AquaticSkyblockPlugin : AbstractAquaticSkyblockPlugin() {

    override fun onLoad() {
    }

    lateinit var customBlock: BlockInteractable

    override fun onEnable() {
        initializeExtensions()

        AquaticSeriesLib.init(this)

        val player = Bukkit.getPlayer("MrLarkyy_")!!
        val layers = hashMapOf(
            -1 to mutableMapOf(
                -3 to "OOO",
                -1 to "X  ",
                0 to "X  ",
                1 to "XXX"
            ),
            3 to mutableMapOf(
                -3 to "OOO",
                -1 to "X  ",
                0 to "X  ",
                1 to "XXX"
            )
        )
        val blocks: HashMap<Char,AquaticBlock> = hashMapOf(
            'X' to VanillaBlock(Material.DIAMOND_BLOCK.createBlockData()),
            'O' to VanillaBlock(Material.EMERALD_BLOCK.createBlockData())
        )
        val shape = BlockShape(
            layers,
            blocks
        )
        customBlock = BlockInteractable(player.location, {
            Bukkit.broadcastMessage("You have clicked on custom multi block!")
        }, shape)
        customBlock.spawn()

    }

    override fun onDisable() {
        customBlock.despawn()
    }

    fun initializeExtensions() {
        dataFolder.mkdirs()
        val extensions = mutableListOf(Extensions.FARMING)
        val folders = HashMap<Extensions, File>()

        extensions.forEach {
            val dataFolder = File(dataFolder, it.id)
            dataFolder.mkdirs()
            folders[it] = dataFolder
        }

        // FARMING
        val folder = folders[Extensions.FARMING]!!
        AquaticFarmingInject(this, folder)
    }

    enum class Extensions(val id: String) {
        FARMING("farming")
    }

}