package gg.aquatic.aquaticseries.lib.inventory.lib

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import gg.aquatic.aquaticseries.lib.inventory.InventoryPacketListener
import gg.aquatic.aquaticseries.lib.inventory.lib.title.TitleHandler
import gg.aquatic.aquaticseries.lib.nms.InventoryAdapter
import java.util.UUID

object InventoryHandler: IFeature {

    val upcommingTitles = HashMap<UUID,AquaticString>()
    val titleHandler = TitleHandler()
    val inventoryAdapter: InventoryAdapter
        get() {
            return AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!.inventoryAdapter()
        }

    override val type: Features
        get() = Features.INVENTORIES

    override fun initialize(lib: AbstractAquaticSeriesLib) {
        lib.plugin.server.pluginManager.registerEvents(InventoryListeners(),lib.plugin)
        lib.nmsAdapter!!.packetListenerAdapter().registerListener(InventoryPacketListener())
    }

}