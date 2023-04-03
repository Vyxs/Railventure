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

        val widthInChunk = 100
        val heightInChunk = 100
        val types = arrayOf(
            TileType.WATER,
            TileType.SAND,
            TileType.SAND,
            TileType.DIRT,
            TileType.GRASS,
            TileType.GRASS,
            TileType.GRASS,
            TileType.GRASS,
            TileType.GRASS,
            TileType.GRASS,
            TileType.GRASS,
            TileType.MOSSY_STONE,
            TileType.STONE,
            TileType.STONE,
            TileType.STONE
        )
        val scale = 50

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
        val offset = minOf(widthInChunk, heightInChunk) / 2
        for (y in 0 until heightInChunk) {
            for (x in 0 until widthInChunk) {
                val chunkY = (heightInChunk - 1) - y
                val chunk = RenderableChunk(x - offset, chunkY - offset)
                for (tileY in 0 until MAP_CHUNK_SIZE) {
                    for (tileX in 0 until MAP_CHUNK_SIZE) {
                        val noiseX = x * MAP_CHUNK_SIZE + tileX
                        val noiseY = y * MAP_CHUNK_SIZE + tileY
                        chunk.setTile(tileX, tileY, 0, getTileType(noiseMap, noiseY, noiseX, types))
                    }
                }
                setChunk(chunk)
                generationProgress.set((y * widthInChunk + x) / (widthInChunk * heightInChunk).toFloat())
            }
        }
    }

    private val random = Random(seed)
    private val rndTiles = arrayOf(TileType.SAND, TileType.DIRT, TileType.DIRT)
    private val randTkt = arrayOf(TileType.GRASS, TileType.MOSSY_STONE, TileType.MOSSY_STONE, TileType.MOSSY_STONE, TileType.STONE)


    private fun getTileType(
        noiseMap: Array<IntArray>,
        y: Int,
        x: Int,
        types: Array<TileType>
    ) : TileType {
        var id = noiseMap[y][x]
        return if (types[id] == TileType.DIRT) {
            id = rndTiles[random.nextInt(0, rndTiles.size)].code
            TileType.fromCode(id)
        } else if (types[id] == TileType.MOSSY_STONE) {
            id = randTkt[random.nextInt(0, randTkt.size)].code
            TileType.fromCode(id)
        } else
            types[id]
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

    private fun getNoise(x: Double, y: Double, types: Int) : Int {
        return floor(abs(noise.random2D(x, y)) * types).toInt()
    }
}