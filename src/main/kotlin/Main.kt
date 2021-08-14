import network.Server

fun main() {
    val server = Server("0.0.0.0", 25565)
    server.start()
}