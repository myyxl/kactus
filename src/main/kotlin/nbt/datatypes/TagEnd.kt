package nbt.datatypes

import nbt.stream.NBTOutputStream

class TagEnd() : Tag(0, "", 0) {

    override fun serialize(): ByteArray {
        return NBTOutputStream().apply {
            writeTagInfo(this@TagEnd)
        }.toByteArray()
    }

}