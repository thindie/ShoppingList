package com.example.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.*

class ShopItemActivityViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl
    private val getShopItemByIdUseCase = GetShopItemByIdUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val _errorInputName = MutableLiveData<Boolean>()
    private val _errorInputCount = MutableLiveData<Boolean>()

    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    /*this is KOTLIN LIKE hi-cast из-за того,
    чтобы соблюдались критерии о чистой архитектуре -
    необходимо чтобы mutablelivedata была
    доступна только изнутри этого класса
     для этого ее кастанули до ЛайвДаты (ее родителя)
     где геттер приватный ---- строчки 15-19 */

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen


    fun addShopItem(name: String?, count: String?) {
        val nameToAdd = parseName(name)
        val countToAdd = parseCount(count)
        val isDataOk = validateInput(nameToAdd, countToAdd)
        if (isDataOk) {
            val shopItemToAdd =
                ShopItem(nameToAdd, countToAdd, true, 0)
            addShopItemUseCase.addShopItem(shopItemToAdd)
            finishScreen()
        }
    }

    fun getShopItem(shopItemID: Int) {
        //-> fill fields ->
        val item = getShopItemByIdUseCase
            .getShopItem(shopItemID)
        _shopItem.postValue(item)
    }

    fun editShopItem(shopItem: ShopItem) {
        val name = shopItem.name
        val count = shopItem.count.toString()
        val nameToAdd = parseName(name)
        val countToAdd = parseCount(count)
        val isDataOk = validateInput(nameToAdd, countToAdd)
        if (isDataOk) {
            _shopItem.value?.let {
                val shopItemToAdd = it
                    .copy(name = nameToAdd, count = countToAdd)
                editShopItemUseCase.editShopItem(shopItemToAdd)
                finishScreen()
            }
        }
    }

    public fun resetErrorInputName(toChange: Boolean) {
        _errorInputName.postValue(false)
    }

    public fun resetErrorInputCount(toChange: Boolean) {
        _errorInputCount.postValue(false)
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parseCount(count: String?): Int {
        val parsedString = parseName(count)
        return try {
            parsedString.toInt()
        } catch (e: NumberFormatException) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        val result = true
        if (name.isBlank()) {
            _errorInputName.postValue(true)
            return false
            //todo mistake alert
        }
        if (count <= 0) {
            _errorInputCount.postValue(true)
            return false
            //todo mistake alert
        }
        return result
    }

    private fun finishScreen() {
        _shouldCloseScreen.postValue(Unit)
    }
}