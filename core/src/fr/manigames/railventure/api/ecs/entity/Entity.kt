package fr.manigames.railventure.api.ecs.entity

@JvmInline
value class Entity(private val id: Long) {
    override fun toString(): String = "Entity($id)"
}