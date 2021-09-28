package network.packet.server.play

import network.packet.ServerPacket

class SPlaySpawnPositionPacket() : ServerPacket() {

    override val packetId: Int = 0x4B

    init {
        /*
        packet.writePosition(location)
        packet.writeFloat(0F)*/
    }

}