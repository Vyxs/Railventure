package fr.manigames.railventure.client.renderer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Matrix4
import fr.manigames.railventure.api.core.Render
import fr.manigames.railventure.api.graphics.gui.InventoryGui

class GuiRenderer {

    private val batch = Render.spriteBatch
    private val inventory = InventoryGui.fromJsonModel(Gdx.files.internal("model/inventory/inventory.json").readString())
    private val resetMatrix = Matrix4().setToOrtho2D(0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    fun render() {
        val x = Gdx.graphics.width / 2f - inventory.width / 2f
        val y = Gdx.graphics.height / 2f - inventory.height / 2f

        batch.projectionMatrix = resetMatrix
        batch.begin()
        batch.draw(inventory.texture, x, y, inventory.width.toFloat(), inventory.height.toFloat())
        batch.end()
    }
}