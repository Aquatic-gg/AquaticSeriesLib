package gg.aquatic.aquaticseries.lib.worldobject.`object`

import gg.aquatic.aquaticseries.lib.worldobject.WorldObjectSerializer

abstract class PersistentWorldObject: WorldObject() {

    abstract val serializer: WorldObjectSerializer<*>

}