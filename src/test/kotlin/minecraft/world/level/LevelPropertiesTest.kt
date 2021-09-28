package minecraft.world.level

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

internal class LevelPropertiesTest {

    @Test
    fun testLevelProperties() {
        val levelProperties = LevelProperties(File("src/test/resources/world-test"))
        levelProperties.apply {
            assertEquals(difficulty, 1)
            assertEquals(isHardcore, false)
            assertEquals(levelName, "world")
            assertEquals(isRaining, false)
            assertEquals(seed, 7333030357405246349)
            assertEquals(versionName, "1.17.1")
            assertEquals(spawnX, -208)
            assertEquals(spawnY, 64)
            assertEquals(spawnZ, -160)
        }
    }

}