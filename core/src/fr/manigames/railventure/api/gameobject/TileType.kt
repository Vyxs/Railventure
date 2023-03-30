package fr.manigames.railventure.api.gameobject

import fr.manigames.railventure.generated.R

enum class TileType(val texture: R.Resource, val code: Int) {
    AIR(R.Texture.AIR, 0),
    GRASS (R.Texture.GRASS, 1),
    DIRT(R.Texture.DIRT, 2),
    SAND(R.Texture.SAND, 3),
    WATER(R.Texture.WATER, 4),
    RAIL_TOP_LEFT(R.Texture.RAIL_TOP_LEFT, 5),
    RAIL_TOP_RIGHT(R.Texture.RAIL_TOP_RIGHT, 6),
    RAIL_BOT_LEFT(R.Texture.RAIL_BOT_LEFT, 7),
    RAIL_BOT_RIGHT(R.Texture.RAIL_BOT_RIGHT, 8),
    RAIL_H(R.Texture.RAIL_H, 9),
    RAIL_V(R.Texture.RAIL_V, 10),
    RAIL_X(R.Texture.RAIL_X, 11),
    RAIL_T_TOP(R.Texture.RAIL_T_TOP, 12),
    RAIL_T_BOT(R.Texture.RAIL_T_BOT, 13),
    RAIL_T_LEFT(R.Texture.RAIL_T_LEFT, 14),
    RAIL_T_RIGHT(R.Texture.RAIL_T_RIGHT, 15);

    companion object {
        fun fromCode(code: Int): TileType = values().firstOrNull { it.code == code } ?: AIR
    }
}
