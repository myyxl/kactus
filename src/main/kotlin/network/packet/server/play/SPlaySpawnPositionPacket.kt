package network.packet.server.play

import minecraft.world.Location
import network.packet.ServerPacket

class SPlaySpawnPositionPacket(location: Location) : ServerPacket() {

    override val packetId: Int = 0x4B

    init {
        packet.writePosition(location)
        packet.writeFloat(0F)
    }

}