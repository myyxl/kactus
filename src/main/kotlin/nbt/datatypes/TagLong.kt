package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagLong(
    tagName: String,
    private val tagValue: Long,
    override val typeId: Int = 4
) : Tag(typeId, tagName, tagValue) {

    constructor(): this("", 0)

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeTagInfo(this@TagLong)
            writeLong(tagValue)
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream, invoker: Tag): Tag {
        var tagName = ""
        if(invoker.typeId != TagList<Tag>().typeId) tagName = stream.readTagName()
        val tagValue = stream.readLong()
        return TagLong(tagName, tagValue)
    }
}