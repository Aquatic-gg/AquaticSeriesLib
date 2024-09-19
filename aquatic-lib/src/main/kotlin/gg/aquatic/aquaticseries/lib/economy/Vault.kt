package gg.aquatic.aquaticseries.lib.economy

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.data.DataDriver
import gg.aquatic.aquaticseries.lib.economy.virtual.VirtualEconomyHandler
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import org.bukkit.entity.Player
import java.util.Optional
import java.util.concurrent.CompletableFuture

class Vault(
    driver: DataDriver?,
): IFeature {

    val virtualEconomyHandler = if (driver != null) {
        VirtualEconomyHandler(driver)
    } else {
        null
    }

    override val type: Features = Features.ECONOMY
    val currencies = HashMap<String, Currency>()

    override fun initialize(lib: AbstractAquaticSeriesLib) {
        virtualEconomyHandler?.initialize()
    }

    fun registerEconomy(currency: Currency) {
        currencies += currency.id to currency
    }

    fun get(currency: Currency, player: Player): Optional<Double> {
        if (currency is VirtualCurrency) {
            if (virtualEconomyHandler == null) {
                return Optional.empty()
            }
            val economyPlayer = virtualEconomyHandler.getPlayer(player.uniqueId)
            return Optional.ofNullable(economyPlayer?.balance?.get(currency.id))
        }
        return Optional.of(currency.getBalance(player))
    }

    fun getAsync(currency: Currency, player: Player): CompletableFuture<Optional<Double>> {
        if (currency is VirtualCurrency) {
            if (virtualEconomyHandler == null) {
                return CompletableFuture.completedFuture(Optional.empty())
            }
            return currency.getBalanceOffline(player.uniqueId).thenApply { Optional.ofNullable(it) }
        }
        return CompletableFuture.completedFuture(Optional.of(currency.getBalance(player)))
    }

    fun has(currency: Currency, player: Player, amount: Double): Boolean {
        if (currency is VirtualCurrency) {
            if (virtualEconomyHandler == null) {
                return false
            }
            val economyPlayer = virtualEconomyHandler.getPlayer(player.uniqueId)
            return economyPlayer?.balance?.get(currency.id)?.let { it >= amount } ?: false
        }
        return currency.has(player, amount)
    }
    fun hasAsync(currency: Currency, player: Player, amount: Double): CompletableFuture<Boolean> {
        if (currency is VirtualCurrency) {
            if (virtualEconomyHandler == null) {
                return CompletableFuture.completedFuture(false)
            }
            return currency.hasOffline(player.uniqueId, amount).thenApply { it }
        }
        return CompletableFuture.completedFuture(currency.has(player, amount))
    }
    fun give(currency: Currency, player: Player, amount: Double) {
        if (currency is VirtualCurrency) {
            if (virtualEconomyHandler == null) {
                return
            }
            val economyPlayer = virtualEconomyHandler.getPlayer(player.uniqueId)
            if (economyPlayer != null) {
                economyPlayer.balance[currency.id] = (economyPlayer.balance[currency.id] ?: 0.0) + amount
            }
            return
        }
        currency.give(player, amount)
    }
    fun giveAsync(currency: Currency, player: Player, amount: Double): CompletableFuture<Void> {
        if (currency is VirtualCurrency) {
            if (virtualEconomyHandler == null) {
                return CompletableFuture.completedFuture(null)
            }
            return currency.giveOffline(player.uniqueId, amount)
        }
        currency.give(player, amount)
        return CompletableFuture.completedFuture(null)
    }
    fun take(currency: Currency, player: Player, amount: Double) {
        if (currency is VirtualCurrency) {
            if (virtualEconomyHandler == null) {
                return
            }
            val economyPlayer = virtualEconomyHandler.getPlayer(player.uniqueId)
            if (economyPlayer != null) {
                economyPlayer.balance[currency.id] = (economyPlayer.balance[currency.id] ?: 0.0) - amount
            }
            return
        }
        currency.take(player, amount)
        return
    }
    fun takeAsync(currency: Currency, player: Player, amount: Double): CompletableFuture<Void> {
        if (currency is VirtualCurrency) {
            if (virtualEconomyHandler == null) {
                return CompletableFuture.completedFuture(null)
            }
            return currency.takeOffline(player.uniqueId, amount)
        }
        currency.take(player, amount)
        return CompletableFuture.completedFuture(null)
    }
}