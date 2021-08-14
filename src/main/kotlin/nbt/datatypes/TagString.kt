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
            writeTagInfo(this@TagString)
            writeShort(tagValue.length.toUShort().toInt())
            writeBytes(tagValue)
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream): Tag {
        val tagName = stream.readTagName()
        val length = stream.readShort().toUShort().toInt()
        val stringBytes = ByteArray(length)
        stream.readFully(stringBytes)
        return TagString(tagName, String(stringBytes))
    }

}