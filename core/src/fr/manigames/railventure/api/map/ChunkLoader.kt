package fr.manigames.railventure.api.map

import fr.manigames.railventure.client.map.RenderableChunk

interface ChunkLoader {

    fun loadChunk(chunk: RenderableChunk)
}