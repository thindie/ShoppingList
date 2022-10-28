package com.example.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.AddShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopListUseCase
import com.example.shoppinglist.domain.ShopItem

class ShopItemActivityViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl
    private val getShopItemByIdUseCase = GetShopListUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    fun addShopItem(){
        val shopItemToAdd: ShopItem
        val name: String
        val count: Int
        //-> fill fields -> save ->
        // addShopItemUseCase.addShopItem(shopItemToAdd)
    }

    fun getShopItem(shopItemID: Int){

    }

    fun editShopItem(shopItem: ShopItem){
        // -> getItem from  -> fill fields -> editing fields -> save ->
        editShopItemUseCase.editShopItem(shopItem)
    }

}