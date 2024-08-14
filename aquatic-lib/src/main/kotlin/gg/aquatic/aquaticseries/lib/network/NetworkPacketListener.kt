package gg.aquatic.aquaticseries.lib.network

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import gg.aquatic.aquaticseries.lib.network.redis.RedisHandler
import gg.aquatic.aquaticseries.lib.network.redis.RedisNetworkSettings
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus
import java.util.concurrent.CompletableFuture

class NetworkPacketListener: IFeature {

    val adapter: NetworkAdapter

    constructor(redisNetworkSettings: RedisNetworkSettings, serverName: String) {
        adapter = RedisHandler(this, redisNetworkSettings, serverName)
    }

    val handlers = mutableMapOf<Class<out NetworkPacket>, NetworkPacketHandler<*>>()

    private var serializerModules: SerializersModule? = null
    private var serializerFormat: Json? = null

    fun registerPacket(clazz: Class<out NetworkPacket>, handler: NetworkPacketHandler<*>, serializerModule: SerializersModule) {
        handlers[clazz] = handler
        if (serializerModules == null) {
            serializerModules = serializerModule
        } else {
            serializerModules!!.plus(
                serializerModule)

            serializerFormat = Json { serializersModule = serializerModules!! }
        }
    }

    fun handle(packet: SignedNetworkPacket): CompletableFuture<NetworkResponse> {
        val handler = handlers[packet.packet.javaClass] ?: return CompletableFuture.completedFuture(
            NetworkResponse(
                NetworkResponse.Status.ERROR, null) )
        return handler.handle(packet).thenApply {
            NetworkResponse(NetworkResponse.Status.SUCCESS, it)
        }
    }

    fun serializePacket(json: String): SignedNetworkPacket? {
        val format = serializerFormat ?: return null
        return try {
            format.decodeFromString<SignedNetworkPacket>(json)
        } catch (_: Exception) {
            null
        }
    }

    fun deserializePacket(packet: SignedNetworkPacket): String? {
        val format = serializerFormat ?: return null
        return try {
            format.encodeToString(packet)
        } catch (_: Exception) {
            null
        }
    }

    override val type: Features = Features.NETWORKING

    override fun initialize(lib: AbstractAquaticSeriesLib) {

    }

}