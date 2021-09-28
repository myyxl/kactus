package minecraft

import minecraft.player.Player
import minecraft.world.LevelLoader
import network.Server
import network.client.ClientConnection
import network.client.ClientState
import network.packet.server.login.SLoginSuccessPacket

class MinecraftServer : Thread() {

    val serverMotd = "I'm a Kotlin server"
    val gameVersion = "1.17.1"
    val protocolVersion = 756
    val maxPlayers = 100

    private val tcpServer = Server("0.0.0.0", 25565, this)

    @Volatile var isRunning = true
    @Volatile var onlinePlayers = ArrayList<Player>()

    override fun run() {
        LevelLoader().loadLevels()
        tcpServer.start()
        while(isRunning) {
            // TODO
        }
    }

    @Synchronized fun initializeNewPlayer(clientConnection: ClientConnection, username: String) {
        val newPlayer = Player(username, clientConnection)
        clientConnection.sendPacket(SLoginSuccessPacket(username, newPlayer.uuid))
        clientConnection.clientState = ClientState.PLAY
        onlinePlayers.add(newPlayer)
    }

    @Synchronized fun removeOnlinePlayer(player: Player) {
        onlinePlayers.remove(player)
    }

    @Synchronized fun removeOnlinePlayer(clientConnection: ClientConnection) {
        val iter = onlinePlayers.iterator()
        while(iter.hasNext()) {
            val player = iter.next()
            if(player.clientConnection == clientConnection) iter.remove()
        }
    }
}