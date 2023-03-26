package fr.manigames.railventure.api.component

/**
 * A component is a part of an entity. It must be immutable.
 **/
abstract class Component(val componentType: ComponentType)

/**
 * The type of component, used to avoid using reflection
 **/
enum class ComponentType {
    TILE_RENDERABLE,
    WORLD_POSITION,
    HUD_POSITION,
    TEXT,
    TEXTURE,
    PLAYER,
    MOVEABLE
}