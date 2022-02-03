package net.propromp.stepsforidiots

import net.minestom.server.coordinate.Pos
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.instance.block.Block

class ForwardBlockGenerator(val instance: InstanceContainer, val seed: Long,firstPos: Pos, val size: Int) {
    val posList = mutableListOf<Pos>()

    init {
        posList.add(firstPos)
        instance.setBlock(firstPos,getRandomBlock())
    }

    fun generateNext() {
        val newPos = getNextPos(posList.last())
        posList.add(newPos)
        instance.setBlock(newPos,getRandomBlock())
        if(posList.size >= size) {
            instance.setBlock(posList.first(), Block.AIR)
            posList.removeFirst()
        }
    }

    fun getRandomBlock() = Block.values().filter {it.name().endsWith("_concrete")}.random()

    private fun getNextPos(position: Pos) = position.apply { x, y, z, yaw, pitch -> Pos(x, y, z + 1, yaw, pitch) }

    fun hasNext(position: Pos) = posList.contains(getNextPos(position))
}