package network.server.inbound.packet.handshake

import io.netty.buffer.ByteBuf
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.util.ReferenceCountUtil
import network.server.ClientState
import network.server.inbound.packet.InboundPacket
import network.server.setState
import network.stream.PacketInputStream
import kotlin.properties.Delegates

class InboundHandshakePacket : InboundPacket {

    override val packetId = 0x00
    override val state = ClientState.HANDSHAKE

    private var protocolVersion: Int by Delegates.notNull()
    private var serverAddress: String by Delegates.notNull()
    private var serverPort: UShort by Delegates.notNull()
    private var nextState: Int by Delegates.notNull()

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        try {
            PacketInputStream(msg as ByteBuf).apply {
                protocolVersion = readVarInt()
                serverAddress = readString()
                serverPort = byteBuf.readUnsignedShort().toUShort()
                nextState = readVarInt()
            }
        } finally {
            ReferenceCountUtil.release(msg)
        }
    }

    override fun onReceive(channel: Channel) {
        when(nextState) {
            1 -> channel.config().setState(ClientState.STATUS)
            2 -> channel.config().setState(ClientState.LOGIN)
        }
    }
}