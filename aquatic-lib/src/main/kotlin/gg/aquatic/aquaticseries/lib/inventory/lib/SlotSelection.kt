package gg.aquatic.aquaticseries.lib.inventory.lib

import kotlin.math.ceil

class SlotSelection(val slots: MutableSet<Int>) {

    companion object {
        fun of(vararg slots: Int): SlotSelection {
            return SlotSelection(slots.toMutableSet())
        }
        fun of(collection: Collection<Int>): SlotSelection {
            return SlotSelection(collection.toMutableSet())
        }

        fun rangeOf(from: Int, to: Int): SlotSelection {
            return SlotSelection((from..to).toMutableSet())
        }

        fun rect(topLeft: Int, bottomRight: Int): SlotSelection {
            if (topLeft == bottomRight) return of(topLeft)
            if (topLeft > bottomRight) return rect(bottomRight, topLeft)
            if (topLeft % 9 > bottomRight % 9) {
                val d = topLeft % 9 - bottomRight % 9
                return rect(topLeft - d, bottomRight + d)
            }

            val set: MutableSet<Int> = HashSet()
            val d = bottomRight % 9 - topLeft % 9
            var rows = ceil((bottomRight / 9f - topLeft / 9f).toDouble()).toInt()
            if (d == 0) rows++
            for (i in topLeft..topLeft + d) for (j in 0 until rows) set.add(i + 9 * j)

            return SlotSelection(set)
        }
    }

    fun containsSlot(slot: Int): Boolean {
        return slots.contains(slot)
    }

    fun getSorted(): Set<Int> {
        return slots.toSortedSet()
    }

    fun and(vararg slots: Int): SlotSelection {
        this.slots.addAll(slots.toMutableSet())
        return this
    }

    fun andRange(from: Int, to: Int): SlotSelection {
        this.slots.addAll((from..to).toMutableSet())
        return this
    }

    fun andRect(topLeft: Int, bottomRight: Int): SlotSelection {
        this.slots.addAll(rect(topLeft, bottomRight).slots)
        return this
    }

}