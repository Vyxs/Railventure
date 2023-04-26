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
		TALL_GRASS("texture/tile/tall_grass.png"),
		FLOWER_GRASS("texture/tile/flower_grass.png"),
		DIRT("texture/tile/dirt.png"),
		SAND("texture/tile/sand.png"),
		CLEAR_SAND("texture/tile/clear_sand.png"),
		WATER("texture/tile/water.png"),
		DEEP_WATER("texture/tile/deep_water.png"),
		STONE("texture/tile/stone.png"),
		SNOWY_STONE("texture/tile/snowy_stone.png"),
		SNOW("texture/tile/snow.png"),
		MOSSY_STONE("texture/tile/mossy_stone.png"),
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
		TILE_0_0("texture/debug/tile_0_0.png"),
		TILE_0_15("texture/debug/tile_0_15.png"),
		TILE_15_0("texture/debug/tile_15_0.png"),
		TILE_15_15("texture/debug/tile_15_15.png"),
		FOLIAGE_AUTUMN_TALL_BUSH_1("texture/foliage/autumn/tall_bush_1.png"),
		FOLIAGE_AUTUMN_TALL_BUSH_2("texture/foliage/autumn/tall_bush_2.png"),
		FOLIAGE_AUTUMN_TALL_BUSH_3("texture/foliage/autumn/tall_bush_3.png"),
		FOLIAGE_AUTUMN_TALL_BUSH_4("texture/foliage/autumn/tall_bush_4.png"),
		FOLIAGE_AUTUMN_TALL_BUSH_5("texture/foliage/autumn/tall_bush_5.png"),
		FOLIAGE_AUTUMN_TALL_BUSH_6("texture/foliage/autumn/tall_bush_6.png"),
		FOLIAGE_AUTUMN_TREE_1("texture/foliage/autumn/tree_1.png"),
		FOLIAGE_AUTUMN_TREE_2("texture/foliage/autumn/tree_2.png"),
		FOLIAGE_AUTUMN_TREE_3("texture/foliage/autumn/tree_3.png"),
		FOLIAGE_AUTUMN_TREE_4("texture/foliage/autumn/tree_4.png"),
		FOLIAGE_AUTUMN_TREE_5("texture/foliage/autumn/tree_5.png"),
		FOLIAGE_AUTUMN_TREE_6("texture/foliage/autumn/tree_6.png"),
		FOLIAGE_AUTUMN_ROCK_1("texture/foliage/autumn/rock_1.png"),
		FOLIAGE_AUTUMN_ROCK_2("texture/foliage/autumn/rock_2.png"),
		FOLIAGE_AUTUMN_ROCK_3("texture/foliage/autumn/rock_3.png"),
		FOLIAGE_SPRING_CONIFER_1("texture/foliage/spring/conifer_1.png"),
		FOLIAGE_SPRING_CONIFER_2("texture/foliage/spring/conifer_2.png"),
		FOLIAGE_SPRING_CONIFER_3("texture/foliage/spring/conifer_3.png"),
		FOLIAGE_SPRING_ROCK_1("texture/foliage/spring/rock_1.png"),
		FOLIAGE_SPRING_ROCK_2("texture/foliage/spring/rock_2.png"),
		FOLIAGE_SPRING_ROCK_3("texture/foliage/spring/rock_3.png"),
		FOLIAGE_SPRING_TRUNK_1("texture/foliage/spring/trunk_1.png"),
		FOLIAGE_SPRING_TRUNK_2("texture/foliage/spring/trunk_2.png"),
		FOLIAGE_SPRING_TRUNK_3("texture/foliage/spring/trunk_3.png"),
		FOLIAGE_SPRING_TRUNK_4("texture/foliage/spring/trunk_4.png"),
		FOLIAGE_SUMMER_TALL_BUSH_1("texture/foliage/summer/tall_bush_1.png"),
		FOLIAGE_SUMMER_TALL_BUSH_2("texture/foliage/summer/tall_bush_2.png"),
		FOLIAGE_SUMMER_TALL_BUSH_3("texture/foliage/summer/tall_bush_3.png"),
		FOLIAGE_SUMMER_TALL_BUSH_4("texture/foliage/summer/tall_bush_4.png"),
		FOLIAGE_SUMMER_TALL_BUSH_5("texture/foliage/summer/tall_bush_5.png"),
		FOLIAGE_SUMMER_TALL_BUSH_6("texture/foliage/summer/tall_bush_6.png"),
		FOLIAGE_SUMMER_BUSH_1("texture/foliage/summer/bush_1.png"),
		FOLIAGE_SUMMER_BUSH_2("texture/foliage/summer/bush_2.png"),
		FOLIAGE_SUMMER_BUSH_3("texture/foliage/summer/bush_3.png"),
		FOLIAGE_SUMMER_BUSH_4("texture/foliage/summer/bush_4.png"),
		FOLIAGE_SUMMER_BUSH_5("texture/foliage/summer/bush_5.png"),
		FOLIAGE_SUMMER_BUSH_6("texture/foliage/summer/bush_6.png"),
		FOLIAGE_SUMMER_BUSH_7("texture/foliage/summer/bush_7.png"),
		FOLIAGE_SUMMER_BUSH_8("texture/foliage/summer/bush_8.png"),
		FOLIAGE_SUMMER_CONIFER_1("texture/foliage/summer/conifer_1.png"),
		FOLIAGE_SUMMER_CONIFER_2("texture/foliage/summer/conifer_2.png"),
		FOLIAGE_SUMMER_CONIFER_3("texture/foliage/summer/conifer_3.png"),
		FOLIAGE_SUMMER_FLOWER_1("texture/foliage/summer/flower_1.png"),
		FOLIAGE_SUMMER_FLOWER_2("texture/foliage/summer/flower_2.png"),
		FOLIAGE_SUMMER_FLOWER_3("texture/foliage/summer/flower_3.png"),
		FOLIAGE_SUMMER_FLOWER_4("texture/foliage/summer/flower_4.png"),
		FOLIAGE_SUMMER_TREE_1("texture/foliage/summer/tree_1.png"),
		FOLIAGE_SUMMER_TREE_2("texture/foliage/summer/tree_2.png"),
		FOLIAGE_SUMMER_TREE_3("texture/foliage/summer/tree_3.png"),
		FOLIAGE_SUMMER_TREE_4("texture/foliage/summer/tree_4.png"),
		FOLIAGE_SUMMER_TREE_5("texture/foliage/summer/tree_5.png"),
		FOLIAGE_SUMMER_TREE_6("texture/foliage/summer/tree_6.png"),
		FOLIAGE_WINTER_CONIFER_1("texture/foliage/winter/conifer_1.png"),
		FOLIAGE_WINTER_CONIFER_2("texture/foliage/winter/conifer_2.png"),
		FOLIAGE_WINTER_CONIFER_3("texture/foliage/winter/conifer_3.png"),
		FOLIAGE_WINTER_ROCK_1("texture/foliage/winter/rock_1.png"),
		FOLIAGE_WINTER_ROCK_2("texture/foliage/winter/rock_2.png"),
		FOLIAGE_WINTER_ROCK_3("texture/foliage/winter/rock_3.png"),
		FOLIAGE_WINTER_TRUNK_1("texture/foliage/winter/trunk_1.png"),
		FOLIAGE_WINTER_TRUNK_2("texture/foliage/winter/trunk_2.png"),
		FOLIAGE_WINTER_TRUNK_3("texture/foliage/winter/trunk_3.png"),
		FOLIAGE_WINTER_TRUNK_4("texture/foliage/winter/trunk_4.png"),
	}

	fun loadingConsumer(am: com.badlogic.gdx.assets.AssetManager) {
		Texture.values().filter { it.path.isNotBlank() }.forEach { am.load(it.path, com.badlogic.gdx.graphics.Texture::class.java) }
	}

}