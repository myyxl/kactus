package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagShort(
    tagName: String,
    override val value: Short,
    override val typeId: Int = 2
) : Tag(typeId, tagName, value) {

    constructor(): this("", 0)

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeShort(value.toInt())
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream): Tag {
        val tagValue = stream.readShort()
        return TagShort("", tagValue)
    }
}