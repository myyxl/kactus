package minecraft.world.region.requests

import minecraft.world.region.Chunk
import minecraft.world.world.World

open class RegionRequest
class WriteRequest(val chunk: Chunk, val world: World): RegionRequest()
class ReadRequest(val chunkX: Int, val chunkZ: Int, val world: World): RegionRequest()