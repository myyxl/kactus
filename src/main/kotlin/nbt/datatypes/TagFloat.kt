package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagFloat(
    tagName: String,
    override val value: Float,
    override val typeId: Int = 5
) : Tag(typeId, tagName, value) {

    constructor(): this("", 0f)

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeFloat(value)
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream): Tag {
        val tagValue = stream.readFloat()
        return TagFloat("", tagValue)
    }
}