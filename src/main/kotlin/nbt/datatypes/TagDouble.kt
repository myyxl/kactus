package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagDouble(
    tagName: String,
    private val tagValue: Double,
    override val typeId: Int = 6
) : Tag(typeId, tagName, tagValue) {

    constructor(): this("", 0.0)

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeDouble(tagValue)
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream): Tag {
        val tagValue = stream.readDouble()
        return TagDouble("", tagValue)
    }
}