package fr.manigames.railventure.api.map.procedural

import fr.manigames.railventure.api.core.Metric
import fr.manigames.railventure.api.map.generation.OpenSimplexNoise
import fr.manigames.railventure.client.map.RenderableChunk
import fr.manigames.railventure.client.map.RenderableMap
import java.util.concurrent.atomic.AtomicReference

abstract class ProceduralMap(
    private var config: ProceduralMapConfig,
    private val tileHandler: ProceduralTileHandler,
    chunkLoader: (RenderableChunk) -> Unit
) : RenderableMap(chunkLoader) {

    private val altitude = OpenSimplexNoise(config.altitudeSeed) // base relief
    private val humidity = OpenSimplexNoise(config.humiditySeed) // biome related
    private val temperature = OpenSimplexNoise(config.temperatureSeed) // biome related
    private val generationProgress = AtomicReference(0f)

    /**
     * Get the config of the map.
     *
     * @return The config of the map.
     */
    fun getConfig(): ProceduralMapConfig = config

    /**
     * Set the config of the map. If the config has the regenerateOnConfigChange property set to true, the map will be
     * regenerated.
     *
     * @param config The new config of the map.
     */
    fun setConfig(config: ProceduralMapConfig) {
        if (config == this.config)
            return
        this.config = config
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
        val size = config.defaultGenerationSize
        for (x in -(size / 2) until (size / 2))
            for (y in -(size / 2) until (size / 2)) {
                generateChunk(config.defaultGenerationPosition.first + x, config.defaultGenerationPosition.second + y)
                generationProgress.set((x + (size / 2)) / size.toFloat())
            }
        generationProgress.set(1f)
    }

    /**
     * Generate a chunk.
     *
     * @param x The x position of the chunk.
     * @param y The y position of the chunk.
     */
    override fun generateChunk(x: Int, y: Int) {
        val chunk = RenderableChunk(x, y)
        val size = Pair(Metric.MAP_CHUNK_SIZE * x, Metric.MAP_CHUNK_SIZE * y)
        for (tileX in 0 until Metric.MAP_CHUNK_SIZE)
            for (tileY in 0 until Metric.MAP_CHUNK_SIZE) {
                val ux = size.first + tileX
                val uy = size.second + tileY
                val nx = ux / config.scale
                val ny = uy / config.scale
                val alt = altitude.random2D(nx, ny)
                val hum = humidity.random2D(nx, ny)
                val temp = temperature.random2D(nx, ny)
                chunk.setTile(tileX, tileY, 0, tileHandler.determineTileType(alt, hum, temp, ux, uy))
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