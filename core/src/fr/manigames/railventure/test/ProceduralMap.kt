package fr.manigames.railventure.test

import fr.manigames.railventure.api.core.Metric.MAP_CHUNK_SIZE
import fr.manigames.railventure.api.debug.Logger
import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.map.generation.OpenSimplexNoise
import fr.manigames.railventure.client.map.RenderableChunk
import fr.manigames.railventure.client.map.RenderableMap
import java.util.concurrent.atomic.AtomicReference
import kotlin.math.abs
import kotlin.math.floor
import kotlin.random.Random

class ProceduralMap(
    chunkLoader: (RenderableChunk) -> Unit
) : RenderableMap(chunkLoader) {

    private val generationProgress = AtomicReference(0f)

    override fun getGenerationProgress(): Float = generationProgress.get()

    private val seed = 4455L
    private val noise = OpenSimplexNoise(seed)

    override fun generate() {

        val widthInChunk = 3
        val heightInChunk = 3


        generateDefaultMap(widthInChunk, heightInChunk)

        for (x in 0 until 64)
            setTile(x, 0, 0, TileType.GRASS)

        //generateNoiseMap(widthInChunk, heightInChunk)

        generationProgress.set(1f)
    }

    private fun generateNoiseMap(widthInChunk: Int, heightInChunk: Int) {
        val scale = 1.0
        val widthInTiles = widthInChunk * MAP_CHUNK_SIZE
        val heightInTiles = heightInChunk * MAP_CHUNK_SIZE

        val noiseMap = Array(heightInTiles) { IntArray(widthInTiles) }

        for (y in 0 until heightInTiles) {
            for (x in 0 until widthInTiles) {
                val nx = x / widthInTiles.toDouble() * 2 - 1
                val ny = y / heightInTiles.toDouble() * 2 - 1

                noiseMap[y][x] = getNoise(nx, ny, 3)
            }
        }

        printNoiseMap(noiseMap)

        for (y in 0 until heightInTiles) {
            for (x in 0 until widthInTiles) {

                val tileType = when (noiseMap[y][x]) {
                    0 -> TileType.WATER
                    1 -> TileType.GRASS
                    2 -> TileType.DIRT
                    else -> TileType.GRASS
                }
                setTile(x, y, 0, tileType)
            }
        }
    }

    private fun printNoiseMap(noiseMap: Array<IntArray>) {
        val stringBuffer = StringBuffer()
        for (y in noiseMap.indices) {
            for (x in noiseMap[y].indices) {
                val char = when (noiseMap[y][x]) {
                    0 -> ' '
                    1 -> '.'
                    2 -> 'o'
                    3 -> 'O'
                    else -> ' '
                }
                stringBuffer.append(char)
            }
            stringBuffer.append("\n")
        }
        println(stringBuffer.toString())
    }

    private fun generateDefaultMap(width: Int, height: Int) {
        for (x in -width..width) {
            for (y in -height..height) {
                generateDefaultChunk(x, y)
            }
        }
    }

    private fun getNoise(x: Double, y: Double, types: Int) : Int {
        return floor(abs(noise.random2D(x, y)) * types).toInt()
    }

    private fun generateDefaultChunk(x: Int, y: Int) {
        val chunk = RenderableChunk(x, y)
        for (tileX in 0 until MAP_CHUNK_SIZE) {
            for (tileY in 0 until MAP_CHUNK_SIZE) {
                chunk.setTile(tileX, tileY, 0, TileType.WATER)
            }
        }
        setChunk(x, y, chunk)
    }
}