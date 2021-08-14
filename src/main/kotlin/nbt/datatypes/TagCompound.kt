package nbt.datatypes

import nbt.stream.NBTOutputStream

class TagCompound(
    private val tagName: String,
    private val tagValue: ArrayList<Tag>,
    override val typeId: Int = 10
) : Tag(typeId, tagName, tagValue) {

    constructor(tagName: String): this(tagName, ArrayList<Tag>())

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeTagInfo(this@TagCompound)
            tagValue.forEach {
                write(it.serialize())
            }
            writeTagInfo(TagEnd())
        }.toByteArray()
    }

    fun addTag(tag: Tag) {
        tagValue.add(tag)
    }

    fun getTag(name: String): Tag {
        return tagValue.find {
            it.name == name
        } ?: TagEnd()
    }

}