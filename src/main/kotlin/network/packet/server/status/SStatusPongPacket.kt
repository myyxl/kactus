package network.packet.server.status

import network.packet.ServerPacket

class SStatusPongPacket(payload: Long) : ServerPacket() {

    override val packetId: Int = 0x01
    override var closeConnection: Boolean = true

    init {
        packet.writeLong(payload)
    }
}