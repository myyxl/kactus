package network.packet.client.handshake

import network.client.ClientConnection
import network.client.ClientState
import network.packet.ClientPacket

class CHandshakePacket(override val clientConnection: ClientConnection,
                       packetBuffer: ByteArray) : ClientPacket(clientConnection, packetBuffer) {

    override val packetId = 0x00
    override val state = ClientState.HANDSHAKE

    private var protocolVersion: Int
    private var serverAddress: String
    private var serverPort: UShort
    private var nextState: Int

    init {
        stream.apply {
            protocolVersion = readVarInt()
            serverAddress = readString()
            serverPort = readUnsignedShort().toUShort()
            nextState = readVarInt()
        }
    }

    override fun onReceive() {
        when(nextState) {
            1 -> clientConnection.clientState = ClientState.STATUS
            2 -> clientConnection.clientState = ClientState.LOGIN
        }
    }
}