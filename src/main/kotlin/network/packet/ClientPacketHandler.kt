package network.packet

import network.client.ClientConnection
import network.client.ClientState
import network.packet.client.handshake.CHandshakePacket
import network.packet.client.login.CLoginStartPacket
import network.packet.client.play.CPlayKeepAlivePacket
import network.packet.client.status.CStatusPingPacket
import network.packet.client.status.CStatusRequestPacket


class ClientPacketHandler {

    @Synchronized fun handle(packetId: Int, packetBuffer: ByteArray, clientConnection: ClientConnection) {
        when(clientConnection.clientState) {

            ClientState.HANDSHAKE -> when(packetId) {
                0x00 -> CHandshakePacket(clientConnection, packetBuffer).onReceive()
            }

            ClientState.STATUS -> when(packetId) {
                0x00 -> CStatusRequestPacket(clientConnection, packetBuffer).onReceive()
                0x01 -> CStatusPingPacket(clientConnection, packetBuffer).onReceive()
            }

            ClientState.LOGIN -> when(packetId) {
                0x00 -> CLoginStartPacket(clientConnection, packetBuffer).onReceive()
            }

            ClientState.PLAY -> when(packetId) {
                0x0F -> CPlayKeepAlivePacket(clientConnection, packetBuffer).onReceive()
            }

        }
    }
}