package minecraft.world.level

import minecraft.world.world.World
import minecraft.world.world.WorldType
import java.io.File

class Level(val directory: File) {
    val properties = LevelProperties(directory)
    val worldOverworld = World(WorldType.OVERWORLD, this)
    val worldNether = World(WorldType.NETHER, this)
    val worldEnd = World(WorldType.END, this)
}