package xyz.larkyy.aquaticfarming

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.event.block.Action
import xyz.larkyy.aquaticfarming.crop.CropRegistryManager
import xyz.larkyy.aquaticfarming.farmland.CropFarmland
import xyz.larkyy.aquaticfarming.farmland.Farmland
import xyz.larkyy.aquaticfarming.harvestable.HarvestableManager
import xyz.larkyy.aquaticfarming.harvestable.HarvestableTicker
import xyz.larkyy.aquaticfarming.harvestable.stage.TreeStage
import xyz.larkyy.aquaticfarming.harvestable.tree.TreeHarvestable
import xyz.larkyy.aquaticseries.AbstractAquaticModuleInject
import xyz.larkyy.aquaticseries.block.impl.OraxenBlock
import xyz.larkyy.aquaticseries.block.impl.VanillaBlock
import xyz.larkyy.aquaticseries.interactable.impl.block.BlockInteractable
import xyz.larkyy.aquaticseries.interactable.impl.block.BlockShape
import xyz.larkyy.aquaticseries.item.CustomItem

class AquaticFarming(injection: AbstractAquaticModuleInject) : AbstractAquaticFarming(injection) {

    override val cropRegistryManager: AbstractCropRegistryManager = CropRegistryManager()
    override val harvestableManager: HarvestableManager = HarvestableManager()
    init {
        instance = this
        Bukkit.getScheduler().runTaskLater(injection.plugin, Runnable {
            onEnable()
        }, 1 )
    }

    private fun onEnable() {
        Bukkit.broadcastMessage("Loading")
        harvestableManager.registerListeners(injection.plugin)
        HarvestableTicker().runTaskTimer(injection.plugin, 5, 5)

        val stage1 = BlockInteractable("test_tree_st1", {
            Bukkit.broadcastMessage("You have clicked on custom multi block!")
            if (it.originalEvent.action != Action.LEFT_CLICK_BLOCK) {
                it.originalEvent.isCancelled = true
            }
        }, {
            it.originalEvent.isCancelled = true
            if (!it.blockInteractable.loaded) {
                it.originalEvent.player.sendMessage("The interactable is not loaded yet! You cannot break it")
                return@BlockInteractable
            }
            it.originalEvent.player.sendMessage("You have broken the custom block!")
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
        }, BlockShape(
            hashMapOf(
                0 to mutableMapOf(
                    0 to "X",
                )
            ),
            hashMapOf(
                'X' to VanillaBlock(Material.OAK_SAPLING.createBlockData()),
            )
        ))

        val stage2 = BlockInteractable("test_tree_st2", {
            Bukkit.broadcastMessage("You have clicked on custom multi block!")
            if (it.originalEvent.action != Action.LEFT_CLICK_BLOCK) {
                it.originalEvent.isCancelled = true
            }
        }, {
            it.originalEvent.isCancelled = true
            if (!it.blockInteractable.loaded) {
                it.originalEvent.player.sendMessage("The interactable is not loaded yet! You cannot break it")
                return@BlockInteractable
            }
            it.originalEvent.player.sendMessage("You have broken the custom block!")
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
        }, BlockShape(
            hashMapOf(
                0 to mutableMapOf(
                    0 to "X",
                ),
                1 to mutableMapOf(
                    0 to "X",
                ),
                2 to mutableMapOf(
                    -1 to "OOO",
                    0 to "OXO",
                    1 to "OOO",
                ),
                3 to mutableMapOf(
                    -1 to "O",
                    0 to "OOO",
                    1 to "O",
                ),
            ),
            hashMapOf(
                'X' to VanillaBlock(Material.DIAMOND_BLOCK.createBlockData()),
                'O' to OraxenBlock("amethyst_ore")
            )
        ))

        val stage3 = BlockInteractable("test_tree_st3", {
            Bukkit.broadcastMessage("You have clicked on custom multi block!")
            if (it.originalEvent.action != Action.LEFT_CLICK_BLOCK) {
                it.originalEvent.isCancelled = true
            }
        }, {
            it.originalEvent.isCancelled = true
            if (!it.blockInteractable.loaded) {
                it.originalEvent.player.sendMessage("The interactable is not loaded yet! You cannot break it")
                return@BlockInteractable
            }
            it.originalEvent.player.sendMessage("You have broken the custom block!")
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
        }, BlockShape(
            hashMapOf(
                0 to mutableMapOf(
                    0 to "X",
                ),
                1 to mutableMapOf(
                    0 to "X",
                ),
                2 to mutableMapOf(
                    0 to "X",
                ),
                3 to mutableMapOf(
                    -2 to "OOOOO",
                    -1 to "OOOOO",
                    0 to "OOXOO",
                    1 to "OOOOO",
                    2 to "OOOOO"
                ),
                4 to mutableMapOf(
                    -2 to "OOOOO",
                    -1 to "OOOOO",
                    0 to "OOXOO",
                    1 to "OOOOO",
                    2 to "OOOOO"
                ),
                5 to mutableMapOf(
                    -1 to "O",
                    0 to "OOO",
                    1 to "O",
                ),
                6 to mutableMapOf(
                    -1 to "O",
                    0 to "OOO",
                    1 to "O",
                )
            ),
            hashMapOf(
                'X' to VanillaBlock(Material.DIAMOND_BLOCK.createBlockData()),
                'O' to OraxenBlock("amethyst_ore")
            )
        ))

        val seed = CustomItem.create("STONE", "&fTest Tree", null, 1, -1, null, null)
        val tree = TreeHarvestable(
            "test",
            seed,
            arrayListOf(CropFarmland(Farmland("test", VanillaBlock(Material.GRASS_BLOCK.createBlockData())), 1.0)),
            0.0,
            ArrayList(),
            ArrayList(),
            arrayListOf(
                TreeStage(
                    0,
                    stage1,
                    3
                ),
                TreeStage(
                    1,
                    stage2,
                    3
                ),
                TreeStage(
                    2,
                    stage3,
                    3
                )
            )
        )
        harvestableManager.harvestables["test"] = tree

        val player = Bukkit.getPlayer("MrLarkyy_")!!
        tree.giveSeed(player,1)
    }


    override fun onDisable() {
        instance = null
    }
}