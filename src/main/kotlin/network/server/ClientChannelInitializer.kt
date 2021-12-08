package network.server

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.util.AttributeKey
import minecraft.MinecraftServer
import network.server.inbound.InboundDecompressionHandler
import network.server.inbound.InboundDecryptionHandler
import network.server.inbound.InboundDeserializationHandler
import network.server.outbound.OutboundCompressionHandler
import network.server.outbound.OutboundEncryptionHandler

class ClientChannelInitializer(private val minecraftServer: MinecraftServer): ChannelInitializer<SocketChannel>() {

    override fun initChannel(ch: SocketChannel?) {
        ch?.let {
            ch.config().setState(ClientState.HANDSHAKE)
            ch.config().setMinecraft(minecraftServer)
            it.pipeline().apply {

                //Inbound
                addLast(InboundDecryptionHandler())
                addLast(InboundDecompressionHandler())
                addLast(InboundDeserializationHandler())

                //Outbound
                addLast(OutboundEncryptionHandler())
                addLast(OutboundCompressionHandler())

            }
        }
    }

}