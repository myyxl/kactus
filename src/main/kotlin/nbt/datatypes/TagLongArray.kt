package nbt.datatypes

import nbt.stream.NBTOutputStream

class TagLongArray(
    tagName: String,
    private val tagValue: LongArray,
    override val typeId: Int = 12
) : Tag(typeId, tagName, tagValue) {

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeTagInfo(this@TagLongArray)
            writeInt(tagValue.size)
            tagValue.forEach {
                writeLong(it)
            }
        }.toByteArray()
    }

}