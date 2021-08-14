package nbt.datatypes

import nbt.stream.NBTOutputStream

class TagInt(
    tagName: String,
    private val tagValue: Int,
    override val typeId: Int = 3
) : Tag(typeId, tagName, tagValue) {

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeTagInfo(this@TagInt)
            writeInt(tagValue)
        }.toByteArray()
    }
}