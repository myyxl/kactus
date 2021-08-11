package network.packet

import network.stream.PacketOutputStream
import java.io.ByteArrayOutputStream

abstract class ServerPacket {

    abstract val packetId: Int
    private var packetBytes: ByteArrayOutputStream = ByteArrayOutputStream()
    private var dataBytes: ByteArrayOutputStream = ByteArrayOutputStream()
    open var closeConnection: Boolean = false

    val packet = PacketOutputStream(dataBytes)

    private fun finalizePacket() {
        val tmpBuffer = ByteArrayOutputStream()
        PacketOutputStream(tmpBuffer).apply {
            writeVarInt(packetId)
            write(dataBytes.toByteArray())
        }
        PacketOutputStream(packetBytes).writeVarInt(tmpBuffer.size())
        packetBytes.writeBytes(tmpBuffer.toByteArray())
    }

    fun getBytes(): ByteArray {
        finalizePacket()
        return packetBytes.toByteArray()
    }
}














