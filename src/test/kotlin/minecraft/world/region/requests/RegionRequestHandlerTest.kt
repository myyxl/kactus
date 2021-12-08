package minecraft.world.region.requests

import minecraft.world.level.Level
import nbt.datatypes.TagCompound
import nbt.datatypes.TagInt
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class RegionRequestHandlerTest {

    @Test
    fun testReadChunk() {
        val testLevel = Level(File("src/test/resources/world-test"))
        var readRequest = ReadRequest(-24, 0, testLevel.worldOverworld)
        var chunk = RegionRequestHandler().handleRequest(readRequest)
        assertNotNull(chunk)
        assertEquals(14, (chunk.tag.getTag("Level") as TagCompound).getTags().size)
        assertEquals(2730, (chunk.tag.getTag("DataVersion") as TagInt).value)
        readRequest = ReadRequest(-1337, 1337, testLevel.worldOverworld)
        chunk = RegionRequestHandler().handleRequest(readRequest)
        assertNull(chunk)
    }

}