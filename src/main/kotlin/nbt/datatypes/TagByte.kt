package nbt.datatypes

import nbt.stream.NBTOutputStream

class TagByte(
    tagName: String,
    private val tagValue: Byte,
    override val typeId: Int = 1
) : Tag(typeId, tagName, tagValue) {

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeTagInfo(this@TagByte)
            writeByte(tagValue.toInt())
        }.toByteArray()
    }

}