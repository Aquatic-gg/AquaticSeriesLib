package gg.aquatic.aquaticseries.lib.network

import gg.aquatic.aquaticseries.lib.network.redis.RedisHandler
import gg.aquatic.aquaticseries.lib.network.redis.RedisNetworkSettings
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus

class NetworkPacketListener {

    val adapter: NetworkAdapter

    constructor(redisNetworkSettings: RedisNetworkSettings) {
        adapter = RedisHandler(this, redisNetworkSettings)
    }

    val handlers = mutableMapOf<Class<NetworkPacket>, NetworkPacketHandler<*>>()

    private var serializerModules: SerializersModule? = null
    private var serializerFormat: Json? = null

    fun registerPacket(clazz: Class<NetworkPacket>, handler: NetworkPacketHandler<*>, serializerModule: SerializersModule) {
        handlers[clazz] = handler
        if (serializerModules == null) {
            serializerModules = serializerModule
        } else {
            serializerModules!!.plus(
                serializerModule)

            serializerFormat = Json { serializersModule = serializerModules!! }
        }
    }

    fun handle(packet: NetworkPacket) {
        val handler = handlers[packet.javaClass] ?: return
        handler.handle(packet)
    }

    fun serializePacket(json: String): NetworkPacket? {
        val format = serializerFormat ?: return null
        return try {
            format.decodeFromString<NetworkPacket>(json)
        } catch (_: Exception) {
            null
        }
    }

    fun deserializePacket(packet: NetworkPacket): String? {
        val format = serializerFormat ?: return null
        return try {
            format.encodeToString(packet)
        } catch (_: Exception) {
            null
        }
    }

}