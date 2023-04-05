package fr.manigames.railventure.api.ecs.entity

object EntityBuilder {

    private var id = 0L

    fun make(): Entity = Entity(id++)
}