package fr.manigames.railventure.api.inventory

import fr.manigames.railventure.api.gameobject.item.Item

class ItemStack(
    val item: Item? = null,
    var quantity: Int = 0
) {

    companion object {
        val EMPTY = ItemStack()
    }

    /**
     * Check if the item is not set
     *
     * @return true if the item is not set
     */
    fun isUnset(): Boolean {
        return item == null
    }

    /**
     * Check if the quantity is 0
     *
     * @return true if the quantity is 0
     */
    fun isEmpty(): Boolean {
        return quantity == 0
    }

    /**
     * Check if the quantity is equal to the max stack size of the item
     *
     * @return true if the quantity is equal to the max stack size
     */
    fun isFull(): Boolean {
        return quantity == item?.maxStackSize
    }

    /**
     * Check if the item is stackable with another item
     *
     * @param other the other item
     * @return true if the item is stackable with the other item
     */
    fun isStackableWith(other: ItemStack): Boolean {
        return item == other.item
    }

    /**
     * Check if the item can hold another item. It check if its stackable with the other item and if the new quantity
     * is not greater than the max stack size. If the item is not set, it will return true.
     *
     * @param other the other item
     * @return true if the item can hold the other item
     */
    fun canHold(other: ItemStack): Boolean {
        if (isUnset()) return true
        if (!isStackableWith(other)) return false
        return quantity + other.quantity <= item!!.maxStackSize
    }

    /**
     * Add stack to this stack. If the quantity overflow the max stack size, or the item is not stackable with this stack,
     * it will return false.
     *
     * @param other the other itemStack
     * @return true if the itemStack has been added
     */
    fun stackWith(other: ItemStack): Boolean {
        if (!canHold(other)) return false
        quantity += other.quantity
        return true
    }

    /**
     * Copy the itemStack
     *
     * @return the copy of the itemStack
     **/
    fun copy(): ItemStack {
        return ItemStack(item, quantity)
    }
}