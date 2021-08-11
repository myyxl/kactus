package network.client

import network.packet.ServerPacket
import java.io.DataOutputStream
import java.util.*

class WriteThread(private val clientConnection: ClientConnection) : Thread() {

    init {
        name = "WriteThread${clientConnection.socket.remoteSocketAddress}"
    }

    override fun run() {
        try {
            val packetQueue: Queue<ServerPacket> = clientConnection.packetQueue
            val sockOut = DataOutputStream(clientConnection.socket.getOutputStream())
            while(!isInterrupted) {
                if(packetQueue.isEmpty()) continue
                val packet = packetQueue.poll()
                sockOut.write(packet.getBytes())

                if(packet.closeConnection) clientConnection.disconnect()
            }
            sockOut.close()
        } catch(e: Exception) {
            clientConnection.disconnect()
        }
    }
}