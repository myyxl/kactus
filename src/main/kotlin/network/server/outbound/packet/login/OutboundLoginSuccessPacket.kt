package network.server.outbound.packet.login

import network.server.outbound.packet.OutboundPacket
import java.util.*

class OutboundLoginSuccessPacket(username: String, uuid: UUID) : OutboundPacket() {

    override val packetId: Int = 0x02

    init {
        packet.writeUUID(uuid)
        packet.writeString(username)
    }
}