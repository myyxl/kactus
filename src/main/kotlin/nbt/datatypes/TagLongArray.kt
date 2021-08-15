package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagLongArray(
    tagName: String,
    private val tagValue: LongArray,
    override val typeId: Int = 12
) : Tag(typeId, tagName, tagValue) {

    constructor(): this("", LongArray(0))

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeInt(tagValue.size)
            tagValue.forEach {
                writeLong(it)
            }
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream): Tag {
        val size = stream.readInt()
        val longArray = LongArray(size)
        for(i in 0..size) {
            longArray[i] = stream.readLong()
        }
        return TagLongArray("", longArray)
    }

}