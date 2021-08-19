package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagByte(
    tagName: String,
    override val value: Byte,
    override val typeId: Int = 1
) : Tag(typeId, tagName, value) {

    constructor(): this("", 0)

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeByte(value.toInt())
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream): Tag {
        val tagValue = stream.readByte()
        return TagByte("", tagValue)
    }

}