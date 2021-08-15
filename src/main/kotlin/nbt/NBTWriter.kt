package nbt

import nbt.datatypes.TagCompound
import java.io.File

class NBTWriter {

    fun writeFile(fileName: String, tagCompound: TagCompound) {
        File(fileName).writeBytes(tagCompound.serialize()) //TODO: Write tag info before
    }
}