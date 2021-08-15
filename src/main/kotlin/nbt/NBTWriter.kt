package nbt

import nbt.datatypes.TagCompound
import nbt.stream.NBTOutputStream
import java.io.File
import java.util.*

class NBTWriter {

    fun writeFile(fileName: String, tagCompound: TagCompound) {
        val dataBytes = NBTOutputStream().apply {
            writeTagInfo(tagCompound)
            write(tagCompound.serialize())
        }.toByteArray()
        val outFile = File(fileName)
        outFile.writeBytes(dataBytes)
    }
}