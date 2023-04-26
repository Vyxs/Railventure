package fr.manigames.railventure.api.gameobject.item

abstract class Item {

    companion object {

        const val DEFAULT_ITEM_STACK_SIZE = 64
    }

    abstract val key: String
    abstract val name: String
    abstract val texture: String
    @Transient open val maxStackSize: Int = DEFAULT_ITEM_STACK_SIZE
    @Transient open val isStackable: Boolean = false
    @Transient open val isUsable: Boolean = false
    @Transient open val isPlaceable: Boolean = false
    @Transient open val isDroppable: Boolean = false
    @Transient open val isPickable: Boolean = false
    @Transient open val isEquippable: Boolean = false
    @Transient open val isConsumable: Boolean = false
    @Transient open val isCraftable: Boolean = false
    @Transient open val isRepairable: Boolean = false
    @Transient open val isEnchantable: Boolean = false
    @Transient open val isEnchanted: Boolean = false
    @Transient open val isDamaged: Boolean = false
    @Transient open val isDamagable: Boolean = false
}