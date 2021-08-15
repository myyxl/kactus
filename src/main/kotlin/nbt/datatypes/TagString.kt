package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagString(
    tagName: String,
    private val tagValue: String,
    override val typeId: Int = 8
) : Tag(typeId, tagName, tagValue) {

    constructor(): this("", "")

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeShort(tagValue.length.toUShort().toInt())
            writeBytes(tagValue)
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream): Tag {
        val length = stream.readShort().toUShort().toInt()
        val stringBytes = ByteArray(length)
        stream.readFully(stringBytes)
        return TagString("", String(stringBytes))
    }

}