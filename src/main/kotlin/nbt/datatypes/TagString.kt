package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagString(
    tagName: String,
    override val value: String,
    override val typeId: Int = 8
) : Tag(typeId, tagName, value) {

    constructor(): this("", "")

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeShort(value.length.toUShort().toInt())
            writeBytes(value)
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream): Tag {
        val length = stream.readShort().toUShort().toInt()
        val stringBytes = ByteArray(length)
        stream.readFully(stringBytes)
        return TagString("", String(stringBytes))
    }

}