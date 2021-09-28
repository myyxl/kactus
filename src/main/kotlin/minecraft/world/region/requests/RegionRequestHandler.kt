package minecraft.world.region.requests

import minecraft.world.region.Chunk
import minecraft.world.region.ChunkFactory
import nbt.NBT
import nbt.NBTCompression
import nbt.datatypes.TagCompound
import java.io.File
import java.io.RandomAccessFile

class RegionRequestHandler {

    @Synchronized fun handleRequest(req: RegionRequest): Chunk? {
        return when(req) {
            is ReadRequest -> readChunk(req)
            is WriteRequest -> writeChunk(req)
            else -> null
        }
    }

    private fun readChunk(req: ReadRequest): Chunk? {
        val regionDir = req.world.level.directory.absolutePath + req.world.type.directory
        val regionX = req.chunkX shr 5
        val regionZ = req.chunkZ shr 5
        val regionFile = regionDir + "region/r.$regionX.$regionZ.mca"
        if(!File(regionFile).exists()) return null
        val chunkLocationOffset = (4 * ((req.chunkX and 31) + (req.chunkZ and 31) * 32)).toLong()

        RandomAccessFile(regionFile, "r").use {
            it.seek(chunkLocationOffset)
            val locationField = it.readInt()
            if(locationField == 0) return null
            val chunkLocation = ((locationField shr 8) * 4096).toLong()

            it.seek(chunkLocation)
            val chunkLength = it.readInt()
            val compressionType = when(it.readByte().toInt()) {
                1 -> NBTCompression.GZIP
                2 -> NBTCompression.ZLIB
                3 -> NBTCompression.NONE
                else -> NBTCompression.ZLIB
            }
            val chunkData = ByteArray(chunkLength)
            it.readFully(chunkData)
            val tag = NBT(compressionType).deserialize(chunkData) as TagCompound
            return ChunkFactory().build(tag)
        }

    }

    private fun writeChunk(req: WriteRequest): Chunk? {
        // TODO: will be implemented later
        return null
    }

}