package fr.manigames.railventure.api.inventory

class Inventory(
    val data: InventoryData
) {
    val indices: IntRange
        get() = data.indices

    operator fun get(index: Int): ItemStack {
        return data[index]
    }

    operator fun set(index: Int, itemStack: ItemStack) {
        data[index] = itemStack
    }

    fun contains(itemStack: ItemStack): Boolean {
        return itemStack in data
    }

    fun iterator(): Iterator<ItemStack> {
        return data.iterator()
    }

    fun forEach(action: (ItemStack) -> Unit) {
        data.forEach(action)
    }

    fun forEachIndexed(action: (Int, ItemStack) -> Unit) {
        data.forEachIndexed(action)
    }

    fun copy(): Inventory {
        return Inventory(data.copy())
    }
}