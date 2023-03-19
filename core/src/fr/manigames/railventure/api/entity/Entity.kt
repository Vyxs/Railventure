package fr.manigames.railventure.api.entity

@JvmInline
value class Entity(private val id: Long) {
    override fun toString(): String = "Entity($id)"
}