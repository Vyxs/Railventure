package fr.manigames.railventure.api.serialize.json

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.FileTextureData
import com.google.gson.*

object Json {

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

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(Texture::class.java, textureSerializer)
        .registerTypeAdapter(Texture::class.java, textureDeserializer)
        .setPrettyPrinting()
        .create()

    operator fun invoke(): Gson = gson
}