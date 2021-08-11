package network.packet.client.status

import network.client.ClientConnection
import network.client.ClientState
import network.packet.ClientPacket
import network.packet.server.status.SStatusPongPacket

class CStatusPingPacket(override val clientConnection: ClientConnection,
                        packetBuffer: ByteArray) : ClientPacket(clientConnection, packetBuffer) {

    override val packetId: Int = 0x01
    override val state: ClientState = ClientState.STATUS

    private val payload: Long

    init {
        stream.apply {
            payload = readLong()
        }
    }

    override fun onReceive() {
        val responsePacket = SStatusPongPacket(payload)
        clientConnection.sendPacket(responsePacket)
    }
}