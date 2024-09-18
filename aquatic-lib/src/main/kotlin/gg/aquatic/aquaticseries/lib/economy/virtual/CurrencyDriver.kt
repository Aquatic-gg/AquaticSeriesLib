package gg.aquatic.aquaticseries.lib.economy.virtual

import gg.aquatic.aquaticseries.lib.economy.EconomyPlayer
import gg.aquatic.aquaticseries.lib.economy.VirtualCurrency
import java.util.UUID
import java.util.concurrent.CompletableFuture

interface CurrencyDriver {

    fun get(uuid: UUID): CompletableFuture<EconomyPlayer>
    fun get(uuid: UUID, currency: VirtualCurrency): CompletableFuture<Double>
    fun set(currency: VirtualCurrency, amount: Double): CompletableFuture<Void>
    fun set(economyPlayer: EconomyPlayer): CompletableFuture<Void>

}