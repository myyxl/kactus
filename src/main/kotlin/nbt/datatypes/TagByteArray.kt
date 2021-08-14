package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagByteArray(
    tagName: String,
    private val tagValue: ByteArray,
    override val typeId: Int = 7
) : Tag(typeId, tagName, tagValue) {

    constructor(): this("", ByteArray(0))

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeTagInfo(this@TagByteArray)
            writeInt(tagValue.size)
            write(tagValue)
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream, invoker: Tag): Tag {
        var tagName = ""
        if(invoker.typeId != TagList<Tag>().typeId) tagName = stream.readTagName()
        val size = stream.readInt()
        val byteArray = ByteArray(size)
        for(i in 0..size) {
            byteArray[i] = stream.readByte()
        }
        return TagByteArray(tagName, byteArray)
    }

}