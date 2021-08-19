package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagLong(
    tagName: String,
    override val value: Long,
    override val typeId: Int = 4
) : Tag(typeId, tagName, value) {

    constructor(): this("", 0)

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeLong(value)
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream): Tag {
        val tagValue = stream.readLong()
        return TagLong("", tagValue)
    }
}