package network

import network.client.ClientConnection
import minecraft.MinecraftServer
import java.net.InetAddress
import java.net.ServerSocket

class Server(bindInterface: String, bindPort: Int) : Thread() {

    private val serverSock = ServerSocket(bindPort, 0, InetAddress.getByName(bindInterface))
    private val connections = ArrayList<ClientConnection>()

    private val mcServer = MinecraftServer()

    override fun run() {
        mcServer.start()
        while(true) {
            val connection = ClientConnection(serverSock.accept(), mcServer)
            connections.add(connection)
        }
    }





}





















