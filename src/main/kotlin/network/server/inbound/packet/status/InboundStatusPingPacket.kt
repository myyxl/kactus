package network.server.inbound.packet.status

import io.netty.buffer.ByteBuf
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.util.ReferenceCountUtil
import network.server.outbound.packet.status.OutboundStatusPongPacket
import network.server.ClientState
import network.server.inbound.packet.InboundPacket
import network.server.sendPacket
import network.stream.PacketInputStream
import kotlin.properties.Delegates

class InboundStatusPingPacket : InboundPacket {

    override val packetId = 0x01
    override val state = ClientState.STATUS

    private var payload: Long by Delegates.notNull()

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        try {
            PacketInputStream(msg as ByteBuf).apply {
                payload = byteBuf.readLong()
            }
        } finally {
            ReferenceCountUtil.release(msg)
        }
    }

    override fun onReceive(channel: Channel) {
        val responsePacket = OutboundStatusPongPacket(payload)
        channel.sendPacket(responsePacket)
    }
}