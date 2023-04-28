package fr.manigames.railventure.api.gameobject.tileentity

data class OrientedTexture(
    val base: String,
    val north: String = base,
    val east: String = base,
    val south: String = base,
    val west: String = base
) {
    fun getEachDifferentTexture(): List<String> {
        val list = mutableListOf(base)
        if (!list.contains(north)) list.add(north)
        if (!list.contains(east)) list.add(east)
        if (!list.contains(south)) list.add(south)
        if (!list.contains(west)) list.add(west)
        return list
    }
}