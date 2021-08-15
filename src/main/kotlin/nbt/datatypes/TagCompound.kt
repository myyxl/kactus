package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream
import java.io.EOFException

class TagCompound(
    tagName: String,
    private val tagValue: ArrayList<Tag>,
    override val typeId: Int = 10
) : Tag(typeId, tagName, tagValue) {

    constructor(tagName: String): this(tagName, ArrayList<Tag>())
    constructor(): this("", ArrayList())

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            tagValue.forEach {
                writeTagInfo(it)
                write(it.serialize())
            }
            write(TagEnd().serialize())
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream): Tag {
        val compoundTag = TagCompound("")
        try {
            var currentId = stream.readTypeId()
            while(currentId > 0) {
                val tagName = stream.readTagName()
                val newTag = getTagById(currentId).deserialize(stream)
                newTag.name = tagName
                compoundTag.addTag(newTag)
                currentId = stream.readTypeId()
            }
        } catch (e: EOFException) {}
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