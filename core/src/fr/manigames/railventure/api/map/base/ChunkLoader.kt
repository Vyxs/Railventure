package fr.manigames.railventure.api.map.base

import fr.manigames.railventure.client.map.RenderableChunk

interface ChunkLoader {

    fun loadChunk(chunk: RenderableChunk)
}