package fr.manigames.railventure.client.renderer

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.core.Metric
import fr.manigames.railventure.api.graphics.renderer.Renderer

class TileRenderer(private val asset: Assets) : Renderer {

    private val batch = SpriteBatch()

    fun renderTile(type: TileType, x: Int, y: Int) {
        asset.getTexture(type.assetKey)?.let { texture ->
            batch.begin()
            batch.draw(texture, x.toFloat(), y.toFloat(), Metric.TILE_SIZE, Metric.TILE_SIZE)
            batch.end()
        }
    }

    fun renderTexture(texture: String, worldX: Float, worldY: Float) {
        asset.getTexture(texture)?.let { tex ->
            batch.begin()
            batch.draw(tex, worldX * Metric.TILE_SIZE, worldY * Metric.TILE_SIZE, Metric.TILE_SIZE, Metric.TILE_SIZE)
            batch.end()
        }
    }

    override fun setProjectionMatrix(projectionMatrix: Matrix4?) {
        batch.projectionMatrix = projectionMatrix
    }

    override fun dispose() {
        batch.dispose()
    }


}