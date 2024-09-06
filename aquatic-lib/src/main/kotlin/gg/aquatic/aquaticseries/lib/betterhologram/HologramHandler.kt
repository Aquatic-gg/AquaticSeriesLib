package gg.aquatic.aquaticseries.lib.betterhologram

import gg.aquatic.aquaticseries.lib.betterhologram.cache.LocationHolograms
import gg.aquatic.aquaticseries.lib.chunkcache.location.LocationCacheHandler
import org.bukkit.Location

object HologramHandler {

    fun registerHologram(hologram: AquaticHologram) {
        val holder = getOrCreateLocationHolder(hologram.location)
        holder.holograms += hologram
    }

    fun unregisterHologram(hologram: AquaticHologram) {
        val holder = getLocationHolder(hologram.location) ?: return
        holder.holograms -= hologram
        if (holder.holograms.isEmpty()) {
            LocationCacheHandler.unregisterObject(LocationHolograms::class.java, hologram.location)
        }
    }

    fun getHolograms(location: Location): MutableList<AquaticHologram> {
        val holder = getLocationHolder(location) ?: return mutableListOf()
        return holder.holograms
    }

    fun getOrCreateLocationHolder(location: Location): LocationHolograms {
        var obj = getLocationHolder(location)
        if (obj == null) {
            obj = LocationHolograms()
            LocationCacheHandler.registerObject(obj, LocationHolograms::class.java, location)
        }
        return obj
    }

    fun getLocationHolder(location: Location): LocationHolograms? {
        return LocationCacheHandler.getObject(location, LocationHolograms::class.java) as LocationHolograms?
    }

}