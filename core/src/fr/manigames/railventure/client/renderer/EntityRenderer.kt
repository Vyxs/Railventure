package fr.manigames.railventure.client.renderer

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.PolygonRegion
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy
import com.badlogic.gdx.graphics.g3d.decals.Decal
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch
import com.badlogic.gdx.math.EarClippingTriangulator
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

    private val decalBatch = DecalBatch(CameraGroupStrategy(camera))

    fun renderEntity(texture: String, worldX: Float, worldY: Float) {
        val tex = EntityAssets.getTexture(texture)
        batch.begin()
        tex?.let {
            batch.draw(tex, worldX * Metric.TILE_SIZE, worldY * Metric.TILE_SIZE)
        }
        batch.end()
    }

    fun renderEntity(texture: String, worldX: Float, worldY: Float, size: WorldSize) {
        if (use3D)
            render3dEntity(texture, worldX, worldY, size)
        else
            render2dEntity(texture, worldX, worldY, size)
    }

    private fun render2dEntity(texture: String, worldX: Float, worldY: Float, size: WorldSize) {
        val tex = EntityAssets.getTexture(texture) ?: return
        var x = worldX * Metric.TILE_SIZE + size.offsetX * Metric.TILE_SIZE
        var y = worldY * Metric.TILE_SIZE + size.offsetY * Metric.TILE_SIZE
        x -= if (size.ignoreWidth) 0f else ((size.width / 2) * Metric.TILE_SIZE)
        y -= if (size.ignoreHeight) 0f else ((size.height / 2) * Metric.TILE_SIZE)
        batch.begin()
        batch.draw(tex, x, y)
        batch.end()
    }

    private fun render3dEntity(texture: String, worldX: Float, worldY: Float, size: WorldSize) {
        val tex = EntityAssets.getTexture(texture) ?: return
        val texRegion = TextureRegion(tex)
        val decal = Decal.newDecal(texRegion, true)
        decal.setDimensions(tex.width.toFloat(), tex.height.toFloat())
        decal.setRotationY(90f)
        decal.setRotationX(90f)

        var x = worldX * Metric.TILE_SIZE + size.offsetX * Metric.TILE_SIZE
        var y = worldY * Metric.TILE_SIZE + size.offsetY * Metric.TILE_SIZE
        x -= if (size.ignoreWidth) 0f else ((size.width / 2) * Metric.TILE_SIZE)
        y -= if (size.ignoreHeight) 0f else ((size.height / 2) * Metric.TILE_SIZE)
        decal.setPosition(x, y, tex.height.toFloat() / 2)
        decalBatch.add(decal)
    }

    fun flush() {
        decalBatch.flush()
    }

    override fun setProjectionMatrix(projectionMatrix: Matrix4?) {
        batch.projectionMatrix = projectionMatrix
    }
}