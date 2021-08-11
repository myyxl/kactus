package network.client

import network.packet.ClientPacketHandler
import network.stream.PacketInputStream
import network.stream.StreamHelper

class ReadThread(private val clientConnection: ClientConnection) : Thread() {

    init {
        name = "ReadThread${clientConnection.socket.remoteSocketAddress}"
    }

    override fun run() {
            try {
                val sockIn = PacketInputStream(clientConnection.socket.getInputStream())
                while(!isInterrupted) {
                    val length = sockIn.readVarInt()
                    val packetId = sockIn.readVarInt()
                    val packetBuffer = ByteArray(length - StreamHelper().getVarIntLength(packetId))
                    sockIn.readFully(packetBuffer)

                    println("Packet id: 0x${Integer.toHexString(packetId).uppercase()} (Current state: ${clientConnection.clientState})")

                    ClientPacketHandler().handle(packetId, packetBuffer, clientConnection)
                }
                sockIn.close()
            } catch(e: Exception) {
                clientConnection.disconnect()
            }
    }

}