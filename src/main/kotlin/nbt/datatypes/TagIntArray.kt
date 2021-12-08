package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagIntArray(
    tagName: String,
    override val value: IntArray,
    override val typeId: Int = 11
) : Tag(typeId, tagName, value) {

    constructor(): this("", IntArray(0))

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeInt(value.size)
            value.forEach {
                writeInt(it)
            }
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream): Tag {
        val size = stream.readInt()
        val intArray = IntArray(size)
        for(i in 0 until size) {
            intArray[i] = stream.readInt()
        }
        return TagIntArray("", intArray)
    }

}