package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagInt(
    tagName: String,
    private val tagValue: Int,
    override val typeId: Int = 3
) : Tag(typeId, tagName, tagValue) {

    constructor(): this("", 0)

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeTagInfo(this@TagInt)
            writeInt(tagValue)
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream, invoker: Tag): Tag {
        var tagName = ""
        if(invoker.typeId != TagList<Tag>().typeId) tagName = stream.readTagName()
        val tagValue = stream.readInt()
        return TagInt(tagName, tagValue)
    }
}