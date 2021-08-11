package network.packet.server.status

import network.packet.ServerPacket

class SStatusResponsePacket(serverStatus: String) : ServerPacket() {

    override val packetId: Int = 0x00

    init {
        packet.writeString(serverStatus)
    }
}