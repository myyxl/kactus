package network.server.inbound.packet.login

import io.netty.buffer.ByteBuf
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.util.ReferenceCountUtil
import network.server.outbound.packet.login.OutboundLoginDisconnectPacket
import network.server.ClientState
import network.server.getMinecraft
import network.server.inbound.packet.InboundPacket
import network.server.sendPacket
import network.stream.PacketInputStream
import kotlin.properties.Delegates

class InboundLoginStartPacket : InboundPacket {

    override val packetId = 0x00
    override val state = ClientState.LOGIN

    private var username: String by Delegates.notNull()

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        try {
            PacketInputStream(msg as ByteBuf).apply {
                username = readString()
            }
        } finally {
            ReferenceCountUtil.release(msg)
        }

    }

    override fun onReceive(channel: Channel) {
        if(username.length > 16) {
            val responsePacket = OutboundLoginDisconnectPacket("Username too long!")
            channel.sendPacket(responsePacket)
            return
        }
        channel.config().getMinecraft().initializeNewPlayer(channel, username)
    }
}