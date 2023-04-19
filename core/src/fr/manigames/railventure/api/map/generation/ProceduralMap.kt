package fr.manigames.railventure.api.map.generation

import fr.manigames.railventure.api.core.Metric
import fr.manigames.railventure.api.map.noise.OpenSimplexNoise
import fr.manigames.railventure.client.map.RenderableChunk
import fr.manigames.railventure.client.map.RenderableMap
import java.util.concurrent.atomic.AtomicReference

open class ProceduralMap : RenderableMap() {

    protected var proceduralMapConfig: ProceduralMapConfig = ProceduralMapConfig()
        private set
    protected var tileHandler: ProceduralTileHandler = ProceduralTileHandler.DEFAULT
        private set

    private val altitude = OpenSimplexNoise(proceduralMapConfig.altitudeSeed) // base relief
    private val humidity = OpenSimplexNoise(proceduralMapConfig.humiditySeed) // biome related
    private val temperature = OpenSimplexNoise(proceduralMapConfig.temperatureSeed) // biome related
    protected val generationProgress = AtomicReference(0f)

    /**
     * Set the procedural map config of the map. The procedural map config is used to generate the map.
     *
     * @param proceduralMapConfig The procedural map config of the map.
     **/
    fun setProceduralMapConfig(proceduralMapConfig: ProceduralMapConfig) {
        this.proceduralMapConfig = proceduralMapConfig
    }

    /**
     * Set the tile handler of the map. The tile handler is used to determine the tile type of a tile.
     *
     * @param tileHandler The tile handler of the map.
     **/
    fun setTileHandler(tileHandler: ProceduralTileHandler) {
        this.tileHandler = tileHandler
    }

    /**
     * Get the config of the map.
     *
     * @return The config of the map.
     */
    fun getConfig(): ProceduralMapConfig = proceduralMapConfig

    /**
     * Set the config of the map. If the config has the regenerateOnConfigChange property set to true, the map will be
     * regenerated.
     *
     * @param config The new config of the map.
     */
    fun setConfig(config: ProceduralMapConfig) {
        if (config == this.proceduralMapConfig)
            return
        this.proceduralMapConfig = config
        if (config.regenerateOnConfigChange)
            markAllChunksDirty()
    }

    /**
     * Get the progress of the generation of the map. The value is between 0 and 1.
     *
     * @return The progress of the generation of the map.
     */
    override fun getGenerationProgress(): Float = generationProgress.get()

    /**
     * Generate the map.
     */
    override fun generate() {
        val size = proceduralMapConfig.defaultGenerationSize
        for (x in -(size / 2) until (size / 2))
            for (y in -(size / 2) until (size / 2)) {
                generateChunk(proceduralMapConfig.defaultGenerationPosition.first + x, proceduralMapConfig.defaultGenerationPosition.second + y)
                generationProgress.set((x + (size / 2)) / size.toFloat())
            }
        generationProgress.set(1f)
    }

    /**
     * Generate a chunk. If the chunk is already generated, it will not be generated again unless the regenerate
     * parameter is set to true.
     *
     * @param x The x position of the chunk.
     * @param y The y position of the chunk.
     * @param regenerate If the chunk should be regenerated if it is already generated.
     */
    override fun generateChunk(x: Int, y: Int, regenerate: Boolean) {
        if (hasChunk(x, y) && !regenerate)
            return
        val chunk = RenderableChunk(x, y)
        val size = Pair(Metric.MAP_CHUNK_SIZE * x, Metric.MAP_CHUNK_SIZE * y)
        for (tileX in 0 until Metric.MAP_CHUNK_SIZE)
            for (tileY in 0 until Metric.MAP_CHUNK_SIZE) {
                val ux = size.first + tileX
                val uy = size.second + tileY
                val nx = ux / proceduralMapConfig.scale
                val ny = uy / proceduralMapConfig.scale
                val alt = altitude.random2D(nx, ny)
                val hum = humidity.random2D(nx, ny)
                val temp = temperature.random2D(nx, ny)
                chunk.setTileStack(tileX, Metric.MAP_CHUNK_SIZE - 1 - tileY, tileHandler.determineTileLayer(alt, hum, temp, ux, uy))
            }
        setChunk(chunk)
    }

    /**
     * Mark all chunks as dirty.
     */
    protected fun markAllChunksDirty() {
        for (chunk in chunks.values) {
            (chunk as RenderableChunk).markDirty()
        }
    }
}