package minecraft.world.level

import nbt.NBT
import nbt.NBTCompression
import nbt.datatypes.*
import java.io.File

open class LevelProperties(levelDirectory: File) {

    val difficulty: Byte
    val isHardcore: Boolean
    val isRaining: Boolean
    val seed: Long
    val versionName: String
    val spawnX: Int
    val spawnY: Int
    val spawnZ: Int
    val levelName: String

    init {
        NBT(NBTCompression.GZIP).apply {
            val dataTag = readFromFile(levelDirectory.absolutePath + "/level.dat").getTag("Data") as TagCompound
            dataTag.apply {
                isHardcore = (getTag("hardcore") as TagByte).value != 0.toByte()
                isRaining = (getTag("raining") as TagByte).value != 0.toByte()
                seed = ((getTag("WorldGenSettings") as TagCompound).getTag("seed") as TagLong).value
                versionName = ((getTag("Version") as TagCompound).getTag("Name") as TagString).value
                spawnX = (getTag("SpawnX") as TagInt).value
                spawnY = (getTag("SpawnY") as TagInt).value
                spawnZ = (getTag("SpawnZ") as TagInt).value
                levelName = (getTag("LevelName") as TagString).value
                difficulty = (getTag("Difficulty") as TagByte).value
            }
        }
    }
}