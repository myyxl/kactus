package network.packet.client.status

import network.client.ClientConnection
import network.client.ClientState
import network.packet.ClientPacket
import network.packet.server.status.SStatusResponsePacket

class CStatusRequestPacket(override val clientConnection: ClientConnection,
                           packetBuffer: ByteArray) : ClientPacket(clientConnection, packetBuffer) {

    override val packetId: Int = 0x00
    override val state: ClientState = ClientState.STATUS

    override fun onReceive() {
        val mcServer = clientConnection.mcServer
        val currentPlayers = mcServer.onlinePlayers.size
        val responsePacket = SStatusResponsePacket("""
            {"version": 
                {"name": "${mcServer.gameVersion}","protocol": ${mcServer.protocolVersion}},
                "players": {"max": ${mcServer.maxPlayers},"online": $currentPlayers},
                "description": {"text": "${mcServer.serverMotd}"}}""".trimIndent())
        clientConnection.sendPacket(responsePacket)
    }
}