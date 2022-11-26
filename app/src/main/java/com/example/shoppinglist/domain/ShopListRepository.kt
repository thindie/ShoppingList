package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    suspend fun addShopItem(item: ShopItem)
    suspend fun editShopItem(item: ShopItem)
    suspend fun getShopItem(value: Int) : ShopItem
    fun getShopList() : LiveData<List<ShopItem>>
    suspend fun removeShopItem(shopItem: ShopItem)
}