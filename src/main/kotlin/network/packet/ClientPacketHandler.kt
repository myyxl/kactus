package network.packet

import network.client.ClientConnection
import network.client.ClientState
import network.packet.client.handshake.CHandshakePacket
import network.packet.client.login.CLoginStartPacket
import network.packet.client.play.CPlayKeepAlivePacket
import network.packet.client.status.CStatusPingPacket
import network.packet.client.status.CStatusRequestPacket


class ClientPacketHandler {

    private val packetList = listOf(
        CHandshakePacket::class,
        CStatusRequestPacket::class,
        CStatusPingPacket::class,
        CLoginStartPacket::class,
        CPlayKeepAlivePacket::class
    )

    @Synchronized fun handle(packetId: Int, packetBuffer: ByteArray, clientConnection: ClientConnection) {
        /*
                Not working because stream/buffer is given and
                every packet tries to read from it and fails
                if not the correct packet is on top of the list
         */

        /*packetList.forEach {
            println("handle() loop: ${it.simpleName}")

            var dummyArray = ByteArray(packetBuffer.size) {0}
            val dummyObject = it.java.getConstructor(ClientConnection::class.java,
                                                    PacketInputStream::class.java).newInstance(clientConnection, dummyArray)

            if(clientConnection.clientState == dummyObject.state && packetId == dummyObject.packetId) {
                val packet = it.java.getConstructor(ClientConnection::class.java,
                                                    PacketInputStream::class.java).newInstance(clientConnection, packetBuffer)
                packet.onReceive()
                print("Object state: " + packet.state)
                return
            }
        }
        println("test")*/


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