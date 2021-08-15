package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagIntArray(
    tagName: String,
    private val tagValue: IntArray,
    override val typeId: Int = 11
) : Tag(typeId, tagName, tagValue) {

    constructor(): this("", IntArray(0))

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeInt(tagValue.size)
            tagValue.forEach {
                writeInt(it)
            }
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream): Tag {
        val size = stream.readInt()
        val intArray = IntArray(size)
        for(i in 0..size) {
            intArray[i] = stream.readInt()
        }
        return TagIntArray("", intArray)
    }

}