package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagInt(
    tagName: String,
    override val value: Int,
    override val typeId: Int = 3
) : Tag(typeId, tagName, value) {

    constructor(): this("", 0)

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeInt(value)
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream): Tag {
        val tagValue = stream.readInt()
        return TagInt("", tagValue)
    }
}