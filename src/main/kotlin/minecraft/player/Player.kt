package minecraft.player

import network.client.ClientConnection
import java.util.*

class Player(val username: String, val clientConnection: ClientConnection) {
    val uuid: UUID = UUID.randomUUID()
}