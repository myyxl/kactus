package network.server.inbound.packet.status

import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.util.ReferenceCountUtil
import network.server.outbound.packet.status.OutboundStatusResponsePacket
import network.server.ClientState
import network.server.getMinecraft
import network.server.inbound.packet.InboundPacket
import network.server.sendPacket

class InboundStatusRequestPacket : InboundPacket {

    override val packetId = 0x00
    override val state = ClientState.STATUS

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        ReferenceCountUtil.release(msg)
    }

    override fun onReceive(channel: Channel) {
        val minecraftServer = channel.config().getMinecraft()
        val currentPlayers = minecraftServer.onlinePlayers.size
        val responsePacket = OutboundStatusResponsePacket("""
            {"version": 
                {"name": "${minecraftServer.gameVersion}","protocol": ${minecraftServer.protocolVersion}},
                "players": {"max": ${minecraftServer.maxPlayers},"online": $currentPlayers},
                "description": {"text": "${minecraftServer.serverMotd}"}}""".trimIndent())
        channel.sendPacket(responsePacket)
    }
}