package gg.aquatic.aquaticseries.lib.fake

import org.bukkit.Location

class FakeObjectRegistry {

    val blocks = HashMap<String,HashMap<String,HashMap<String,MutableList<PacketBlock>>>>()
    val entities = HashMap<String,HashMap<String,ArrayList<Int>>>()

    val entitiesMapper = HashMap<Int,PacketEntity>()

    fun registerEntity(packetEntity: PacketEntity) {
        val loc = packetEntity.location
        val chunk = loc.chunk

        val worldMap = entities.getOrPut(loc.world!!.name) { HashMap() }
        val chunkMap = worldMap.getOrPut("${chunk.x};${chunk.z}") { ArrayList() }
        chunkMap += packetEntity.entityId
        entitiesMapper += packetEntity.entityId to packetEntity
    }

    fun registerBlock(packetBlock: PacketBlock) {
        val loc = packetBlock.location
        val chunk = loc.chunk

        val worldMap = blocks.getOrPut(loc.world!!.name) { HashMap() }
        val chunkMap = worldMap.getOrPut("${chunk.x};${chunk.z}") { HashMap() }
        val blockList = chunkMap.getOrPut("${loc.blockX};${loc.blockY};${loc.blockZ}") { ArrayList() }
        blockList += packetBlock
        //chunkMap += "${loc.blockX};${loc.blockY};${loc.blockZ}" to packetBlock
    }

    fun unregisterBlock(loc: Location) {
        val chunk = loc.chunk

        val worldMap = blocks[loc.world!!.name] ?: return
        val chunkMap = worldMap["${chunk.x};${chunk.z}"] ?: return
        chunkMap -= "${loc.blockX};${loc.blockY};${loc.blockZ}"

        if (chunkMap.isEmpty()) {
            worldMap.remove("${chunk.x};${chunk.z}")
        }
        if (worldMap.isEmpty()) {
            blocks.remove(loc.world!!.name)
        }
    }

    fun unregisterEntity(loc: Location, id: Int) {
        val chunk = loc.chunk

        val worldMap = entities[loc.world!!.name] ?: return
        val chunkMap = worldMap["${chunk.x};${chunk.z}"] ?: return
        chunkMap -= id
        entitiesMapper -= id

        if (chunkMap.isEmpty()) {
            worldMap.remove("${chunk.x};${chunk.z}")
        }
        if (worldMap.isEmpty()) {
            entities.remove(loc.world!!.name)
        }
    }

    fun getBlocks(loc: Location): MutableList<PacketBlock> {
        val chunk = loc.chunk

        val worldMap = blocks[loc.world!!.name] ?: return mutableListOf()
        val chunkMap = worldMap["${chunk.x};${chunk.z}"] ?: return mutableListOf()
        return chunkMap["${loc.blockX};${loc.blockY};${loc.blockZ}"] ?: return mutableListOf()
    }

}