package nbt.datatypes

import nbt.stream.NBTOutputStream

class TagIntArray(
    tagName: String,
    private val tagValue: IntArray,
    override val typeId: Int = 11
) : Tag(typeId, tagName, tagValue) {

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeTagInfo(this@TagIntArray)
            writeInt(tagValue.size)
            tagValue.forEach {
                writeInt(it)
            }
        }.toByteArray()
    }

}