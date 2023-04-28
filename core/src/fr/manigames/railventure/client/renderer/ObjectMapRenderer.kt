package fr.manigames.railventure.client.renderer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy
import com.badlogic.gdx.graphics.g3d.decals.Decal
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.core.EntityAssets
import fr.manigames.railventure.api.core.Metric
import fr.manigames.railventure.api.core.Metric.MAP_CHUNK_SIZE
import fr.manigames.railventure.api.core.Metric.TILE_SIZE
import fr.manigames.railventure.api.core.Render
import fr.manigames.railventure.api.map.generation.ProceduralMap
import fr.manigames.railventure.api.registry.TileEntityRegistry

private data class Render2d(
    val texture: Texture,
    val x: Float,
    val y: Float
)

private data class Render3d(
    val decal: Decal
)

class ObjectMapRenderer(
    map: ProceduralMap,
    camera: Camera,
    private val use3D: Boolean,
    private val tileEntityRegistry: TileEntityRegistry
) : MapRenderer(map, camera) {

    private val batch: SpriteBatch = Render.spriteBatch
    private val decalBatch = DecalBatch(CameraGroupStrategy(camera))

    private val render2d: MutableList<Render2d> = mutableListOf()
    private val render3d: MutableList<Render3d> = mutableListOf()

    override fun render() {
        super.render()

        if (use3D)
            render3D()
        else
            render2D()
    }

    override fun onChunksChange() {
        if (use3D)
            prepare3D()
        else
            prepare2D()

    }

    private fun render2D() {
        batch.projectionMatrix = camera.combined
        batch.begin()
        render2d.forEach {
            batch.draw(it.texture, it.x, it.y)
        }
        batch.end()
    }

    private fun render3D() {
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST)
        decalBatch.initialize(render3d.size)
        render3d.forEach {
            decalBatch.add(it.decal)
        }
        decalBatch.flush()
        Gdx.gl.glDisable(GL20.GL_DEPTH_TEST)
    }

    private fun prepare2D() {
        render2d.clear()

        visibleChunks.values.forEach { chunk ->
            val chunkXOffset = chunk.x * MAP_CHUNK_SIZE
            val chunkYOffset = chunk.y * MAP_CHUNK_SIZE

            chunk.getTiles().forEachIndexed { y, column ->

                column.forEachIndexed { x, layer ->

                    val tileCode = layer[Metric.MAP_OBJECT_LAYER]
                    val tileKey = tileEntityRegistry.mapper[tileCode]
                    if (tileKey == "air") return@forEachIndexed

                    val tileEntity = tileEntityRegistry[tileKey] ?: return@forEachIndexed

                    var tex = EntityAssets.getTexture(tileEntity.texture.base.base)
                    if (tex == null) {
                        tex = Assets.instance.getTexture(tileEntity.texture.base.base) ?: return@forEachIndexed
                        val rx = (chunkXOffset + x) * TILE_SIZE
                        val ry = (chunkYOffset + y) * TILE_SIZE
                        render2d.add(Render2d(tex, rx, ry))
                    } else {
                        val rx = (chunkXOffset + x) * TILE_SIZE - ((tex.width) / 2) + TILE_SIZE / 2
                        val ry = (chunkYOffset + y) * TILE_SIZE + TILE_SIZE / 2
                        render2d.add(Render2d(tex, rx, ry))
                    }
                }
            }
        }
        render2d.sortByDescending { it.y }
    }

    private fun prepare3D() {
        render3d.clear()
        visibleChunks.values.forEach { chunk ->
            val chunkXOffset = chunk.x * MAP_CHUNK_SIZE
            val chunkYOffset = chunk.y * MAP_CHUNK_SIZE

            chunk.getTiles().forEachIndexed { y, column ->

                column.forEachIndexed { x, layer ->
                    val tileCode = layer[Metric.MAP_OBJECT_LAYER]
                    val tileKey = tileEntityRegistry.mapper[tileCode]
                    if (tileKey == "air") return@forEachIndexed

                    val tileEntity = tileEntityRegistry[tileKey] ?: return@forEachIndexed

                    var tex = EntityAssets.getTexture(tileEntity.texture.base.base)
                    if (tex == null) {
                        tex = Assets.instance.getTexture(tileEntity.texture.base.base) ?: return@forEachIndexed
                    }
                    val texRegion = TextureRegion(tex)
                    val decal = Decal.newDecal(texRegion, true)
                    val rx = (chunkXOffset + x) * TILE_SIZE + TILE_SIZE / 2
                    val ry = (chunkYOffset + y) * TILE_SIZE + TILE_SIZE / 2 + (rx % 5 + 1) * 1f // avoid z-fighting

                    decal.setDimensions(tex.width.toFloat(), tex.height.toFloat())
                    decal.setRotationY(90f)
                    decal.setRotationX(90f)
                    decal.setPosition(rx, ry, tex.height.toFloat() / 2)
                    render3d.add(Render3d(decal))
                }
            }
        }
    }
}