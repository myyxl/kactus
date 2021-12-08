package network.server.outbound.packet.play

import network.server.outbound.packet.OutboundPacket

class OutboundPlaySpawnPositionPacket() : OutboundPacket() {

    override val packetId: Int = 0x4B

    init {
        /*
        packet.writePosition(location)
        packet.writeFloat(0F)*/
    }

}