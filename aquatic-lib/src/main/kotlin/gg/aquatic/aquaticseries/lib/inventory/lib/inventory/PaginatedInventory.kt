package gg.aquatic.aquaticseries.lib.inventory.lib.inventory

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import gg.aquatic.aquaticseries.lib.inventory.lib.title.TitleHolder
import java.util.function.Consumer

class PaginatedInventory(
    title: TitleHolder,
    size: Int,
    type: InventoryType,
    player: Player,
    factory: Consumer<PaginatedInventory>?
) : PersonalizedInventory(
    title,
    size,
    type,
    player,
    null
) {

    val pages = ArrayList<InventoryPage>()
    var page = 0

    constructor(
        title: TitleHolder,
        type: InventoryType,
        player: Player,
        factory: Consumer<PaginatedInventory>?
    ): this(title,0,type,player,factory)
    constructor(
        title: TitleHolder,
        size: Int,
        player: Player,
        factory: Consumer<PaginatedInventory>?
    ): this(title,size,InventoryType.CHEST,player,factory)

    fun addInventoryPage(index: Int, page: InventoryPage) {
        pages.add(index,page)
    }

    fun addInventoryPage(page: InventoryPage) {
        pages.add(page)
    }

    fun createPage(titleHolder: TitleHolder): InventoryPage {
        return InventoryPage(this,titleHolder)
    }

    fun getInventoryPage(index: Int): InventoryPage? {
        if (index >= pages.size) return null
        return pages[index]
    }

    fun removeInventoryPage(index: Int) {
        pages.removeAt(index)
    }

    fun removeInventoryPage(inventoryPage: InventoryPage) {
        pages.remove(inventoryPage)
    }

    fun hasPreviousPage(): Boolean {
        return page>0
    }
    fun hasNextPage(): Boolean {
        return page < pages.size - 1
    }

    fun openNextPage(): Boolean {
        if (!hasNextPage()) return false
        setPage(page+1,true)
        return true
    }

    fun openPreviousPage(): Boolean {
        if (!hasPreviousPage()) {
            return false
        }
        setPage(page-1,true)
        return true
    }

    fun setPage(page: Int, update: Boolean) {
        this.page = page
        val invPage = pages[page]
        titleHolder = invPage.titleHolder
        componentHandler = invPage.componentHandler
        if (update) {
            redrawComponents()
            sendTitleUpdate()
        }
    }

    override fun open(player: Player, ignoreHistory: Boolean) {
        if (page >= pages.size) return
        super.open(player, ignoreHistory)
    }

    override fun open(ignoreHistory: Boolean) {
        if (page >= pages.size) return
        super.open(player, ignoreHistory)
    }



}