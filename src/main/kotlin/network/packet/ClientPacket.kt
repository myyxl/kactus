package network.packet

import network.client.ClientConnection
import network.client.ClientState
import network.stream.PacketInputStream
import java.io.ByteArrayInputStream

abstract class ClientPacket(open val clientConnection: ClientConnection, packetBuffer: ByteArray) {

    abstract val packetId: Int
    abstract val state: ClientState

    val stream = PacketInputStream(ByteArrayInputStream(packetBuffer))

    abstract fun onReceive()

}