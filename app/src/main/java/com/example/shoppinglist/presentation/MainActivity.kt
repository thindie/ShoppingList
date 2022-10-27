package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
          viewModel = ViewModelProvider(this)
            .get(MainViewModel::class.java)

        viewModel.shopList.observe(this) {
            shopListAdapter.listOfShopItem = it
        }
    }

    private fun setupRecyclerView() {
        shopListAdapter = ShopListAdapter()
        val recyclerViewShoppingList = findViewById<RecyclerView>(R.id.rv_shop_list)
        recyclerViewShoppingList.adapter = shopListAdapter
        with(recyclerViewShoppingList) {

            recycledViewPool
                .setMaxRecycledViews(ShopListAdapter.SHOPITEM_ENABLED, ShopListAdapter.MAX_POOL_SIZE)
            recycledViewPool
                .setMaxRecycledViews(ShopListAdapter.SHOPITEM_DISABLED, ShopListAdapter.MAX_POOL_SIZE)
        }
        shopListAdapter.onShopItemLongClickListener = {it -> viewModel.changeEnabledState(it)}
        shopListAdapter.onShopItemClickListener = {it -> Log.d("onShopItemClickListener", "${it.name} status")}
        setItemTouchHelper(recyclerViewShoppingList)
    }
    private fun setItemTouchHelper(recyclerView: RecyclerView){
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT)
        {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val viewHolderAdapterPosition = viewHolder.adapterPosition
                val deletedShopItem : ShopItem
                val adapter = recyclerView.adapter

                if(adapter is ShopListAdapter)
                    {
                        deletedShopItem = adapter.listOfShopItem[viewHolderAdapterPosition]
                        viewModel.removeShopItem(deletedShopItem)
                        adapter.notifyItemRemoved(viewHolderAdapterPosition)
                        Snackbar.make(viewHolder.itemView,"deleted ${deletedShopItem.name}", Snackbar.LENGTH_LONG).show()
                    }


            }
        }).attachToRecyclerView(recyclerView)
    }}