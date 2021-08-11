package minecraft

import minecraft.player.Player
import network.client.ClientConnection
import network.client.ClientState
import network.packet.server.login.SLoginSuccessPacket
import network.packet.server.play.SPlayPlayerPositionAndLookPacket
import network.packet.server.play.SPlaySpawnPositionPacket

class MinecraftServer : Thread() {

    val serverMotd = "I'm a Kotlin server"
    val gameVersion = "1.17.1"
    val protocolVersion = 756
    val maxPlayers = 100

    @Volatile var isRunning = true
    @Volatile var onlinePlayers = ArrayList<Player>()

    override fun run() {
        while(isRunning) {
            sleep(1/20000) // Tick
        }
    }

    @Synchronized fun initializeNewPlayer(clientConnection: ClientConnection, username: String) {
        val newPlayer = Player(username, clientConnection)
        clientConnection.sendPacket(SLoginSuccessPacket(username, newPlayer.uuid))
        clientConnection.clientState = ClientState.PLAY
        onlinePlayers.add(newPlayer)
        clientConnection.sendPacket(SPlaySpawnPositionPacket(newPlayer.playerLocation))
        clientConnection.sendPacket(SPlayPlayerPositionAndLookPacket(newPlayer.playerLocation, 0, 1, false))
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