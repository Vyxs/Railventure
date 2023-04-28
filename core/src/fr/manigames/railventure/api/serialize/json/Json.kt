package fr.manigames.railventure.api.serialize.json

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.FileTextureData
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import fr.manigames.railventure.api.gameobject.tileentity.OrientedTexture
import fr.manigames.railventure.api.gameobject.tileentity.RenderType
import fr.manigames.railventure.api.gameobject.tileentity.SeasonWeatherTexture
import fr.manigames.railventure.api.gameobject.tileentity.json.TileEntityData
import fr.manigames.railventure.api.map.biome.*
import fr.manigames.railventure.api.map.biome.json.BiomeData
import fr.manigames.railventure.api.map.biome.json.BiomeInstance

object Json {

    private val tilesType = object : TypeToken<List<TileWithProbability>>() {}.type
    private val tileEntitiesType = object : TypeToken<List<BiomeTileEntitiesConfig>>() {}.type

    /**
     * Require texture to have a [FileTextureData] as texture data to be serialized. So only texture loaded from file can be serialized.
     **/
    private val textureSerializer =
        JsonSerializer<Texture> { texture, _, _ ->
            try {
                JsonPrimitive((texture.textureData as FileTextureData).fileHandle.path())
            } catch (e: ClassCastException) {
                throw JsonParseException("Texture must have a FileTextureData as texture data to be serialized")
            }
        }

    private val textureDeserializer =
        JsonDeserializer { json, _, _ ->
            Texture(json.asString)
        }

    private val orientedTextureDeserializer =
        JsonDeserializer { json, typeOfT, context ->
            val jsonObject = json.asJsonObject

            val base = jsonObject["base"]?.asString ?: throw JsonParseException("OrientedTexture must have a base texture")
            val north = jsonObject["north"]?.asString ?: base
            val east = jsonObject["east"]?.asString ?: base
            val south = jsonObject["south"]?.asString ?: base
            val west = jsonObject["west"]?.asString ?: base

            OrientedTexture(base, north, east, south, west)
        }

    private val seasonWeatherTextureDeserializer =
        JsonDeserializer { json, typeOfT, context ->
            val jsonObject = json.asJsonObject

            val base = jsonObject["base"] ?: throw JsonParseException("SeasonWeatherTexture must have a base texture")
            val spring = jsonObject["spring"]?: base
            val summer = jsonObject["summer"]?: base
            val autumn = jsonObject["autumn"]?: base
            val winter = jsonObject["winter"]?: base

            val baseTexture = context.deserialize<OrientedTexture>(base, OrientedTexture::class.java)
            val springTexture = context.deserialize<OrientedTexture>(spring, OrientedTexture::class.java)
            val summerTexture = context.deserialize<OrientedTexture>(summer, OrientedTexture::class.java)
            val autumnTexture = context.deserialize<OrientedTexture>(autumn, OrientedTexture::class.java)
            val winterTexture = context.deserialize<OrientedTexture>(winter, OrientedTexture::class.java)

            SeasonWeatherTexture(baseTexture, springTexture, summerTexture, autumnTexture, winterTexture)
        }

    private val tileEntityDeserializer =
        JsonDeserializer { json, typeOfT, context ->
            val jsonObject = json.asJsonObject

            val key = jsonObject["key"]?.asString ?: throw JsonParseException("TileEntity must have a key")
            val name = jsonObject["name"]?.asString ?: throw JsonParseException("TileEntity must have a name")
            val texture = jsonObject["texture"] ?: throw JsonParseException("TileEntity must have a texture")

            val tex = context.deserialize<SeasonWeatherTexture>(texture, SeasonWeatherTexture::class.java)
            val renderType = jsonObject["renderType"]?.let { RenderType.valueOf(it.asString) } ?: RenderType.TILE
            val height = jsonObject["height"]?.asFloat ?: 1f
            val width = jsonObject["width"]?.asFloat ?: 1f
            val textureScale = jsonObject["textureScale"]?.asFloat ?: 1f
            val isOrientable = jsonObject["isOrientable"]?.asBoolean ?: false
            val isSolid = jsonObject["isSolid"]?.asBoolean ?: false
            val isHarvestable = jsonObject["isHarvestable"]?.asBoolean ?: false

            TileEntityData(
                key = key,
                name = name,
                texture = tex,
                renderType = renderType,
                height = height,
                width = width,
                textureScale = textureScale,
                isOrientable = isOrientable,
                isSolid = isSolid,
                isHarvestable = isHarvestable
            )
        }

    private val biomeDeserializer =
        JsonDeserializer { json, typeOfT, context ->
            val jsonObject = json.asJsonObject

            val color = jsonObject["color"]?.asInt ?: Biome.BIOME_DEFAULT_COLOR
            val type = jsonObject["type"]?.let { BiomeType.valueOf(it.asString) } ?: Biome.BIOME_DEFAULT_TYPE
            val gradient = jsonObject["gradient"]?.let { BiomeGradient.valueOf(it.asString) } ?: Biome.BIOME_DEFAULT_GRADIENT
            val tileEntities = jsonObject["tileEntities"]?.let { context.deserialize(it, tileEntitiesType) } ?: emptyList<BiomeTileEntitiesConfig>()

            BiomeData(
                key = jsonObject["key"].asString,
                name = jsonObject["name"].asString,
                temperature = jsonObject["temperature"].asInt,
                humidity = jsonObject["humidity"].asInt,
                altitude = jsonObject["altitude"].asInt,
                tiles = context.deserialize(jsonObject["tiles"], tilesType),
                color = color,
                type = type,
                gradient = gradient,
                tileEntities = tileEntities
            )
        }

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(Texture::class.java, textureSerializer)
        .registerTypeAdapter(Texture::class.java, textureDeserializer)
        .registerTypeAdapter(BiomeData::class.java, biomeDeserializer)
        .registerTypeAdapter(TileEntityData::class.java, tileEntityDeserializer)
        .registerTypeAdapter(OrientedTexture::class.java, orientedTextureDeserializer)
        .registerTypeAdapter(SeasonWeatherTexture::class.java, seasonWeatherTextureDeserializer)
        .setPrettyPrinting()
        .create()

    operator fun invoke(): Gson = gson
}