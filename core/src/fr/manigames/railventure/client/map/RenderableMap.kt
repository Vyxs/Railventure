package fr.manigames.railventure.client.map

import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.common.map.BaseMap


open class RenderableMap(
    private val assets: Assets
) : BaseMap() {

    /**
     * Load all chunks
     *
     * @return The number of chunks loaded
     */
    fun load() : Int {
        for (chunk in chunks.values) {
            (chunk as RenderableChunk).load(assets)
        }
        return chunks.size
    }

    /**
     * Load the chunk at the given position
     *
     * @param x The x position of the chunk
     * @param y The y position of the chunk
     **/
    fun loadChunk(x: Int, y: Int) {
        (getChunk(x, y) as RenderableChunk).load(assets)
    }

    /**
     * Unload the chunk at the given position
     *
     * @param x The x position of the chunk
     * @param y The y position of the chunk
     */
    fun unloadChunk(x: Int, y: Int) {
        (getChunk(x, y) as RenderableChunk).texture?.dispose()
    }

    fun isChunkLoaded(x: Int, y: Int): Boolean {
        return (getChunk(x, y) as RenderableChunk).texture != null
    }

    /**
     * Return true if the chunk at the given position is loaded. The chunk is loaded if it exists and if it has a texture.
     *
     * @param x The x position of the chunk
     * @param y The y position of the chunk
     * @return True if the chunk is loaded
     **/
    override fun hasChunk(x: Int, y: Int): Boolean {
        return super.hasChunk(x, y) && (getChunk(x, y) as RenderableChunk).texture != null
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
        return (getChunk(x, y) as RenderableChunk).isDirty
    }
}