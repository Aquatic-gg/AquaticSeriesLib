package gg.aquatic.aquaticseries.lib.chance

class ChanceUtils {

    companion object {

        fun <T : IChance> getRandomItem(items: Collection<T>): T? {
            val chances = items.map { it.chance }.toMutableList()
            val randomIndex = getRandomChanceIndex(chances)
            if (randomIndex < 0) return null
            return items.elementAtOrNull(randomIndex)
        }

        fun getRandomChanceIndex(chances: Collection<Double>): Int {
            if (chances.isEmpty()) return -1
            if (getTotalPercentage(chances) <= 0) {
                return -1
            }
            var totalWeight = 0.0
            for (chance in chances) {
                totalWeight += chance
            }

            var random: Double = Math.random() * totalWeight
            for ((i, chance) in chances.withIndex()) {
                random -= chance
                if (random <= 0.0) {
                    return i
                }
            }
            return -1
        }

        private fun getTotalPercentage(chances: Collection<Double>): Double {
            return chances.sum()
        }
    }

}