package minecraft.world.region

import nbt.datatypes.TagCompound

class ChunkFactory {

    fun build(tag: TagCompound): Chunk {
        return Chunk(tag)
    }
}