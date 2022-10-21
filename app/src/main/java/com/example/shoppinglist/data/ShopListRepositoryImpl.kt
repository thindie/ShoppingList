package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ShopListRepository
import com.example.shoppinglist.domain.ShopItem

object ShopListRepositoryImpl : ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0;

    override fun addShopItem(item: ShopItem) {
        if(autoIncrementId == ShopItem.UNDEFINED_VAL) item.id = autoIncrementId++
        shopList.add(item)
    }

    override fun editShopItem(item: ShopItem) {
        val oldShopitem = getShopItem(item.id)
        shopList.remove(oldShopitem)
        addShopItem(item)
    }

    override fun getShopItem(value: Int): ShopItem {
        return shopList.find {
            it.id == value } ?:
            throw RuntimeException("didn't find that thing with $value")
   }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }

    override fun removeShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }
}