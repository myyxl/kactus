package nbt.datatypes

import nbt.stream.NBTOutputStream

class TagString(
    tagName: String,
    private val tagValue: String,
    override val typeId: Int = 8
) : Tag(typeId, tagName, tagValue) {

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeTagInfo(this@TagString)
            writeShort(tagValue.length.toUShort().toInt())
            writeBytes(tagValue)
        }.toByteArray()
    }

}