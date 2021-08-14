package nbt.datatypes

import nbt.stream.NBTOutputStream

class TagFloat(
    tagName: String,
    private val tagValue: Float,
    override val typeId: Int = 5
) : Tag(typeId, tagName, tagValue) {

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeTagInfo(this@TagFloat)
            writeFloat(tagValue)
        }.toByteArray()
    }
}