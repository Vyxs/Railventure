package fr.manigames.railventure.test

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.PixmapIO
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.client.map.ChunkLoader
import fr.manigames.railventure.client.map.RenderableChunk

class DebugChunkLoader(
    assets: Assets
) : ChunkLoader(assets) {

    override fun generateChunkTexture(chunk: RenderableChunk, assets: Assets) {
        val pixmap = makePixmap(chunk, assets)
        val file = Gdx.files.local("save/chunk_${chunk.y}_${chunk.x}.png")
        PixmapIO.writePNG(file, pixmap)
        applyPixmapToChunk(chunk, pixmap)
    }
}