package gg.aquatic.aquaticseries.lib.network.tcp

class TCPNetworkSettings(
    val port: Int,
    val servers: HashMap<String, TCPServerSettings>
) {

    class TCPServerSettings(
        val name: String,
        val ip: String,
        val port: Int,
    )

}