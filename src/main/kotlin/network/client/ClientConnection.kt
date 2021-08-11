package network.client

import minecraft.MinecraftServer
import network.packet.ServerPacket
import java.net.Socket
import java.util.*

class ClientConnection(val socket: Socket, val mcServer: MinecraftServer) {

    @Volatile var packetQueue = LinkedList<ServerPacket>()
    @Volatile var clientState = ClientState.HANDSHAKE

    private val readThread = ReadThread(this)
    private val writeThread = WriteThread(this)

    init {
        readThread.start()
        writeThread.start()
    }

    @Synchronized fun sendPacket(packet: ServerPacket) {
        packetQueue.add(packet)
    }

    @Synchronized fun disconnect() {
        if(socket.isClosed) return

        readThread.interrupt()
        writeThread.interrupt()
        socket.close()
        mcServer.removeOnlinePlayer(this)
    }

}
