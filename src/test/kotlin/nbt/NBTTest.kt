package nbt

import nbt.datatypes.*
import org.junit.Test
import java.lang.Double.NaN
import kotlin.test.assertEquals

internal class NBTTest {

    @Test
    fun testDecompressionFromFile() {
        val compoundTag = NBT(NBTCompression.GZIP).readFromFile("src/test/resources/test-compression.nbt")
        val byteTag = (compoundTag.getTag("OnGround") as TagByte).value
        val listTag = (compoundTag.getTag("Pos") as TagList<*>)
        val doubleTag = (listTag.getList()[1]).value
        assertEquals(byteTag, (1).toByte())
        assertEquals(doubleTag, NaN)
    }

    @Test
    fun testDeserializeFromFile() {
        val mainTag = NBT().readFromFile("src/test/resources/test-deserialize.nbt")

        assertEquals((7).toByte(), (mainTag.getTag("ByteTagFull") as TagByte).value)
        assertEquals((0).toByte(), (mainTag.getTag("ByteTagNoValue") as TagByte).value)

        assertEquals((1337).toShort(), (mainTag.getTag("ShortTagFull") as TagShort).value)
        assertEquals((0).toShort(), (mainTag.getTag("ShortTagNoValue") as TagShort).value)

        assertEquals(1234567890, (mainTag.getTag("IntTagFull") as TagInt).value)
        assertEquals(0, (mainTag.getTag("IntTagNoValue") as TagInt).value)

        assertEquals((1234567897654321).toLong(), (mainTag.getTag("LongTagFull") as TagLong).value)
        assertEquals((0).toLong(), (mainTag.getTag("LongTagNoValue") as TagLong).value)

        assertEquals((1.2).toDouble(), (mainTag.getTag("DoubleTagFull") as TagDouble).value)
        assertEquals((0).toDouble(), (mainTag.getTag("DoubleTagNoValue") as TagDouble).value)
        assertEquals(NaN, (mainTag.getTag("DoubleTagNaN") as TagDouble).value)

        assertEquals((3.141592).toFloat(), (mainTag.getTag("FloatTagFull") as TagFloat).value)
        assertEquals((0).toFloat(), (mainTag.getTag("FloatTagNoValue") as TagFloat).value)
        assertEquals((NaN).toFloat(), (mainTag.getTag("FloatTagNaN") as TagFloat).value)

        assertEquals("Hello World!", (mainTag.getTag("StringTagFull") as TagString).value)
        assertEquals("", (mainTag.getTag("StringTagNoValue") as TagString).value)
        assertEquals("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
            (mainTag.getTag("StringTagLong") as TagString).value)
        assertEquals("!\"§\$%&/()=?@²³{}[]\\+*#'_-.:,;µ<>öäü", (mainTag.getTag("StringTagSpecial") as TagString).value)

        assert(byteArrayOf(-34, -83, -66, -17).contentEquals(((mainTag.getTag("ByteArrayTag") as TagByteArray).value) as ByteArray))

        assert(intArrayOf(286331153, 572662306, 858993459, 1145324612, 1431655765
        ).contentEquals(((mainTag.getTag("IntArrayTag") as TagIntArray).value) as IntArray))

        assert(longArrayOf(1229782938247303441, 2459565876494606882, 3689348814741910323
        ).contentEquals(((mainTag.getTag("LongArrayTag") as TagLongArray).value) as LongArray))

        assertEquals(0, ((mainTag.getTag("TagCompoundEmpty") as TagCompound).getTags().size))

        assertEquals(21, ((mainTag.getTag("TagCompoundFull") as TagCompound).getTags().size))

        assertEquals(2, ((mainTag.getTag("TagCompoundNested") as TagCompound).getTags().size))

        val nestedChild1 = ((mainTag.getTag("TagCompoundNested") as TagCompound)).getTag("TagCompoundNestedChild1") as TagCompound
        val nestedChild2 = ((mainTag.getTag("TagCompoundNested") as TagCompound)).getTag("TagCompoundNestedChild2") as TagCompound
        assertEquals(21, nestedChild1.getTags().size)
        assertEquals(21, nestedChild2.getTags().size)

        assertEquals(0, (mainTag.getTag("ListTagEmpty") as TagList<*>).getList().size)

        assertEquals(4, (mainTag.getTag("ListTagByte") as TagList<*>).getList().size)
        assertEquals(4, (mainTag.getTag("ListTagShort") as TagList<*>).getList().size)
        assertEquals(2, (mainTag.getTag("ListTagInt") as TagList<*>).getList().size)
        assertEquals(2, (mainTag.getTag("ListTagLong") as TagList<*>).getList().size)
        assertEquals(4, (mainTag.getTag("ListTagDouble") as TagList<*>).getList().size)
        assertEquals(3, (mainTag.getTag("ListTagFloat") as TagList<*>).getList().size)
        assertEquals(3, (mainTag.getTag("ListTagString") as TagList<*>).getList().size)
    }

    @Test
    fun testSerialize() {
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