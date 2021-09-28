package minecraft.world.world

import minecraft.world.level.Level

class World(val type: WorldType, val level: Level) {
    val properties = level.properties
}