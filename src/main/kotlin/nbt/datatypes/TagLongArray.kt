package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagLongArray(
    tagName: String,
    override val value: LongArray,
    override val typeId: Int = 12
) : Tag(typeId, tagName, value) {

    constructor(): this("", LongArray(0))

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeInt(value.size)
            value.forEach {
                writeLong(it)
            }
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream): Tag {
        val size = stream.readInt()
        val longArray = LongArray(size)
        for(i in 0 until size) {
            longArray[i] = stream.readLong()
        }
        return TagLongArray("", longArray)
    }

}