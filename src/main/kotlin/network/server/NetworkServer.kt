package network.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import minecraft.MinecraftServer
import network.server.inbound.packet.InboundPacketRegister

class NetworkServer(bindInterface: String, bindPort: Int, minecraftServer: MinecraftServer) {

    init {
        InboundPacketRegister.registerPackets()
        val bossGroup = NioEventLoopGroup()
        val workerGroup = NioEventLoopGroup()
        try {
            val bootstrap = ServerBootstrap().apply {
                group(bossGroup, workerGroup)
                channel(NioServerSocketChannel::class.java)
                childHandler(ClientChannelInitializer(minecraftServer))
            }
            val future = bootstrap.bind(bindInterface, bindPort).sync()
            future.channel().closeFuture().sync()
        } finally {
            workerGroup.shutdownGracefully()
            bossGroup.shutdownGracefully()
        }
    }
}






















