package nbt

import nbt.datatypes.Tag
import nbt.datatypes.TagCompound
import nbt.stream.NBTInputStream
import java.io.ByteArrayInputStream
import java.io.File

class NBTReader {

    fun readFile(fileName: String): TagCompound {
        val fileBytes = File(fileName).readBytes()
        return deserialize(fileBytes) as TagCompound
    }

    fun deserialize(bytes: ByteArray): Tag {
        val stream = NBTInputStream(ByteArrayInputStream(bytes))
        val typeId = stream.readTypeId()
        val tag = Tag.getTagById(typeId).deserialize(stream)
        stream.close()
        return tag
    }
}