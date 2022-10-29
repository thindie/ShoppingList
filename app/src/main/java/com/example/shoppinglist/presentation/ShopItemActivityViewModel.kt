package com.example.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.*

class ShopItemActivityViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl
    private val getShopItemByIdUseCase = GetShopItemByIdUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    fun addShopItem(name: String?, count: String?) {

        val nameToAdd = parseName(name)
        val countToAdd = parseCount(count)
        val isDataOk = validateInput(nameToAdd,countToAdd)
        if(isDataOk) {
            val shopItemToAdd = ShopItem(nameToAdd, countToAdd, true, 0)
            //-> fill fields -> save ->
            addShopItemUseCase.addShopItem(shopItemToAdd)
        }
    }

    fun getShopItem(shopItemID: Int){
        //-> fill fields ->

        getShopItemByIdUseCase.getShopItem(shopItemID)
    }

    fun editShopItem(shopItem: ShopItem){
        ////-> fill fields -> edit fields ->
        val name = shopItem.name
        val count = shopItem.count.toString() // to be corrected
        //TODO listeners for input fields

        val nameToAdd = parseName(name)
        val countToAdd = parseCount(count)
        val isDataOk = validateInput(nameToAdd,countToAdd)
        if(isDataOk) {
            val shopItemToAdd = ShopItem(nameToAdd, countToAdd, shopItem.enabled, shopItem.id)
            editShopItemUseCase.editShopItem(shopItemToAdd)
        }
    }

    private fun validateInput(name: String, count: Int) : Boolean{
        val result = true
        if(name.isBlank()){
            return false
            //todo mistake alert
        }
        if(count <= 0){
            return false
            //todo mistake alert
        }
        return result
    }
    private fun parseName(name: String?) : String{
        return name?.trim() ?:""
    }
    private fun parseCount(count: String?) : Int{
        val parsedString = parseName(count)
        return try {
            parsedString.toInt()
        }catch (e: NumberFormatException){
            0
        }
    }

}