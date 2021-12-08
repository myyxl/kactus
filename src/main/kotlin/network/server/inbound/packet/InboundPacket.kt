package network.server.inbound.packet

import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import network.server.ClientState

interface InboundPacket {

    val packetId: Int
    val state: ClientState

    fun channelRead(ctx: ChannelHandlerContext?, msg: Any?)
    fun onReceive(channel: Channel)

}