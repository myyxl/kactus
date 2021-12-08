package network.server.inbound.packet

import network.server.ClientState
import org.reflections.Reflections

class InboundPacketRegister {

    companion object {
        private val packets = HashMap<Pair<ClientState, Int>, Class<out InboundPacket>>()

        fun registerPackets() {
            val reflections = Reflections("network.server.inbound")
            val classes = reflections.getSubTypesOf(InboundPacket::class.java)
            classes?.forEach {
                val packet = it.getDeclaredConstructor().newInstance()
                packets[Pair(packet.state, packet.packetId)] = packet::class.java
            }
        }
    }

    @Synchronized fun getPacket(clientState: ClientState, packetId: Int): InboundPacket? {
        return packets[Pair(clientState, packetId)]?.getDeclaredConstructor()?.newInstance()
    }
}