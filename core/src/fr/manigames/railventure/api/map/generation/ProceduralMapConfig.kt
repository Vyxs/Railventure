package fr.manigames.railventure.api.map.generation

/**
 * The configuration of the procedural map
 */
data class ProceduralMapConfig(

    /**
     * The seed of the map. It will be used to generate the altitude, humidity and temperature and also any other random value.
     */
    val seed: Long,

    /**
     * The seed of the altitude generator.
     */
    val altitudeSeed: Long = seed,

    /**
     * The seed of the humidity generator.
     */
    val humiditySeed: Long = seed + 1,

    /**
     * The seed of the temperature generator.
     */
    val temperatureSeed: Long = seed + 2,

    /**
     * The scale factor of the map. For higher values, the map will be more zoomed ou otherwise it will look noisy.
     */
    val scale: Double = 50.0,

    /**
     * The default position of the generation when the method [ProceduralMap.generate] is called.
     * The first value is the x position and the second value is the y position.
     */
    val defaultGenerationPosition: Pair<Int, Int> = Pair(0, 0),

    /**
     * The default size of the generation when the method [ProceduralMap.generate] is called.
     */
    val defaultGenerationSize: Int = 100,

    /**
     * Whether the map should regenerate when the config is changed. If set to true, all the chunks will be marked
     * as dirty when the config is changed.
     */
    val regenerateOnConfigChange: Boolean = true
)