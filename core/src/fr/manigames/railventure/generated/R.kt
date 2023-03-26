package fr.manigames.railventure.generated

/*
* Auto-generated file, do not edit. Use datagen/generateRessourcesFromAssets task to update this file.
*/
object R {

	interface Resource {
		val path: String
	}

	enum class Texture(
		override val path: String
	) : Resource {
		AIR(""),
		GRASS("texture/tile/grass.png"),
		DIRT("texture/tile/dirt.png"),
		SAND("texture/tile/sand.png"),
		WATER("texture/tile/water.png"),
		RAIL_V("texture/rail/rail-v.png"),
		RAIL_H("texture/rail/rail-h.png"),
		RAIL_X("texture/rail/rail-x.png"),
		RAIL_TOP_LEFT("texture/rail/rail-top-left.png"),
		RAIL_TOP_RIGHT("texture/rail/rail-top-right.png"),
		RAIL_BOT_LEFT("texture/rail/rail-bot-left.png"),
		RAIL_BOT_RIGHT("texture/rail/rail-bot-right.png"),
		RAIL_T_BOT("texture/rail/rail-t-bot.png"),
		RAIL_T_TOP("texture/rail/rail-t-top.png"),
		RAIL_T_LEFT("texture/rail/rail-t-left.png"),
		RAIL_T_RIGHT("texture/rail/rail-t-right.png"),
		WAGON("texture/wagon/wagon.png"),
	}

	fun loadingConsumer(am: com.badlogic.gdx.assets.AssetManager) {
		Texture.values().filter { it.path.isNotBlank() }.forEach { am.load(it.path, com.badlogic.gdx.graphics.Texture::class.java) }
	}

}