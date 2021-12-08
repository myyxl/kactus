package network.server.outbound.packet

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import network.stream.PacketOutputStream

abstract class OutboundPacket {

    abstract val packetId: Int
    private var packetBytes = Unpooled.buffer()
    private var dataBytes = Unpooled.buffer()
    open var closeConnection: Boolean = false

    val packet = PacketOutputStream(dataBytes)

    private fun finalizePacket() {
        val tmpBuffer = Unpooled.buffer()
        PacketOutputStream(tmpBuffer).apply {
            writeVarInt(packetId)
            tmpBuffer.writeBytes(dataBytes)
        }

        PacketOutputStream(packetBytes).writeVarInt(tmpBuffer.readableBytes())
        packetBytes.writeBytes(tmpBuffer)
    }

    fun generatePacket(): ByteBuf {
        finalizePacket()
        return packetBytes
    }
}














