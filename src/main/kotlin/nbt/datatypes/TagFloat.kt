package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagFloat(
    tagName: String,
    private val tagValue: Float,
    override val typeId: Int = 5
) : Tag(typeId, tagName, tagValue) {

    constructor(): this("", 0f)

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeFloat(tagValue)
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream): Tag {
        val tagValue = stream.readFloat()
        return TagFloat("", tagValue)
    }
}