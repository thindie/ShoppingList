package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()


          viewModel = ViewModelProvider(this)
            .get(MainViewModel::class.java)

        viewModel.shopList.observe(this) {
            adapter.listOfShopItem = it
        }
    }

    private fun setupRecyclerView(){
        val recyclerViewShoppingList = findViewById<RecyclerView>(R.id.rv_shop_list)
        adapter = ShopListAdapter()
        recyclerViewShoppingList.adapter = adapter
        recyclerViewShoppingList.recycledViewPool.setMaxRecycledViews(ShopListAdapter.ENABLED, ShopListAdapter.MAX_POOL_SIZE)
        recyclerViewShoppingList.recycledViewPool.setMaxRecycledViews(ShopListAdapter.DISABLED, ShopListAdapter.MAX_POOL_SIZE)

    }
}