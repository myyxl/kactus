package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagDouble(
    tagName: String,
    private val tagValue: Double,
    override val typeId: Int = 6
) : Tag(typeId, tagName, tagValue) {

    constructor(): this("", 0.0)

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeTagInfo(this@TagDouble)
            writeDouble(tagValue)
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream, invoker: Tag): Tag {
        var tagName = ""
        if(invoker.typeId != TagList<Tag>().typeId) tagName = stream.readTagName()
        val tagValue = stream.readDouble()
        return TagDouble(tagName, tagValue)
    }
}