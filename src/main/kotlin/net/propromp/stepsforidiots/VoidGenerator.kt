package net.propromp.stepsforidiots

import net.minestom.server.instance.Chunk
import net.minestom.server.instance.ChunkGenerator
import net.minestom.server.instance.ChunkPopulator
import net.minestom.server.instance.batch.ChunkBatch
import net.minestom.server.instance.block.Block

object VoidGenerator:ChunkGenerator {
    /**
     * Called when the blocks in the [Chunk] should be set using [ChunkBatch.setBlock]
     * or similar.
     *
     *
     * WARNING: all positions are chunk-based (0-15).
     *
     * @param batch  the [ChunkBatch] which will be flush after the generation
     * @param chunkX the chunk X
     * @param chunkZ the chunk Z
     */
    override fun generateChunkData(batch: ChunkBatch, chunkX: Int, chunkZ: Int) {
        if(chunkX to chunkZ == 0 to 0) {
            (0..8).forEach { x ->
                (0..5).forEach { y ->
                    (0..5).forEach { z ->
                        batch.setBlock(x,y,z, Block.STONE)
                    }
                }
            }
        }
    }

    /**
     * Gets all the [ChunkPopulator] of this generator.
     *
     * @return a [List] of [ChunkPopulator], can be null or empty
     */
    override fun getPopulators(): MutableList<ChunkPopulator>? {
        return null
    }
}