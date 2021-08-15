package nbt.stream

import nbt.datatypes.Tag
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.OutputStream

class NBTOutputStream(out: OutputStream?) : DataOutputStream(out) {

    constructor(): this(ByteArrayOutputStream())

    fun writeTagInfo(tag: Tag) {
        writeTypeId(tag.typeId)
        writeTagName(tag.name)
    }

    fun writeTypeId(typeId: Int) {
        writeByte(typeId)
    }

    fun writeTagName(tagName: String) {
        writeShort(tagName.length.toUShort().toInt())
        writeBytes(tagName)
    }

    fun toByteArray(): ByteArray {
        close()
        return (out as ByteArrayOutputStream).toByteArray()

    }

}