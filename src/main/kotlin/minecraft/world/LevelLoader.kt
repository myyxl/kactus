package minecraft.world

import minecraft.world.level.Level
import java.io.File

class LevelLoader {

    companion object {
        private val levelList = ArrayList<Level>()
        fun addLevel(level: Level) = levelList.add(level)
        fun getLevels(): List<Level> = levelList.toList()
    }

    fun loadLevels() {
        findLevels()?.forEach {
            addLevel(Level(it))
        }
    }

    private fun findLevels(): List<File>? {
        return File("./").listFiles { pathname ->
            pathname.isDirectory
        }?.filter {
            File(it.absolutePath + "/level.dat").exists()
        }
    }

}