package network.server.inbound

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.util.ReferenceCountUtil

class InboundDecompressionHandler : ChannelInboundHandlerAdapter() {

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        try {
            super.channelRead(ctx, msg)
            ctx?.fireChannelRead(msg)
        } finally {
            ReferenceCountUtil.release(msg)
        }
    }

}