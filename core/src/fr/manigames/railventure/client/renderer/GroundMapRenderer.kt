package fr.manigames.railventure.client.renderer

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import fr.manigames.railventure.api.core.Metric
import fr.manigames.railventure.api.core.Render
import fr.manigames.railventure.client.map.RenderableMap

class GroundMapRenderer(
    map: RenderableMap,
    camera: Camera
) : MapRenderer(map, camera) {

    private val batch: SpriteBatch = Render.spriteBatch
    private val chunkSizeInPx = (Metric.TILE_SIZE * Metric.MAP_CHUNK_SIZE).toInt()

    override fun render() {
        super.render()
        batch.projectionMatrix = camera.combined
        batch.begin()
        visibleChunks.forEach { (_, chunk) ->
            chunk.texture?.let {
                batch.draw(it, chunk.x.toFloat() * chunkSizeInPx, chunk.y.toFloat() * chunkSizeInPx, chunkSizeInPx.toFloat(), chunkSizeInPx.toFloat())
            }
        }
        batch.end()
    }
}