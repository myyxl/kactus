package network.server.outbound.packet.login

import network.server.outbound.packet.OutboundPacket

class OutboundLoginDisconnectPacket(reason: String) : OutboundPacket() {

    override val packetId: Int = 0x00
    override var closeConnection: Boolean = true

    init {
        packet.writeString(""" {"text": "$reason"} """)
    }
}