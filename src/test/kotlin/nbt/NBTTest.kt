package nbt

import nbt.datatypes.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.EOFException
import java.lang.Double.NaN
import java.util.zip.ZipException
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class NBTTest {

    @Test
    fun testDecompression() {
        val compoundTag = NBT(NBTCompression.GZIP).readFromFile("src/test/resources/test-compression.nbt")
        val byteTag = (compoundTag.getTag("OnGround") as TagByte).value
        val listTag = (compoundTag.getTag("Pos") as TagList<*>)
        val doubleTag = (listTag.getList()[1]).value
        assertEquals(byteTag, (1).toByte())
        assertEquals(doubleTag, NaN)

        /*
            Since test-compression.nbt is compressed but no compression is given in NBT() it should throw an EOFException
         */
        assertThrows<EOFException> {
            NBT().readFromFile("src/test/resources/test-compression.nbt")
        }
    }

    @Test
    fun testCompression() {
        val compressedTag = NBT(NBTCompression.GZIP).serialize(TagString("Hello", "World"))
        assertThrows<EOFException> {
            assertNotEquals("Hello", (NBT().deserialize(compressedTag) as TagString).name)
        }
        assertEquals("Hello", (NBT(NBTCompression.GZIP).deserialize(compressedTag) as TagString).name)
    }

    @Test
    fun testDeserialize() {
        assertThrows<ZipException> {
            NBT(NBTCompression.GZIP).readFromFile("src/test/resources/test-deserialize.nbt")
        }
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

        assert(byteArrayOf(-34, -83, -66, -17).contentEquals((mainTag.getTag("ByteArrayTag") as TagByteArray).value))

        assert(intArrayOf(286331153, 572662306, 858993459, 1145324612, 1431655765
        ).contentEquals(((mainTag.getTag("IntArrayTag") as TagIntArray).value)))

        assert(longArrayOf(1229782938247303441, 2459565876494606882, 3689348814741910323
        ).contentEquals((mainTag.getTag("LongArrayTag") as TagLongArray).value))

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

        assertEquals(4, (mainTag.getTag("ListTagCompoundEmpty") as TagList<*>).getList().size)
        val compoundTag1 = (mainTag.getTag("ListTagCompoundEmpty") as TagList<*>).getList()[0] as TagCompound
        val compoundTag2 = (mainTag.getTag("ListTagCompoundEmpty") as TagList<*>).getList()[1] as TagCompound
        val compoundTag3 = (mainTag.getTag("ListTagCompoundEmpty") as TagList<*>).getList()[2] as TagCompound
        val compoundTag4 = (mainTag.getTag("ListTagCompoundEmpty") as TagList<*>).getList()[3] as TagCompound
        assertEquals(0, compoundTag1.getTags().size)
        assertEquals(0, compoundTag2.getTags().size)
        assertEquals(0, compoundTag3.getTags().size)
        assertEquals(0, compoundTag4.getTags().size)

        val compoundTagSuperNested = mainTag.getTag("TagCompoundSuperNested") as TagCompound
        assertEquals(1, compoundTagSuperNested.getTags().size)

        val level1 = compoundTagSuperNested.getTag("Level1") as TagCompound
        assertEquals("Level1", level1.name)
        assertEquals(1, level1.getTags().size)

        val level2 = level1.getTag("Level2") as TagList<*>
        assertEquals("Level2", level2.name)
        assertEquals(1, level2.getList().size)

        val level3 = level2.getList()[0] as TagList<*>
        assertEquals(1, level3.getList().size)

        val level3compound = level3.getList()[0] as TagCompound
        assertEquals(1, level3compound.getTags().size)

        val level4 = level3compound.getTag("Level3") as TagList<*>
        assertEquals("Level3", level4.name)
        assertEquals(1, level4.getList().size)

    }

    @Test
    fun testSerialize() {
        var tag: Tag = TagString("tagName123", "tagValue345")
        var serialized = NBT().serialize(tag)
        var deserialized = NBT().deserialize(serialized)
        assert(checkTagInfo(tag, deserialized))

        assertThrows<ZipException> {
            NBT(NBTCompression.GZIP).deserialize(serialized)
        }

        tag = TagString("tagName123", "")
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

        tag = TagLong("TagLong", 100000L)
        serialized = NBT().serialize(tag)
        deserialized = NBT().deserialize(serialized)
        assert(checkTagInfo(tag, deserialized))

        tag = TagShort("TagShort", 1337)
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

        tag = TagInt("TagInt", 123456789)
        serialized = NBT().serialize(tag)
        deserialized = NBT().deserialize(serialized)
        assert(checkTagInfo(tag, deserialized))

        tag = TagFloat("TagFloat", 3.1415F)
        serialized = NBT().serialize(tag)
        deserialized = NBT().deserialize(serialized)
        assert(checkTagInfo(tag, deserialized))

        tag = TagFloat("TagFloatNaN", NaN.toFloat())
        serialized = NBT().serialize(tag)
        deserialized = NBT().deserialize(serialized)
        assert(checkTagInfo(tag, deserialized))

        tag = TagDouble("TagDoubleNaN", NaN)
        serialized = NBT().serialize(tag)
        deserialized = NBT().deserialize(serialized)
        assert(checkTagInfo(tag, deserialized))

        tag = TagDouble("TagDouble", 1.1)
        serialized = NBT().serialize(tag)
        deserialized = NBT().deserialize(serialized)
        assert(checkTagInfo(tag, deserialized))

        tag = TagByteArray("TagByteArray", byteArrayOf(0x00, 0x12, 0x66, 0x02))
        serialized = NBT().serialize(tag)
        deserialized = NBT().deserialize(serialized) as TagByteArray
        assert(tag.value.contentEquals(deserialized.value))

        tag = TagIntArray("TagIntArray", intArrayOf(1, 2, 3, 4))
        serialized = NBT().serialize(tag)
        deserialized = NBT().deserialize(serialized) as TagIntArray
        assert(tag.value.contentEquals(deserialized.value))

        tag = TagLongArray("TagLongArray", longArrayOf(111111, 222222, 333333, 444444))
        serialized = NBT().serialize(tag)
        deserialized = NBT().deserialize(serialized) as TagLongArray
        assert(tag.value.contentEquals(deserialized.value))
        
    }

    private fun checkTagInfo(tag1: Tag, tag2: Tag): Boolean {
        return tag1.name == tag2.name && tag1.typeId == tag2.typeId && tag1.value == tag2.value
    }
}