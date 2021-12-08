import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class JDKTest {

    @Test
    fun testJavaVersion() {
        val javaVersion = System.getProperty("java.version").split(".").first().toInt()
        assertEquals(16, javaVersion)
    }
}