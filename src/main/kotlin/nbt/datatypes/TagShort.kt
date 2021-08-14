package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagShort(
    tagName: String,
    private val tagValue: Short,
    override val typeId: Int = 2
) : Tag(typeId, tagName, tagValue) {

    constructor(): this("", 0)

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeTagInfo(this@TagShort)
            writeShort(tagValue.toInt())
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream): Tag {
        val tagName = stream.readTagName()
        val tagValue = stream.readShort()
        return TagShort(tagName, tagValue)
    }
}