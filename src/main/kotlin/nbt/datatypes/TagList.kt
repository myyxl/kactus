package nbt.datatypes

import nbt.stream.NBTOutputStream

class TagList<T>(
    private val tagName: String,
    private val tagValue: ArrayList<T>,
    override val typeId: Int = 9
) : Tag(typeId, tagName, tagValue) {

    constructor(tagName: String): this(tagName, ArrayList<T>())

    override fun serialize(): ByteArray {
        val serializedList = NBTOutputStream().apply {
            tagValue.forEach {
                write((it as Tag).serialize())
            }
        }.toByteArray()
        return NBTOutputStream().apply {
            writeTagInfo(this@TagList)
            writeTypeId(0)  //TODO Type id of list elements
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