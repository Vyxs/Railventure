package fr.manigames.railventure.client.map

import fr.manigames.railventure.common.map.BaseMap


abstract class RenderableMap(
    private val chunkLoader: (RenderableChunk) -> Unit
) : BaseMap() {

    /**
     * Return the progress of the map generation. The progress is a float between 0 and 1. If the map is already generated, the progress is 1.
     * The progress is only relevant if [generate] is called and not if [generateChunk] is called.
     *
     * @return The progress of the map generation
     */
    abstract fun getGenerationProgress() : Float

    /**
     * Generate the map. This method must be called only once at the beginning of the game. Then you can use [generateChunk] to generate new chunks.
     */
    abstract fun generate()

    /**
     * Generate the chunk at the given position
     *
     * @param x The x position of the chunk
     * @param y The y position of the chunk
     */
    open fun generateChunk(x: Int, y: Int) = Unit

    /**
     * Load all chunks
     *
     * @return The number of chunks loaded
     */
    fun load() : Int {
        chunks.values.map { it as RenderableChunk }.forEach(chunkLoader)
        return chunks.size
    }

    /**
     * Load the chunk at the given position
     *
     * @param x The x position of the chunk
     * @param y The y position of the chunk
     **/
    fun loadChunk(x: Int, y: Int) {
        if (isChunkLoaded(x, y)) return
        getChunk(x, y)?.let { it as RenderableChunk }?.let(chunkLoader)
    }

    /**
     * Unload the chunk at the given position
     *
     * @param x The x position of the chunk
     * @param y The y position of the chunk
     */
    fun unloadChunk(x: Int, y: Int) {
        getChunk(x, y)?.let { it as RenderableChunk }?.unload()
    }

    /**
     * Return true if the chunk at the given position is loaded. The chunk is loaded if it exists and if it has a texture.
     **/
    fun isChunkLoaded(x: Int, y: Int): Boolean {
        return getChunk(x, y)?.let { it as RenderableChunk }?.isLoaded() ?: false
    }

    /**
     * Return true if the chunk at the given position is loaded. The chunk is loaded if it exists and if it has a texture.
     *
     * @param x The x position of the chunk
     * @param y The y position of the chunk
     * @return True if the chunk is loaded
     **/
    override fun hasChunk(x: Int, y: Int): Boolean {
        return super.hasChunk(x, y) && getChunk(x, y)?.let { it as RenderableChunk }?.isLoaded() ?: false
    }

    /**
     * Return true if the chunk at the given position is dirty. The chunk is dirty when it has been modified.
     * If the chunk is dirty, it should be reloaded to be displayed correctly.
     *
     * @param x The x position of the chunk
     * @param y The y position of the chunk
     * @return True if the chunk is dirty
     **/
    fun isChunkDirty(x: Int, y: Int): Boolean {
        return getChunk(x, y)?.let { it as RenderableChunk }?.isDirty ?: false
    }
}