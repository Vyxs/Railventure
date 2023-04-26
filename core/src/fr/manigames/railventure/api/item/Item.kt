package fr.manigames.railventure.api.item

abstract class Item {

    companion object {

        const val DEFAULT_ITEM_STACK_SIZE = 64

        private var idCounter = 0

        private fun makeId(): Int {
            return idCounter++
        }
    }

    val id: Int = makeId()
    abstract val key: String
    abstract val name: String
    abstract val texture: String
    open val maxStackSize: Int = DEFAULT_ITEM_STACK_SIZE
    open val isStackable: Boolean = false
    open val isUsable: Boolean = false
    open val isPlaceable: Boolean = false
    open val isDroppable: Boolean = false
    open val isPickable: Boolean = false
    open val isEquippable: Boolean = false
    open val isConsumable: Boolean = false
    open val isCraftable: Boolean = false
    open val isRepairable: Boolean = false
    open val isEnchantable: Boolean = false
    open val isEnchanted: Boolean = false
    open val isDamaged: Boolean = false
    open val isDamagable: Boolean = false
}