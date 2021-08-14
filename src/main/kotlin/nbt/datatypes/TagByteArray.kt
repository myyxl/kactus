package nbt.datatypes

import nbt.stream.NBTOutputStream

class TagByteArray(
    tagName: String,
    private val tagValue: ByteArray,
    override val typeId: Int = 7
) : Tag(typeId, tagName, tagValue) {

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeTagInfo(this@TagByteArray)
            writeInt(tagValue.size)
            write(tagValue)
        }.toByteArray()
    }

}