package nbt.datatypes

import nbt.stream.NBTInputStream

abstract class Tag(
    open val typeId: Int,
    var name: String,
    val value: Any) {
    abstract fun serialize(): ByteArray
    abstract fun deserialize(stream: NBTInputStream): Tag

    companion object {
        private val tagList = listOf(
            TagEnd(),
            TagByte(),
            TagShort(),
            TagInt(),
            TagLong(),
            TagFloat(),
            TagDouble(),
            TagByteArray(),
            TagString(),
            TagList<Tag>(),
            TagCompound(),
            TagIntArray(),
            TagLongArray()
        )

        fun getTagById(id: Int): Tag {
            return tagList.find {
                it.typeId == id
            } ?: TagEnd()
        }
    }
}
