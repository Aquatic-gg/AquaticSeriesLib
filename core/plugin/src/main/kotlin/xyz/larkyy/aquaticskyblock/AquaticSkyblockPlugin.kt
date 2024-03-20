package xyz.larkyy.aquaticskyblock

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.event.block.Action
import xyz.larkyy.aquaticfarming.AquaticFarmingInject
import xyz.larkyy.aquaticseries.AquaticSeriesLib
import xyz.larkyy.aquaticseries.block.AquaticBlock
import xyz.larkyy.aquaticseries.block.impl.OraxenBlock
import xyz.larkyy.aquaticseries.block.impl.VanillaBlock
import xyz.larkyy.aquaticseries.interactable.impl.block.BlockInteractable
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
        val blocks: HashMap<Char, AquaticBlock> = hashMapOf(
            'X' to VanillaBlock(Material.DIAMOND_BLOCK.createBlockData()),
            'O' to OraxenBlock("amethyst_ore")
        )
        val shape = BlockShape(
            layers,
            blocks
        )
        customBlock = BlockInteractable("test", {
            Bukkit.broadcastMessage("You have clicked on custom multi block!")
            if (it.originalEvent.action != Action.LEFT_CLICK_BLOCK) {
                it.originalEvent.isCancelled = true
            }
        }, {
            it.originalEvent.player.sendMessage("You have broken the custom block!")
            it.originalEvent.isCancelled = true
            val world = it.blockInteractable.location.world!!
            for (associatedLocation in it.blockInteractable.associatedLocations) {
                world.spawnParticle(
                    Particle.BLOCK_CRACK,
                    associatedLocation,
                    20,
                    0.0,
                    0.0,
                    0.0,
                    associatedLocation.block.blockData
                )
            }
            it.blockInteractable.despawn()
        }, shape)
        customBlock.spawn(player.location)

    }

    override fun onDisable() {

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