package fr.manigames.railventure.common.ecs.component

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import fr.manigames.railventure.api.gameobject.TileType

class TileRender(
    var type: TileType,
    var x: Int,
    var y: Int
) : Component<TileRender> {
    override fun type() = TileRender
    companion object : ComponentType<TileRender>()
}