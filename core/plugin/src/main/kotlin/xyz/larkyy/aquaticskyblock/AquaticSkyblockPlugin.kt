package xyz.larkyy.aquaticskyblock

import com.ticxo.modelengine.api.events.BaseEntityInteractEvent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import xyz.larkyy.aquaticfarming.AquaticFarmingInject
import xyz.larkyy.aquaticseries.AquaticSeriesLib
import xyz.larkyy.aquaticseries.block.AquaticBlock
import xyz.larkyy.aquaticseries.block.impl.OraxenBlock
import xyz.larkyy.aquaticseries.block.impl.VanillaBlock
import xyz.larkyy.aquaticseries.interactable.impl.block.BlockInteractable
import xyz.larkyy.aquaticseries.interactable.impl.block.BlockShape
import xyz.larkyy.aquaticseries.interactable.impl.meg.MEGInteractable
import xyz.larkyy.aquaticseries.interactable.impl.meg.SpawnedMegInteractable
import xyz.larkyy.aquaticseries.item.CustomItem
import xyz.larkyy.aquaticseries.item.CustomItemHandler
import java.io.File

class AquaticSkyblockPlugin : AbstractAquaticSkyblockPlugin() {

    override fun onLoad() {
    }

    lateinit var customBlock: BlockInteractable
    lateinit var customEntity: MEGInteractable

    override fun onEnable() {
        initializeExtensions()

        AquaticSeriesLib.init(this)

        val player = Bukkit.getPlayer("MrLarkyy_")!!
        val layers = hashMapOf(
            -1 to mutableMapOf(
                -3 to "OOO",
                -1 to "  X",
                0 to "X X",
                1 to "XXX"
            ),
            3 to mutableMapOf(
                -3 to "OOO",
                -1 to "  X",
                0 to "  X",
                1 to "OOO"
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
        }, shape)

        val item = CustomItem.create("ORAXEN:amethyst_ore", "Test Block", ArrayList(), 1, -1, HashMap(), ArrayList())
        item.register("test")
        val item2 = CustomItem.create("ORAXEN:amethyst_ore", "Test Entity", ArrayList(), 1, -1, HashMap(), ArrayList())
        item2.register("test2")

        customEntity = MEGInteractable(
            "test2",
            BlockShape(
                mutableMapOf(
                    0 to mutableMapOf(
                        1 to "AAA",
                        0 to "AAA",
                        -1 to "AAA"
                    )
                ),
                mutableMapOf(
                    'A' to VanillaBlock(Material.AIR.createBlockData())
                )
            ),
            "crate6"
        ) {
            if (it.originalEvent.slot == EquipmentSlot.OFF_HAND) return@MEGInteractable
            it.originalEvent.player.sendMessage("You have interacted test2 (${it.originalEvent.action})")
            if (it.originalEvent.action == BaseEntityInteractEvent.Action.ATTACK) {

                if (!it.interactable.loaded) {
                    it.originalEvent.player.sendMessage("The interactable is not loaded yet! You cannot break it")
                    return@MEGInteractable
                }

                it.interactable.despawn()
            }
        }

        item.giveItem(player)
        item2.giveItem(player)

        server.pluginManager.registerEvents(Listeners(), this)
    }

    override fun onDisable() {
        for (spawnedRegistry in AquaticSeriesLib.INSTANCE.interactableHandler.spawnedRegistries) {
            for (mutableEntry in spawnedRegistry.value) {
                for (value in mutableEntry.value.parents.values) {
                    if (value is SpawnedMegInteractable) {
                        value.destroyEntity()
                    }
                }
            }
        }
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

    inner class Listeners : Listener {
        @EventHandler
        fun onInteract(event: PlayerInteractEvent) {
            if (event.action != Action.RIGHT_CLICK_BLOCK) return
            if (event.hand == EquipmentSlot.OFF_HAND) return
            val item = event.item ?: return
            val customItem = CustomItem.get(item) ?: return
            val location = event.clickedBlock?.location?.clone() ?: return
            location.add(event.blockFace.direction)
            location.yaw = event.player.location.yaw
            location.pitch = event.player.location.pitch
            if (customItem.registryId == "test") {
                event.isCancelled = true
                if (!customBlock.canBePlaced(location)) {
                    event.player.sendMessage("You cannot place the block here!")
                    return
                }
                customBlock.spawn(location)
            }
            else if (customItem.registryId == "test2") {
                event.isCancelled = true
                if (!customEntity.canBePlaced(location)) {
                    event.player.sendMessage("You cannot place the block here!")
                    return
                }
                customEntity.spawn(location.add(0.5,0.0,0.5))
            }

        }
    }
}