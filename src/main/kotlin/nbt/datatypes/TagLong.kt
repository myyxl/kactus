package nbt.datatypes

import nbt.stream.NBTOutputStream

class TagLong(
    tagName: String,
    private val tagValue: Long,
    override val typeId: Int = 4
) : Tag(typeId, tagName, tagValue) {

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeTagInfo(this@TagLong)
            writeLong(tagValue)
        }.toByteArray()
    }
}