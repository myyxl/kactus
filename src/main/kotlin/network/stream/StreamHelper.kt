package network.stream

import java.io.ByteArrayOutputStream

class StreamHelper {

    fun getVarIntLength(value: Int): Int {
        val stream = PacketOutputStream(ByteArrayOutputStream())
        stream.writeVarInt(value)
        val size = stream.size()
        stream.close()
        return size
    }
}