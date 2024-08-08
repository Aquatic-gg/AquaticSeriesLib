package gg.aquatic.aquaticseries

import org.bukkit.plugin.java.JavaPlugin

abstract class AbstractAquaticPlugin: JavaPlugin() {

    val injections = ArrayList<AbstractAquaticModuleInject>()

    fun injectAll() {
        for (injection in injections) {
            injection.inject()
        }
    }

    fun ejectAll() {
        for (injection in injections) {
            injection.eject()
        }
    }
}