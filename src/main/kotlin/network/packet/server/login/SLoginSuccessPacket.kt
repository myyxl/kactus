package network.packet.server.login

import network.packet.ServerPacket
import java.util.*

class SLoginSuccessPacket(username: String, uuid: UUID) : ServerPacket() {

    override val packetId: Int = 0x02

    init {
        packet.writeUUID(uuid)
        packet.writeString(username)
    }
}