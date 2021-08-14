package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagByte(
    tagName: String,
    private val tagValue: Byte,
    override val typeId: Int = 1
) : Tag(typeId, tagName, tagValue) {

    constructor(): this("", 0)

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeTagInfo(this@TagByte)
            writeByte(tagValue.toInt())
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream): Tag {
        val tagName = stream.readTagName()
        val tagValue = stream.readByte()
        return TagByte(tagName, tagValue)
    }

}