package nbt

import nbt.datatypes.TagCompound
import nbt.stream.NBTOutputStream
import java.io.File

class NBTWriter {

    fun writeFile(fileName: String, tagCompound: TagCompound) {
        val tagInfo = NBTOutputStream().apply {
            writeTagInfo(tagCompound)
        }.toByteArray()
        val outFile = File(fileName)
        outFile.apply {
            writeBytes(tagInfo)
            writeBytes(tagCompound.serialize())
        }
    }
}