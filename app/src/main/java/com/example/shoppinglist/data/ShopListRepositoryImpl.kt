package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ShopListRepository
import com.example.shoppinglist.domain.Shopitem

object ShopListRepositoryImpl : ShopListRepository {

    private val shopList = mutableListOf<Shopitem>()
    private var autoIncrementId = 0;

    override fun addShopItem(item: Shopitem) {
        if(autoIncrementId == Shopitem.UNDEFINED_VAL) item.id = autoIncrementId++
        shopList.add(item)
    }

    override fun editShopItem(item: Shopitem) {
        val oldShopitem = getShopItem(item.id)
        shopList.remove(oldShopitem)
        addShopItem(item)
    }

    override fun getShopItem(value: Int): Shopitem {
        return shopList.find {
            it.id == value } ?:
            throw RuntimeException("didn't find that thing with $value")
   }

    override fun getShopList(): List<Shopitem> {
        return shopList.toList()
    }

    override fun removeShopItem(shopitem: Shopitem) {
        shopList.remove(shopitem)
    }
}