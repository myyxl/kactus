package nbt.datatypes

import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream

class TagList<T: Tag>(
    tagName: String,
    private val tagValue: ArrayList<T>,
    override val typeId: Int = 9
) : Tag(typeId, tagName, tagValue) {

    constructor(tagName: String): this(tagName, ArrayList<T>())
    constructor(): this("", ArrayList())

    override fun serialize(): ByteArray {
        val serializedList = NBTOutputStream().apply {
            tagValue.forEach {
                write(it.serialize())
            }
        }.toByteArray()
        return NBTOutputStream().apply {
            if(tagValue.size == 0) writeTypeId(TagEnd().typeId) else writeTypeId(tagValue[0].typeId)
            writeInt(serializedList.size)
            write(serializedList)
        }.toByteArray()
    }

    override fun deserialize(stream: NBTInputStream): Tag {
        val listType = getTagById(stream.readTypeId())
        val tagList = TagList<Tag>("")
        val size = stream.readInt()
        for(i in 0..size) {
            val newTag = listType.deserialize(stream)
            tagList.addTag(newTag)
        }
        return tagList
    }

    fun addTag(tag: T) {
        tagValue.add(tag)
    }

    fun getList(): ArrayList<T> {
        return tagValue
    }

}