package network.packet.client.login

import network.client.ClientConnection
import network.client.ClientState
import network.packet.ClientPacket
import network.packet.server.login.SLoginDisconnectPacket

class CLoginStartPacket(override val clientConnection: ClientConnection,
                        packetBuffer: ByteArray) : ClientPacket(clientConnection, packetBuffer) {

    override val packetId: Int = 0x00
    override val state: ClientState = ClientState.LOGIN

    private val username: String

    init {
        stream.apply {
            username = readString()
        }
    }

    override fun onReceive() {
        if(username.length > 16) {
            val responsePacket = SLoginDisconnectPacket("Username too long!")
            clientConnection.sendPacket(responsePacket)
            return
        }
        clientConnection.mcServer.initializeNewPlayer(clientConnection, username)
    }
}