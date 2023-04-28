package fr.manigames.railventure.api.gameobject.tileentity

data class SeasonWeatherTexture(
    val base: OrientedTexture,
    val spring: OrientedTexture = base,
    val summer: OrientedTexture = base,
    val autumn: OrientedTexture = base,
    val winter: OrientedTexture = base
) {
    private fun getEachDifferentOrientedTexture(): List<OrientedTexture> {
        val list = mutableListOf(base)
        if (!list.contains(spring)) list.add(spring)
        if (!list.contains(summer)) list.add(summer)
        if (!list.contains(autumn)) list.add(autumn)
        if (!list.contains(winter)) list.add(winter)
        return list
    }

    fun getEachDifferentTexture(): List<String> {
        val list = mutableListOf<String>()
        val orientedTextures = getEachDifferentOrientedTexture()
        orientedTextures.forEach { orientedTexture ->
            list.addAll(orientedTexture.getEachDifferentTexture())
        }
        return list
    }
}