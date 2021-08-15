package nbt.datatypes

import nbt.stream.NBTInputStream

class TagEnd : Tag(0, "", 0) {

    override fun serialize(): ByteArray {
        return ByteArray(1) {
            0
        }
    }

    override fun deserialize(stream: NBTInputStream): Tag {
        return TagEnd()
    }

}