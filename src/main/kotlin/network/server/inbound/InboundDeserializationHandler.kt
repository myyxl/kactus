package network.server.inbound

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.util.ReferenceCountUtil
import network.server.getState
import network.server.inbound.packet.InboundPacketRegister
import network.stream.PacketInputStream

class InboundDeserializationHandler : ChannelInboundHandlerAdapter() {

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        try {
            super.channelRead(ctx, msg)
            val client = ctx?.channel()
            val state = client?.config()?.getState()
            val stream = PacketInputStream(msg as ByteBuf)
            val packetLength = stream.readVarInt()
            val packetId = stream.readVarInt()
            // val packetData = stream.byteBuf.readBytes(packetLength - 1) TODO: Fix, need to know length of packetId (probably useless now)
            InboundPacketRegister().getPacket(state!!, packetId)?.let {
                it.channelRead(ctx, msg)
                it.onReceive(client)
            }
        } finally {
            ReferenceCountUtil.release(msg)
        }
    }
}