package gg.aquatic.aquaticseries.lib.accessory

import org.bukkit.entity.Player

class AccessoryHolder(
    player: Player
) {

    val uuid = player.uniqueId

    // Namespace, Accessory Class, ID, Applied Accessory
    val appliedAccessories = HashMap<String, HashMap<Class<out Accessory>, HashMap<String, AppliedAccessory>>>()

    fun applyAccessory(appliedAccessory: AppliedAccessory) {
        val accessory = appliedAccessory.accessory
        val namespace = accessory.namespace
        val id = accessory.id
        appliedAccessories.computeIfAbsent(namespace) { HashMap() }
            .computeIfAbsent(accessory.javaClass) { HashMap() }[id] = appliedAccessory
    }

    fun removeAccessory(appliedAccessory: AppliedAccessory) {
        val accessory = appliedAccessory.accessory
        val namespace = accessory.namespace
        val id = accessory.id
        appliedAccessories[namespace]?.get(accessory.javaClass)?.remove(id)
    }

    fun hasAccessory(accessory: Accessory): Boolean {
        val namespace = accessory.namespace
        val id = accessory.id
        return appliedAccessories[namespace]?.get(accessory.javaClass)?.containsKey(id) ?: false
    }

    fun hasAccessory(clazz: Class<out Accessory>): Boolean {
        return appliedAccessories.values.any { it.containsKey(clazz) }
    }

    fun getAccessories(namespace: String): HashMap<String,AppliedAccessory> {
        val namespaceMap = appliedAccessories[namespace] ?: return HashMap()
        val applied = HashMap<String,AppliedAccessory>()
        namespaceMap.forEach { (_, map) ->
            applied.putAll(map)
        }
        return applied
    }

}