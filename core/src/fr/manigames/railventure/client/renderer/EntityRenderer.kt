package fr.manigames.railventure.client.renderer

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Matrix4
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.core.EntityAssets
import fr.manigames.railventure.api.core.Metric
import fr.manigames.railventure.api.core.Render
import fr.manigames.railventure.api.graphics.renderer.Renderer
import fr.manigames.railventure.common.ecs.component.WorldSize

class EntityRenderer(
    private val asset: Assets,
    private val use3D: Boolean,
    private val camera: Camera
    ) : Renderer {

    private val batch = Render.spriteBatch
    private val spriteBatch = Render.spriteBatch
    private val textures = mutableMapOf<String, Texture?>()
    private val sprites = mutableMapOf<String, Sprite>()

    fun renderEntity(texture: String, worldX: Float, worldY: Float) {
        val tex = EntityAssets.getTexture(texture)
        batch.begin()
        tex?.let {
            batch.draw(tex, worldX * Metric.TILE_SIZE, worldY * Metric.TILE_SIZE)
        }
        batch.end()
    }

    fun renderEntity(texture: String, worldX: Float, worldY: Float, size: WorldSize) {
        val tex = EntityAssets.getTexture(texture)

        batch.begin()
        tex?.let {
            var x = worldX * Metric.TILE_SIZE + size.offsetX * Metric.TILE_SIZE
            var y = worldY * Metric.TILE_SIZE + size.offsetY * Metric.TILE_SIZE
            x -= if (size.ignoreWidth) 0f else ((size.width / 2) * Metric.TILE_SIZE)
            y -= if (size.ignoreHeight) 0f else ((size.height / 2) * Metric.TILE_SIZE)
            batch.draw(tex, x, y)
        }
        batch.end()
    }

    override fun setProjectionMatrix(projectionMatrix: Matrix4?) {
        batch.projectionMatrix = projectionMatrix
    }
}