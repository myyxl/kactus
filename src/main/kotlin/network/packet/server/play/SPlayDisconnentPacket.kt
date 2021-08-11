package network.packet.server.play

import network.packet.ServerPacket

class SPlayDisconnectPacket(reason: String) : ServerPacket() {

    override val packetId: Int = 0x1A
    override var closeConnection: Boolean = true

    init {
        packet.writeString(""" {"text": "$reason"} """)
    }
}