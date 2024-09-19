package gg.aquatic.aquaticseries.lib.util

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.economy.EconomyPlayer
import gg.aquatic.aquaticseries.lib.economy.Vault
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.nms.NMSAdapter
import org.bukkit.entity.Player
import java.util.Optional
import java.util.concurrent.CompletableFuture

fun Player.modifyChatTabCompletion(action: NMSAdapter.TabCompletionAction, list: List<String>) {
    AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!.modifyTabCompletion(action, list, this)
}

fun Player.getEconomyPlayer(): EconomyPlayer? {
    val feature = AbstractAquaticSeriesLib.INSTANCE.features[Features.ECONOMY] ?: return null
    val vault = feature as Vault
    return vault.virtualEconomyHandler?.getPlayer(this.uniqueId)
}

fun Player.getEconomyPlayerAsync(): CompletableFuture<Optional<EconomyPlayer>> {
    val feature =
        AbstractAquaticSeriesLib.INSTANCE.features[Features.ECONOMY] ?: return CompletableFuture.completedFuture(
            Optional.empty()
        )
    val vault = feature as Vault
    val syncEconomyPlayer = getEconomyPlayer()
    if (syncEconomyPlayer != null) {
        return CompletableFuture.completedFuture(Optional.of(syncEconomyPlayer))
    }
    val virtual = vault.virtualEconomyHandler ?: return CompletableFuture.completedFuture(Optional.empty())
    return virtual.loadPlayer(this.uniqueId).thenApply { Optional.ofNullable(it) }
}