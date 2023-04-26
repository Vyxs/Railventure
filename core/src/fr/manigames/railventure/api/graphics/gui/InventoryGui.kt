package fr.manigames.railventure.api.graphics.gui

import com.badlogic.gdx.graphics.Texture
import fr.manigames.railventure.api.serialize.json.Json

data class Slot(val x: Int, val y: Int, val width: Int, val height: Int)

class InventoryGui private constructor() {
    val id: Int = 0
    var texture: Texture? = null
    val width: Int = 0
    val height: Int = 0
    var slots: Array<Slot> = arrayOf()

    companion object {

        fun fromJsonModel(json: String): InventoryGui = Json().fromJson(json, InventoryGui::class.java)
    }
}