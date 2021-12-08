package network.server.outbound.packet.status

import network.server.outbound.packet.OutboundPacket

class OutboundStatusPongPacket(payload: Long) : OutboundPacket() {

    override val packetId: Int = 0x01
    override var closeConnection: Boolean = true

    init {
        packet.byteBuf.writeLong(payload)
    }
}