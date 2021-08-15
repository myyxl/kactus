package nbt

import nbt.datatypes.*
import org.junit.Test
import java.lang.Double.NaN
import kotlin.test.assertEquals

internal class NBTTest {

    @Test
    fun readFromFile() {
        var compoundTag = NBT().readFromFile("src/test/resources/small_uncompressed.nbt")
        val shortValue = (compoundTag.getTag("ImShort") as TagShort).value
        val stringTag = (compoundTag.getTag("name") as TagString).value
        assertEquals(shortValue, (1337).toShort())
        assertEquals(stringTag, "Testing")

        compoundTag = NBT(NBTCompression.GZIP).readFromFile("src/test/resources/small_gzip_compressed.nbt")
        val byteTag = (compoundTag.getTag("OnGround") as TagByte).value
        val listTag = (compoundTag.getTag("Pos") as TagList<*>)
        val doubleTag = (listTag.getList()[1]).value
        assertEquals(byteTag, (1).toByte())
        assertEquals(doubleTag, NaN)
    }

    /*
        Testing the most important tags, if most of the primitives work, all of them should work...
     */
    @Test
    fun deserialize() {
        var tag: Tag = TagString("tagName123", "tagValue345")
        var serialized = NBT().serialize(tag)
        var deserialized = NBT().deserialize(serialized)
        assert(checkTagInfo(tag, deserialized))

        tag = TagString("", "tagValue345")
        serialized = NBT().serialize(tag)
        deserialized = NBT().deserialize(serialized)
        assert(checkTagInfo(tag, deserialized))

        tag = TagString("tagName123", "")
        serialized = NBT().serialize(tag)
        deserialized = NBT().deserialize(serialized)
        assert(checkTagInfo(tag, deserialized))

        tag = TagString("", "")
        serialized = NBT().serialize(tag)
        deserialized = NBT().deserialize(serialized)
        assert(checkTagInfo(tag, deserialized))

        tag = TagEnd()
        serialized = NBT().serialize(tag)
        deserialized = NBT().deserialize(serialized)
        assert(checkTagInfo(tag, deserialized))

        tag = TagByte("TagByte", 0x01)
        serialized = NBT().serialize(tag)
        deserialized = NBT().deserialize(serialized)
        assert(checkTagInfo(tag, deserialized))

        tag = TagLong("TagByte", 100000L)
        serialized = NBT().serialize(tag)
        deserialized = NBT().deserialize(serialized)
        assert(checkTagInfo(tag, deserialized))

        tag = TagCompound("TagCompound")
        serialized = NBT().serialize(tag)
        deserialized = NBT().deserialize(serialized)
        assert(checkTagInfo(tag, deserialized))

        tag = TagList<TagString>("TagList(String)")
        serialized = NBT().serialize(tag)
        deserialized = NBT().deserialize(serialized)
        assert(checkTagInfo(tag, deserialized))

    }

    private fun checkTagInfo(tag1: Tag, tag2: Tag): Boolean {
        return tag1.name == tag2.name && tag1.typeId == tag2.typeId && tag1.value == tag2.value
    }
}