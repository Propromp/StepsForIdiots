package net.propromp.stepsforidiots

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.GameMode
import net.minestom.server.entity.Player
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.timer.TaskSchedule
import net.propromp.stepsforidiots.util.LightEngine
import java.time.Duration

class Parkour(val player: Player) {
    companion object {
        val instances = mutableMapOf<Player, Parkour>()
        fun getOrCreate(player: Player) = instances.getOrPut(player) { Parkour(player) }
    }

    lateinit var instance: InstanceContainer

    lateinit var forwardBlockGenerator: ForwardBlockGenerator

    val spawnPos = Pos(4.5,7.0,3.0)

    var record = 0.0

    init {
        MinecraftServer.getSchedulerManager().buildTask {
            updateActionBar()
            if(player.position.y < 0 && !isDead) {
                kill()
            }
            if(!isDead) {
                record = player.position.z - spawnPos.z
            }
            if(player.position.z+10>forwardBlockGenerator.posList.last().z) {
                forwardBlockGenerator.generateNext()
            }
        }.repeat(TaskSchedule.tick(1)).schedule()
    }

    var isDead = false

    fun kill() {
        isDead = true
        player.gameMode = GameMode.SPECTATOR
        player.showTitle(Title.title(Component.empty(),Component.text("5秒後にリスポーンします。")))
        MinecraftServer.getSchedulerManager().buildTask {
            respawn()
        }.delay(Duration.ofSeconds(5)).schedule()
    }

    fun init() {
        instance = MinecraftServer.getInstanceManager().createInstanceContainer()
        instance.enableAutoChunkLoad(true)
        instance.chunkGenerator = VoidGenerator

        forwardBlockGenerator = ForwardBlockGenerator(instance,(Long.MIN_VALUE..Long.MAX_VALUE).random(),
            Pos(4.0,5.0,6.0),15
        )

        player.respawnPoint = spawnPos
    }

    fun respawn() {
        init()
        if(player.instance != instance) {
            player.setInstance(instance, spawnPos)
        }
        MinecraftServer.getSchedulerManager().buildTask {
            player.gameMode = GameMode.ADVENTURE
            LightEngine().recalculateInstance(instance)
            isDead = false
        }.delay(TaskSchedule.tick(1)).schedule()
    }

    fun updateActionBar() {
        player.sendActionBar(
            Component.text("記録: ").color(NamedTextColor.GRAY)
                .append(Component.text("%.2f".format(record) + "m").color(NamedTextColor.YELLOW))
        )
    }
}