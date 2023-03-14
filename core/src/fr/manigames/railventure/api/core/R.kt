package fr.manigames.railventure.api.core

object R {

    fun assetLoadingFunction(am: com.badlogic.gdx.assets.AssetManager) {
        am.load(Texture.RAIL_V, com.badlogic.gdx.graphics.Texture::class.java)
        am.load(Texture.RAIL_H, com.badlogic.gdx.graphics.Texture::class.java)
        am.load(Texture.RAIL_X, com.badlogic.gdx.graphics.Texture::class.java)
        am.load(Texture.RAIL_TOP_LEFT, com.badlogic.gdx.graphics.Texture::class.java)
        am.load(Texture.RAIL_TOP_RIGHT, com.badlogic.gdx.graphics.Texture::class.java)
        am.load(Texture.RAIL_BOT_LEFT, com.badlogic.gdx.graphics.Texture::class.java)
        am.load(Texture.RAIL_BOT_RIGHT, com.badlogic.gdx.graphics.Texture::class.java)
        am.load(Texture.RAIL_T_BOT, com.badlogic.gdx.graphics.Texture::class.java)
        am.load(Texture.RAIL_T_TOP, com.badlogic.gdx.graphics.Texture::class.java)
        am.load(Texture.RAIL_T_LEFT, com.badlogic.gdx.graphics.Texture::class.java)
        am.load(Texture.RAIL_T_RIGHT, com.badlogic.gdx.graphics.Texture::class.java)
        am.load(Texture.WAGON, com.badlogic.gdx.graphics.Texture::class.java)

        am.load(Texture.GRASS, com.badlogic.gdx.graphics.Texture::class.java)
    }

    object Texture {
        const val RAIL_V: String = "texture/rail/rail-v.png"
        const val RAIL_H: String = "texture/rail/rail-h.png"
        const val RAIL_X: String = "texture/rail/rail-x.png"
        const val RAIL_TOP_LEFT: String = "texture/rail/rail-top-left.png"
        const val RAIL_TOP_RIGHT: String = "texture/rail/rail-top-right.png"
        const val RAIL_BOT_LEFT: String = "texture/rail/rail-bot-left.png"
        const val RAIL_BOT_RIGHT: String = "texture/rail/rail-bot-right.png"
        const val RAIL_T_BOT: String = "texture/rail/rail-t-bot.png"
        const val RAIL_T_TOP: String = "texture/rail/rail-t-top.png"
        const val RAIL_T_LEFT: String = "texture/rail/rail-t-left.png"
        const val RAIL_T_RIGHT: String = "texture/rail/rail-t-right.png"
        const val WAGON: String = "texture/wagon/wagon.png"

        const val GRASS: String = "texture/tile/grass.png"
    }

}