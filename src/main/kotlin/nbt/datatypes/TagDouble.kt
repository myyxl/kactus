package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagDouble(
    tagName: String,
    override val value: Double,
    override val typeId: Int = 6
) : Tag(typeId, tagName, value) {

    constructor(): this("", 0.0)

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeDouble(value)
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream): Tag {
        val tagValue = stream.readDouble()
        return TagDouble("", tagValue)
    }
}