package network.packet.server.login

import network.packet.ServerPacket

class SLoginDisconnectPacket(reason: String) : ServerPacket() {

    override val packetId: Int = 0x00
    override var closeConnection: Boolean = true

    init {
        packet.writeString(""" {"text": "$reason"} """)
    }
}