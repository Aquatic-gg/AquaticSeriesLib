package xyz.larkyy.aquaticfarming.harvestable

import org.bukkit.scheduler.BukkitRunnable
import xyz.larkyy.aquaticfarming.AbstractAquaticFarming

class HarvestableTicker : BukkitRunnable(){
    override fun run() {
        AbstractAquaticFarming.instance?.harvestableManager?.tickHarvestables()
    }
}