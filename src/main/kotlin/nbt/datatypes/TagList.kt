package nbt.datatypes

import nbt.stream.NBTOutputStream

class TagList<T: Tag>(
    tagName: String,
    private val tagValue: ArrayList<T>,
    override val typeId: Int = 9
) : Tag(typeId, tagName, tagValue) {

    constructor(tagName: String): this(tagName, ArrayList<T>())

    override fun serialize(): ByteArray {
        val serializedList = NBTOutputStream().apply {
            tagValue.forEach {
                write(it.serialize())
            }
        }.toByteArray()
        return NBTOutputStream().apply {
            writeTagInfo(this@TagList)
            if(tagValue.size == 0) writeTypeId(TagEnd().typeId) else writeTypeId(tagValue[0].typeId)
            writeInt(serializedList.size)
            write(serializedList)
        }.toByteArray()
    }

    fun addTag(tag: T) {
        tagValue.add(tag)
    }

    fun getList(): ArrayList<T> {
        return tagValue
    }

}