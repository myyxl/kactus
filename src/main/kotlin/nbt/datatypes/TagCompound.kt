package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagCompound(
    tagName: String,
    private val tagValue: ArrayList<Tag>,
    override val typeId: Int = 10
) : Tag(typeId, tagName, tagValue) {

    constructor(tagName: String): this(tagName, ArrayList<Tag>())
    constructor(): this("", ArrayList())

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeTagInfo(this@TagCompound)
            tagValue.forEach {
                write(it.serialize())
            }
            writeTagInfo(TagEnd())
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream, invoker: Tag): Tag {
        var tagName = ""
        if(invoker.typeId != TagList<Tag>().typeId) tagName = stream.readTagName()
        val compoundTag = TagCompound(tagName)
        var currentId = 1
        while(currentId > 0) {
            currentId = stream.readTypeId()
            val newTag = getTagById(currentId).deserialize(stream, this)
            compoundTag.addTag(newTag)
        }
        return compoundTag
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