package network.packet.server.play

import minecraft.world.Location
import network.packet.ServerPacket

class SPlayPlayerPositionAndLookPacket(location: Location, flags: Byte, teleportId: Int, dismountVehicle: Boolean) : ServerPacket() {

    override val packetId: Int = 0x38

    init {
        packet.writeDouble(location.x.toDouble())
        packet.writeDouble(location.y.toDouble())
        packet.writeDouble(location.z.toDouble())
        packet.writeFloat(location.yaw.toFloat())
        packet.writeFloat(location.pitch.toFloat())
        packet.writeByte(flags.toInt())
        packet.writeVarInt(teleportId)
        packet.writeBoolean(dismountVehicle)
    }
}