package network.packet.server.play

import network.packet.ServerPacket

class SPlayKeepAlivePacket(keepAliveId: Long) : ServerPacket() {

    override val packetId: Int = 0x21

    init {
        packet.writeLong(keepAliveId)
    }
}