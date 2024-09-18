package gg.aquatic.aquaticseries.lib.economy

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.economy.virtual.VirtualEconomyHandler
import gg.aquatic.aquaticseries.lib.feature.Features
import org.bukkit.entity.Player
import java.util.UUID
import java.util.concurrent.CompletableFuture

class VirtualCurrency(override val id: String) : Currency {

    fun giveOffline(player: UUID, amount: Double): CompletableFuture<Void> {
        val future = CompletableFuture<Void>()
        getBalanceOffline(player).thenApply {
            setOffline(player, it + amount).thenRun {
                future.complete(null)
            }
        }
        return future
    }
    fun setOffline(player: UUID, amount: Double): CompletableFuture<Void> {
        val economyPlayer = handler.getPlayer(player)
        if (economyPlayer != null) {
            economyPlayer.balance[this.id] = amount
            return CompletableFuture.completedFuture(null)
        }
        return handler.setBalance(player, this, amount)
    }

    fun takeOffline(player: UUID, amount: Double): CompletableFuture<Void> {
        val future = CompletableFuture<Void>()
        getBalanceOffline(player).thenApply {
            setOffline(player, it - amount).thenRun {
                future.complete(null)
            }
        }
        return future
    }
    fun getBalanceOffline(player: UUID): CompletableFuture<Double> {
        val economyPlayer = handler.getPlayer(player)
        if (economyPlayer != null) {
            return CompletableFuture.completedFuture((economyPlayer.balance[this.id] ?: 0.0))
        }
        return handler.loadPlayer(player).thenApply { (it.balance[this.id] ?: 0.0) }
    }
    fun hasOffline(player: UUID, amount: Double): CompletableFuture<Boolean> {
        val economyPlayer = handler.getPlayer(player)
        if (economyPlayer != null) {
            return CompletableFuture.completedFuture((economyPlayer.balance[this.id] ?: 0.0) >= amount)
        }
        return handler.loadPlayer(player).thenApply { (it.balance[this.id] ?: 0.0) >= amount }
    }

    fun save(uuid: UUID): CompletableFuture<Void> {
        val economyPlayer = handler.getPlayer(uuid) ?: return CompletableFuture.completedFuture(null)
        return handler.savePlayer(economyPlayer)
    }

    private val handler: VirtualEconomyHandler
        get() {
            return AbstractAquaticSeriesLib.INSTANCE.features[Features.VIRTUAL_ECONOMY]!! as VirtualEconomyHandler
        }

    override fun give(player: Player, amount: Double) {
        val economyPlayer = handler.getPlayer(player.uniqueId)
        if (economyPlayer != null) {
            economyPlayer.balance[this.id] = (economyPlayer.balance[this.id] ?: 0.0) + amount
            return
        }
    }

    override fun take(player: Player, amount: Double) {
        val economyPlayer = handler.getPlayer(player.uniqueId)
        if (economyPlayer != null) {
            economyPlayer.balance[this.id] = (economyPlayer.balance[this.id] ?: 0.0) - amount
            return
        }
    }

    override fun set(player: Player, amount: Double) {
        val economyPlayer = handler.getPlayer(player.uniqueId)
        if (economyPlayer != null) {
            economyPlayer.balance[this.id] = amount
        }
    }

    override fun getBalance(player: Player): Double {
        val economyPlayer = handler.getPlayer(player.uniqueId)
        if (economyPlayer != null) {
            return (economyPlayer.balance[this.id] ?: 0.0)
        }
        return 0.0
    }

    override fun has(player: Player, amount: Double): Boolean {
        val economyPlayer = handler.getPlayer(player.uniqueId)
        if (economyPlayer != null) {
            return (economyPlayer.balance[this.id] ?: 0.0) >= amount
        }
        return false
    }
}