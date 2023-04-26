package fr.manigames.railventure.api.inventory

class InventoryData(
    val size: Int
) {
    private val data = Array(size) { ItemStack() }

    val indices: IntRange
        get() = data.indices

    operator fun get(index: Int): ItemStack {
        return data[index]
    }

    operator fun set(index: Int, itemStack: ItemStack) {
        data[index] = itemStack
    }

    operator fun contains(itemStack: ItemStack): Boolean {
        for (i in data.indices) {
            if (data[i].isStackableWith(itemStack)) {
                return true
            }
        }
        return false
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

    fun copy(): InventoryData {
        return InventoryData(size).also { newData ->
            for (i in data.indices) {
                newData[i] = data[i].copy()
            }
        }
    }
}