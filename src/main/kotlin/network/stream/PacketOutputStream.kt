package network.stream

import java.io.DataOutputStream
import java.io.OutputStream
import java.util.*
import kotlin.experimental.or

class PacketOutputStream(out: OutputStream?) : DataOutputStream(out) {

    /*
        writeVarInt() and writeVarLong() source: https://wiki.vg/Protocol#VarInt_and_VarLong
     */

    fun writeVarInt(int: Int) {
        var value = int
        do {
            var currentByte = (value and 127).toByte()
            value = value ushr 7
            if (value != 0) currentByte = currentByte or 128.toByte()
            writeByte(currentByte.toInt())
        } while (value != 0)
    }

    fun writeVarLong(long: Long) {
        var value = long
        do {
            var currentByte = (value and 127).toByte()
            value = value ushr 7
            if (value != 0L) currentByte = currentByte or 128.toByte()
            writeByte(currentByte.toInt())
        } while (value != 0L)
    }

    fun writeString(string: String) {
        writeVarInt(string.toByteArray().size)
        writeBytes(string)
    }

    fun writeUUID(uuid: UUID) {
        writeLong(uuid.mostSignificantBits)
        writeLong(uuid.leastSignificantBits)
    }

    /*
        https://wiki.vg/Protocol#Position
     */
    /*fun writePosition(location: Location) {
        val loc: Long = ((location.x and 0x3FFFFFF shl 38 or (location.z and 0x3FFFFFF shl 12) or (location.y and 0xFFF)).toLong())
        writeLong(loc)
    }*/

}