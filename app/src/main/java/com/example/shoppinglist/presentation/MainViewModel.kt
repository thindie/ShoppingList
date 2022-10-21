package com.example.shoppinglist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopListUseCase
import com.example.shoppinglist.domain.RemoveShopItemUseCase
import com.example.shoppinglist.domain.ShopItem


class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = MutableLiveData<List<ShopItem>>()


    fun getShopList(){
        val list = getShopListUseCase.getShopList()
        shopList.value = list
    }

    fun removeShopItem(shopItem: ShopItem){
        removeShopItemUseCase.removeShopItem(shopItem)
        getShopList()
    }

    fun changeEnabledState(shopItem: ShopItem){
        val stateChanged = ShopItem(shopItem.name, shopItem.count, !shopItem.enabled, shopItem.id)
        //val stateChanged = shopItem.copy(enabled != shopItem.enabled)
        editShopItemUseCase.editShopItem(stateChanged)
        getShopList()
    }
}