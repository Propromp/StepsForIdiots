package net.propromp.stepsforidiots

import net.minestom.server.MinecraftServer
import net.minestom.server.event.player.PlayerLoginEvent

fun main(args: Array<String>) {
    val minecraftServer = MinecraftServer.init()

    val globalEventHandler = MinecraftServer.getGlobalEventHandler()
    globalEventHandler.addListener(PlayerLoginEvent::class.java) { e->
        val parkour = Parkour.getOrCreate(e.player)
        parkour.init()
        e.setSpawningInstance(parkour.instance)
    }

    minecraftServer.start("0.0.0.0",25565)
}