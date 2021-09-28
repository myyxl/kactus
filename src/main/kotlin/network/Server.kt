package network

import network.client.ClientConnection
import minecraft.MinecraftServer
import java.net.InetAddress
import java.net.ServerSocket

class Server(bindInterface: String, bindPort: Int, private val minecraftServer: MinecraftServer) : Thread() {

    private val serverSock = ServerSocket(bindPort, 0, InetAddress.getByName(bindInterface))
    private val connections = ArrayList<ClientConnection>()

    override fun run() {
        while(true) {
            val connection = ClientConnection(serverSock.accept(), minecraftServer)
            connections.add(connection)
        }
    }





}





















