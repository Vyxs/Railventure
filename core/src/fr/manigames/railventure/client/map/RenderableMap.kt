package fr.manigames.railventure.client.map

import fr.manigames.railventure.common.map.BaseMap


open class RenderableMap(
    private val chunkLoader: (RenderableChunk) -> Unit
) : BaseMap() {

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