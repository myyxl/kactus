package nbt.stream

import java.io.DataInputStream
import java.io.InputStream

class NBTInputStream(input: InputStream?) : DataInputStream(input) {

    fun readTypeId(): Int {
        return readByte().toInt()
    }

    fun readTagName(): String {
        val length = readShort()
        val nameBytes = ByteArray(length.toInt())
        readFully(nameBytes)
        return String(nameBytes)
    }


}