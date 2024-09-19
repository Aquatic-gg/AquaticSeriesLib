package gg.aquatic.aquaticseries.lib.economy.virtual

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.data.DataDriver
import gg.aquatic.aquaticseries.lib.economy.EconomyPlayer
import gg.aquatic.aquaticseries.lib.economy.VirtualCurrency
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import gg.aquatic.aquaticseries.lib.util.event
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.UUID
import java.util.concurrent.CompletableFuture

class VirtualEconomyHandler(
    driver: DataDriver
) {
    val currencyDriver = CurrencyDriver(driver)

    var initialized: Boolean = false
        private set

    val cache = HashMap<UUID, EconomyPlayer>()

    fun initialize() {
        currencyDriver.initialize().thenRun {
            initialized = true
        }

        loadPlayers(*Bukkit.getOnlinePlayers().map { it.uniqueId }.toTypedArray()).thenAccept {
            cache.putAll(it)
        }

        event<PlayerJoinEvent>(ignoredCancelled = true) {
            loadPlayer(it.player.uniqueId).thenAccept {  ep ->
                cache[ep.uuid] = ep
            }
        }

        event<PlayerQuitEvent>(ignoredCancelled = true) {
            savePlayer(cache[it.player.uniqueId] ?: return@event).thenRun {
                cache.remove(it.player.uniqueId)
            }
        }
    }

    fun getPlayer(uuid: UUID): EconomyPlayer? {
        return cache[uuid]
    }

    fun loadPlayer(uuid: UUID): CompletableFuture<EconomyPlayer> {
        return currencyDriver.get(uuid)
    }

    fun loadPlayers(vararg uuids: UUID): CompletableFuture<Map<UUID, EconomyPlayer>> {
        return currencyDriver.getAll(uuids.toSet())
    }

    fun savePlayer(player: EconomyPlayer): CompletableFuture<Void> {
        return currencyDriver.set(player)
    }

    fun savePlayers(vararg players: EconomyPlayer): CompletableFuture<Void> {
        return currencyDriver.set(*players)
    }

    fun getBalance(uuid: UUID, currency: VirtualCurrency): CompletableFuture<Double> {
        return currencyDriver.get(uuid, currency)
    }

    fun setBalance(uuid: UUID, currency: VirtualCurrency, amount: Double): CompletableFuture<Void> {
        return currencyDriver.set(uuid, currency, amount)
    }

}