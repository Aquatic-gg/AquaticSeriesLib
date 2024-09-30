package gg.aquatic.aquaticseries.lib.network

import com.google.gson.JsonParser
import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import gg.aquatic.aquaticseries.lib.network.redis.RedisHandler
import gg.aquatic.aquaticseries.lib.network.redis.RedisNetworkSettings
import java.util.concurrent.CompletableFuture

class NetworkPacketListener: IFeature {

    val adapter: NetworkAdapter

    constructor(redisNetworkSettings: RedisNetworkSettings, serverName: String) {
        adapter = RedisHandler(this, redisNetworkSettings, serverName)
    }

    private val registry = HashMap<String, Pair<PacketSerializer<*>,NetworkPacketHandler<*>>>()
    init {
        registerPacket("NETWORK_RESPONSE", NetworkResponsePacket.Serializer, NetworkResponsePacket.Handler)
    }

    //val handlers = mutableMapOf<Class<out NetworkPacket>, NetworkPacketHandler<*>>()

    fun registerPacket(id: String, serializer: PacketSerializer<*>, handler: NetworkPacketHandler<*>) {
        registry[id] = serializer to handler
    }

    fun handle(packet: SignedNetworkPacket): CompletableFuture<NetworkResponse> {
        val id = packet.packet.id
        val pair = registry[id] ?: return CompletableFuture.completedFuture(
            NetworkResponse(
                NetworkResponse.Status.ERROR, null)
        )
        val handler = pair.second
        return handler.handle(packet).thenApply {
            NetworkResponse(NetworkResponse.Status.SUCCESS, it)
        }
    }

    fun serializePacket(json: String): SignedNetworkPacket? {
        val jsonObject = JsonParser.parseString(json).asJsonObject
        if (!jsonObject.has("id")) return null
        val packetId = jsonObject.get("id").asString
        val sentFrom = jsonObject.get("sent-from").asString
        val packetJson = jsonObject.get("packet").asJsonObject

        val serializer = registry[packetId]?.first ?: return null
        val packet = serializer.deserialize(packetJson.toString())
        return SignedNetworkPacket(packet, sentFrom)
    }

    fun deserializePacket(packet: SignedNetworkPacket): String? {
        val jsonObject = com.google.gson.JsonObject()
        jsonObject.addProperty("id", packet.packet.id)
        jsonObject.addProperty("sent-from", packet.sentFrom)

        val packetJson = packet.packet.serialize()
        jsonObject.add("packet",JsonParser.parseString(packetJson))
        return jsonObject.toString()
    }

    override val type: Features = Features.NETWORKING

    override fun initialize(lib: AquaticSeriesLib) {

    }

}