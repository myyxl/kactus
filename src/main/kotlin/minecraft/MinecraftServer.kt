package minecraft

import io.netty.channel.Channel
import minecraft.player.Player
import minecraft.world.LevelLoader
import network.server.NetworkServer
import network.server.ClientState
import network.server.outbound.packet.login.OutboundLoginSuccessPacket
import network.server.sendPacket
import network.server.setState

class MinecraftServer {

    val serverMotd = "I'm a Kotlin server"
    val gameVersion = "1.17.1"
    val protocolVersion = 756
    val maxPlayers = 100

    private lateinit var networkServer: NetworkServer

    @Volatile var isRunning = true
    @Volatile var onlinePlayers = ArrayList<Player>()

    fun startServer() {
        LevelLoader().loadLevels()
        networkServer = NetworkServer("0.0.0.0", 25565, this)
        while(isRunning) {
            // TODO
        }
    }

    @Synchronized fun initializeNewPlayer(channel: Channel, username: String) {
        val newPlayer = Player(username, channel)
        channel.sendPacket(OutboundLoginSuccessPacket(username, newPlayer.uuid))
        channel.config().setState(ClientState.PLAY)
        onlinePlayers.add(newPlayer)
    }

    @Synchronized fun removeOnlinePlayer(player: Player) {
        onlinePlayers.remove(player)
    }

    @Synchronized fun removeOnlinePlayer(channel: Channel) {
        val iter = onlinePlayers.iterator()
        while(iter.hasNext()) {
            val player = iter.next()
            if(player.channel == channel) iter.remove()
        }
    }
}