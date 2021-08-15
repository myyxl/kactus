package nbt

import nbt.datatypes.Tag
import nbt.datatypes.TagCompound
import nbt.datatypes.TagList
import nbt.stream.NBTInputStream
import nbt.stream.NBTOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

class NBT(private val compressionMethod: NBTCompression) {

    constructor(): this(NBTCompression.NONE)

    fun writeToFile(fileName: String, tagCompound: TagCompound) {
        val dataBytes = NBTOutputStream().apply {
            writeTagInfo(tagCompound)
            write(tagCompound.serialize())
        }.toByteArray()
        val outFile = File(fileName)
        outFile.writeBytes(compressByteArray(dataBytes))
    }

    fun readFromFile(fileName: String): TagCompound {
        val fileBytes = File(fileName).readBytes()
        return deserialize(decompressByteArray(fileBytes)) as TagCompound
    }

    private fun compressByteArray(byteArray: ByteArray): ByteArray {
        return when(compressionMethod) {
            NBTCompression.GZIP -> {
                val out = ByteArrayOutputStream()
                GZIPOutputStream(out).use {
                    it.write(byteArray)
                }
                out.toByteArray()
            }
            else -> byteArray
        }
    }

    private fun decompressByteArray(byteArray: ByteArray): ByteArray {
        return when(compressionMethod) {
            NBTCompression.GZIP -> {
                val input = ByteArrayInputStream(byteArray)
                GZIPInputStream(input).readAllBytes()
            }
            else -> byteArray
        }
    }

    fun deserialize(bytes: ByteArray): Tag {
        val stream = NBTInputStream(ByteArrayInputStream(bytes))
        val typeId = stream.readTypeId()
        val tagName = stream.readTagName()
        val tag = Tag.getTagById(typeId).deserialize(stream)
        stream.close()
        tag.name = tagName
        return tag
    }

    fun serialize(tag: Tag): ByteArray {
        return NBTOutputStream().apply {
            writeTagInfo(tag)
            write(tag.serialize())
        }.toByteArray()
    }

    fun dump(tag: Tag, level: Int = -1) {
        when(tag) {
            is TagCompound -> {
                for(i in 0..level) print("\t")
                println("TagCompound('${tag.name}'): ${tag.getTags().size} entries")
                for(i in 0..level) print("\t")
                println("{")
                tag.getTags().forEach {
                    dump(it, level + 1)
                }
                for(i in 0..level) print("\t")
                println("}")
            }
            is TagList<*> -> {
                for(i in 0..level) print("\t")
                println("TagList('${tag.name}'): ${tag.getList().size} entries")
                for(i in 0..level) print("\t")
                println("{")
                tag.getList().forEach {
                    dump(it, level + 1)
                }
                for(i in 0..level) print("\t")
                println("}")
            }
            else -> {
                for(i in 0..level) print("\t")
                println("${tag::class.java.simpleName}('${tag.name}'): ${tag.value}")
            }
        }
    }

}

enum class NBTCompression {
    NONE,
    GZIP,
    ZLIB
}