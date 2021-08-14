package nbt.datatypes

import nbt.stream.NBTOutputStream

class TagDouble(
    tagName: String,
    private val tagValue: Double,
    override val typeId: Int = 6
) : Tag(typeId, tagName, tagValue) {

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeTagInfo(this@TagDouble)
            writeDouble(tagValue)
        }.toByteArray()
    }
}