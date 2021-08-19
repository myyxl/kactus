package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagByteArray(
    tagName: String,
    override val value: ByteArray,
    override val typeId: Int = 7
) : Tag(typeId, tagName, value) {

    constructor(): this("", ByteArray(0))

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeInt(value.size)
            write(value)
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream): Tag {
        val size = stream.readInt()
        val byteArray = ByteArray(size)
        for(i in 0 until size) {
            byteArray[i] = stream.readByte()
        }
        return TagByteArray("", byteArray)
    }

}