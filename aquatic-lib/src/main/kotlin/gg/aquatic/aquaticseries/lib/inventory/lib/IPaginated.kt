package gg.aquatic.aquaticseries.lib.inventory.lib

interface IPaginated {

    fun getPage(): Int
    fun hasNextPage(): Boolean
    fun hasPreviousPage(): Boolean
    fun getTotalPages(): Int

}