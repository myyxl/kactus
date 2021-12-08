package network.server.outbound.packet.status

import network.server.outbound.packet.OutboundPacket

class OutboundStatusResponsePacket(serverStatus: String) : OutboundPacket() {

    override val packetId: Int = 0x00

    init {
        packet.writeString(serverStatus)
    }
}