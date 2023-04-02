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

        val widthInChunk = 10
        val heightInChunk = 10
        val types = arrayOf(
            TileType.WATER,
            TileType.SAND,
            TileType.SAND,
            TileType.DIRT,
            TileType.GRASS,
            TileType.GRASS,
            TileType.GRASS,
            TileType.GRASS,
            TileType.GRASS
        )
        val scale = 8


        generateDefaultMap(widthInChunk, heightInChunk)

        generateNoiseMap(widthInChunk, heightInChunk, types, scale)

        generationProgress.set(1f)
    }

    private fun generateNoiseMap(widthInChunk: Int, heightInChunk: Int, types: Array<TileType>, scale: Int) {
        val widthInTiles = widthInChunk * MAP_CHUNK_SIZE
        val heightInTiles = heightInChunk * MAP_CHUNK_SIZE

        val noiseMap = Array(heightInTiles) { IntArray(widthInTiles) }

        for (y in 0 until heightInTiles) {
            for (x in 0 until widthInTiles) {
                val nx = x / widthInTiles.toDouble() * scale - 1
                val ny = y / heightInTiles.toDouble() * scale - 1
                noiseMap[y][x] = getNoise(nx, ny, types.size)
            }
        }

        printNoiseMap(noiseMap)

        /**
         * In world chunk are like this:
         *
         * (y, x)
         * [ 1,-1] [ 1, 0] [ 1, 1]
         * [ 0,-1] [ 0, 0] [ 0, 1]
         * [-1,-1] [-1, 0] [-1, 1]
         *
         * In noise map are like this:
         *
         * (y, x)
         * [ 0, 0] [ 0, 1] [ 0, 2]
         * [ 1, 0] [ 1, 1] [ 1, 2]
         * [ 2, 0] [ 2, 1] [ 2, 2]
         *
         * So we need to invert y axis
         **/
        for (y in 0 until heightInChunk) {
            for (x in 0 until widthInChunk) {
                val chunk = RenderableChunk(x, (heightInChunk - 1) - y)
                for (tileY in 0 until MAP_CHUNK_SIZE) {
                    for (tileX in 0 until MAP_CHUNK_SIZE) {
                        val noiseX = x * MAP_CHUNK_SIZE + tileX
                        val noiseY = y * MAP_CHUNK_SIZE + tileY
                        chunk.setTile(tileX, tileY, 0, getTileType(noiseMap, noiseY, noiseX, types))
                    }
                }
                setChunk(x, y, chunk)
            }
        }
    }


    private fun getTileType(
        noiseMap: Array<IntArray>,
        y: Int,
        x: Int,
        types: Array<TileType>
    ) = types[noiseMap[y][x]]

    private fun printNoiseMap(noiseMap: Array<IntArray>) {
        val stringBuffer = StringBuffer()
        for (y in noiseMap.indices) {
            for (x in noiseMap[y].indices) {
                val char = when (noiseMap[y][x]) {
                    0 -> ' '
                    1 -> '.'
                    2 -> 'o'
                    3 -> 'O'
                    4 -> '0'
                    5 -> '@'
                    6 -> '%'
                    7 -> '#'
                    8 -> '&'
                    else -> ' '
                }
                stringBuffer.append(char)
            }
            stringBuffer.append("\n")
        }
        println(stringBuffer.toString())
    }

    private fun generateDefaultMap(width: Int, height: Int) {
        for (x in 0 until width) {
            for (y in 0 until height) {
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