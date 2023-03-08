package fr.manigames.railventure.api.gameobject

import fr.manigames.railventure.api.core.R


enum class TileType(val assetKey: String) {
    GRASS (""),
    DIRT(""),
    SAND(""),
    WATER(""),
    RAIL_TOP_LEFT(R.Texture.RAIL_TOP_LEFT),
    RAIL_TOP_RIGHT(R.Texture.RAIL_TOP_RIGHT),
    RAIL_BOT_LEFT(R.Texture.RAIL_BOT_LEFT),
    RAIL_BOT_RIGHT(R.Texture.RAIL_BOT_RIGHT),
    RAIL_H(R.Texture.RAIL_H),
    RAIL_V(R.Texture.RAIL_V),
    RAIL_X(R.Texture.RAIL_X),
    RAIL_T_TOP(R.Texture.RAIL_T_TOP),
    RAIL_T_BOT(R.Texture.RAIL_T_BOT),
    RAIL_T_LEFT(R.Texture.RAIL_T_LEFT),
    RAIL_T_RIGHT(R.Texture.RAIL_T_RIGHT)
}
