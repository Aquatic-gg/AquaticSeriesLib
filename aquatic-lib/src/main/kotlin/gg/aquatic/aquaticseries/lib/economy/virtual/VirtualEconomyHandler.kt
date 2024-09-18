package gg.aquatic.aquaticseries.lib.economy.virtual

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.data.DataDriver
import gg.aquatic.aquaticseries.lib.economy.EconomyPlayer
import gg.aquatic.aquaticseries.lib.economy.VirtualCurrency
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import java.util.UUID
import java.util.concurrent.CompletableFuture

class VirtualEconomyHandler(
    driver: DataDriver
): IFeature {

    override val type = Features.VIRTUAL_ECONOMY
    override fun initialize(lib: AbstractAquaticSeriesLib) {

    }

    var initialized: Boolean = false
        private set

    val currencyDriver = CurrencyDriver(driver).apply {
        this.initialize().thenRun {
            initialized = true
        }
    }

    val currencies = HashMap<String, VirtualCurrency>()

    fun register(currency: VirtualCurrency) {
        currencies += currency.id to currency
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