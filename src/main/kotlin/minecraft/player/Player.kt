package minecraft.player

import network.client.ClientConnection
import minecraft.world.Location
import java.util.*

class Player(val username: String, val clientConnection: ClientConnection, val uuid: UUID = UUID.randomUUID()) {

    var playerLocation = Location(0, 0, 0, 0, 0)


}