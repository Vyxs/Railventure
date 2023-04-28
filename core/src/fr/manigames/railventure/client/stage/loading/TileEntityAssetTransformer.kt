package fr.manigames.railventure.client.stage.loading

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import fr.manigames.railventure.api.core.Assets
import fr.manigames.railventure.api.core.EntityAssets
import fr.manigames.railventure.api.debug.Logger
import fr.manigames.railventure.api.gameobject.tileentity.RenderType
import fr.manigames.railventure.api.gameobject.tileentity.TileEntity
import fr.manigames.railventure.api.registry.TileEntityRegistry
import java.util.concurrent.atomic.AtomicReference

class TileEntityAssetTransformer(
    private val tileEntities: Collection<TileEntity>
) {

    private val transformProgression = AtomicReference(0f)
    private val isTransforming = AtomicReference(false)

    fun isTransforming(): Boolean = isTransforming.get()

    fun getTransformProgression(): Float = transformProgression.get()

    fun transform() {
        isTransforming.set(true)
        var current = 0
        tileEntities.forEachIndexed { index, entity ->
            if (entity.textureScale == 1f) {
                current++
                transformProgression.set(current.toFloat() / tileEntities.size)
                return@forEachIndexed
            }

            println("Transforming ${entity.key}")

            entity.texture.getEachDifferentTexture().forEach {
                val tex = Assets.instance.getTexture(it)
                val scaledTex = getScaledTexture(entity.textureScale, tex)

                if (scaledTex == null) {
                    Logger.error("Failed to get entity texture $it")
                    return@forEachIndexed
                }
                EntityAssets.addTexture(it, scaledTex)
            }
            current++
            transformProgression.set(current.toFloat() / tileEntities.size)
        }
        transformProgression.set(1f)
        isTransforming.set(false)
    }

    private fun getScaledTexture(scaleFactor: Float, texture: Texture?): Texture? {
        if (texture == null) return null
        val srcPixmap = getPixmap(texture)

        val width = (srcPixmap.width.toFloat() * scaleFactor).toInt()

        val widthRatio = width / srcPixmap.width.toFloat()
        val height = srcPixmap.height.toFloat() * widthRatio
        val pixmap = getPixmapAtCorrectScale(width, height.toInt(), texture)
        return Texture(pixmap)
    }

    private fun getPixmapAtCorrectScale(width: Int, height: Int, texture: Texture): Pixmap {
        val pixmap = Pixmap(width, height, Pixmap.Format.RGBA8888)
        val texturePixmap = getPixmap(texture)
        pixmap.filter = Pixmap.Filter.NearestNeighbour
        pixmap.drawPixmap(texturePixmap, 0, 0, texturePixmap.width, texturePixmap.height, 0, 0, pixmap.width, pixmap.height)
        return pixmap
    }

    private fun getPixmap(texture: Texture): Pixmap {
        if (!texture.textureData.isPrepared)
            texture.textureData.prepare()
        return texture.textureData.consumePixmap()
    }
}