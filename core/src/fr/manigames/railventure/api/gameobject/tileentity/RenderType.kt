package fr.manigames.railventure.api.gameobject.tileentity

/**
 * The render type of tile entity.
 */
enum class RenderType {
    /**
     * In 2d mode render the object as a tile. In 3d mode, the object will be rendered as a tile.
     */
    TILE,

    /**
     * In 2d mode render the object as a tile. In 3d mode, the object will be rendered as a tile width a thickness.
     */
    SLAB,

    /**
     * In 2d mode render the object as a foliage. In 3d mode, the object will be rendered as foliage.
     */
    FOLIAGE,

    /**
     * In 2d mode render the object as a foliage. In 3d mode, the object will be rendered using his 3d model.
     */
    RENDER_3D,

    /**
     * In both mode, the object will not be rendered.
     */
    NO_RENDER
}