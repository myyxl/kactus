package network.server.outbound.packet.play

import network.server.outbound.packet.OutboundPacket

class SPlayDisconnectPacket(reason: String) : OutboundPacket() {

    override val packetId: Int = 0x1A
    override var closeConnection: Boolean = true

    init {
        packet.writeString(""" {"text": "$reason"} """)
    }
}