package fr.manigames.railventure.common.component

import fr.manigames.railventure.api.gameobject.TileType
import fr.manigames.railventure.api.component.Component
import fr.manigames.railventure.api.component.ComponentType

data class TileRenderComponent(
    val type: TileType,
    val x: Int,
    val y: Int
) : Component(ComponentType.TILE_RENDERABLE)