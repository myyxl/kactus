package network.stream

import io.netty.buffer.ByteBuf
import java.io.DataInputStream
import java.io.InputStream
import kotlin.experimental.and

class PacketInputStream(val byteBuf: ByteBuf) {

    /*
    writeVarInt() and writeVarLong() source: https://wiki.vg/Protocol#VarInt_and_VarLong
    */

    fun readVarInt(): Int {
        var value = 0
        var bitOffset = 0
        var currentByte: Byte
        do {
            if (bitOffset == 35) throw RuntimeException("VarInt is too big")
            currentByte = byteBuf.readByte()
            value = value or ((currentByte and 127).toInt() shl bitOffset)
            bitOffset += 7
        } while ((currentByte and 128.toByte()) != 0.toByte())
        return value
    }

    fun readVarLong(): Long {
        var value: Long = 0
        var bitOffset = 0
        var currentByte: Byte
        do {
            if (bitOffset == 70) throw RuntimeException("VarLong is too big")
            currentByte = byteBuf.readByte()
            value = value or ((currentByte and 127).toLong() shl bitOffset)
            bitOffset += 7
        } while ((currentByte and 128.toByte()) != 0.toByte())

        return value
    }

    fun readString(): String {
        val size = readVarInt()
        val stringBytes = ByteArray(size)
        byteBuf.readBytes(stringBytes)
        return stringBytes.decodeToString()
    }

}