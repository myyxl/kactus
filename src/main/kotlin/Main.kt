import nbt.NBTReader
import nbt.datatypes.TagCompound
import network.Server

fun main() {
    val tagCompound = NBTReader().readFile("servers.dat")
    //val server = Server("0.0.0.0", 25565)
    //server.start()
}