package network.packet.client.play

import network.client.ClientConnection
import network.client.ClientState
import network.packet.ClientPacket

class CPlayKeepAlivePacket(override val clientConnection: ClientConnection,
                           packetBuffer: ByteArray) : ClientPacket(clientConnection, packetBuffer) {

    override val packetId: Int = 0x0F
    override val state: ClientState = ClientState.PLAY

    private val keepAliveId: Long

    init {
        stream.apply {
            keepAliveId = readLong()
        }
    }

    override fun onReceive() {
        TODO("Keep Alive Packet")
    }

}