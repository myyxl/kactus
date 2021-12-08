package network.server

import io.netty.channel.Channel
import io.netty.channel.ChannelConfig
import io.netty.channel.ChannelOption
import minecraft.MinecraftServer
import network.server.outbound.packet.OutboundPacket

private class ChannelState : ChannelOption<ClientState>("CLIENT_STATE")
private class MinecraftInstance : ChannelOption<MinecraftServer>("MINECRAFT_INSTANCE")

fun ChannelConfig.setState(clientState: ClientState) {
    this.setOption(ChannelState(), clientState)
}

fun ChannelConfig.getState(): ClientState {
    return this.getOption(ChannelState())
}

fun ChannelConfig.setMinecraft(minecraftServer: MinecraftServer) {
    this.setOption(MinecraftInstance(), minecraftServer)
}

fun ChannelConfig.getMinecraft(): MinecraftServer {
    return this.getOption(MinecraftInstance())
}

fun Channel.sendPacket(packet: OutboundPacket) {

}

enum class ClientState {
    HANDSHAKE,
    STATUS,
    LOGIN,
    PLAY
}