package fr.manigames.railventure

import fr.manigames.railventure.api.core.R


enum class TileType(val assetKey: String) {
    GRASS (""),
    DIRT(""),
    SAND(""),
    WATER(""),
    RAIL_LEFT(R.Texture.RAIL_TOP_LEFT),
    RAIL_RIGHT(R.Texture.RAIL_TOP_RIGHT),
    RAIL_TOP(""),
    RAIL_BOTTOM(""),
    RAIL_T(""),
    RAIL_H(R.Texture.RAIL_H),
    RAIL_V(R.Texture.RAIL_V);
}
