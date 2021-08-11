package network.packet

import network.client.ClientConnection
import network.client.ClientState
import network.packet.client.handshake.CHandshakePacket
import network.packet.client.login.CLoginStartPacket
import network.packet.client.play.CPlayKeepAlivePacket
import network.packet.client.status.CStatusPingPacket
import network.packet.client.status.CStatusRequestPacket
import network.stream.PacketInputStream


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
                Not working because buffer is given and
                every packet tries to read from it and fails
                if not the correct packet is on top of the list.
                Because the (wrong) packet expects a datatype which is not in
                the given buffer because it wasn't intended for the
                specific packet. Thus, it will hang and wait for the value.
         */

        packetList.forEach {
            println("handle() loop: ${it.simpleName}")

            val packet = it.java.getConstructor(ClientConnection::class.java,
                            PacketInputStream::class.java).newInstance(clientConnection, packetBuffer)

            if(clientConnection.clientState == packet.state && packetId == packet.packetId) {
                packet.onReceive()
                print("Object state: " + packet.state)
                return
            }
        }
        println("test")
    }
}