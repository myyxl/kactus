package minecraft.player

import io.netty.channel.Channel
import java.util.*

class Player(val username: String, val channel: Channel) {
    val uuid: UUID = UUID.randomUUID()
}