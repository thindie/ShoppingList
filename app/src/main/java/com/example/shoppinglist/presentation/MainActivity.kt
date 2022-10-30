package com.example.shoppinglist.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    //on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
          viewModel = ViewModelProvider(this)
            .get(MainViewModel::class.java)

        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
        }
        val buttonAddItem = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        buttonAddItem.setOnClickListener {
            val intent = Intent(
                this, ShopItemActivity::class.java
            )
            intent.putExtra("extra_mode", " mode_add")
            startActivity(intent)
        }
    }
    //SETTING UP RECYCLER VIEW with CARDVIEWs inside
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
        setterOnLongClickListener()
        setOnTapOnShopItem()
        setItemTouchHelper(recyclerViewShoppingList)
    }
    //SETTING ONCLICK (for long and short clicks) LISTENERS
    private fun setterOnLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = { it -> viewModel.changeEnabledState(it) }

    }
    private fun setOnTapOnShopItem(){
        shopListAdapter.onShopItemClickListener =
            {  val intent = Intent(
                this, ShopItemActivity::class.java
            )
                intent.putExtra("extra_mode", " mode_edit")
                startActivity(intent) }
    }
    //SWIPE TO DELETE
    private fun setItemTouchHelper(recyclerView: RecyclerView){
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT
                or ItemTouchHelper.LEFT)
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
                val deletedShopItem = shopListAdapter.
                currentList[viewHolderAdapterPosition]

                viewModel.removeShopItem(deletedShopItem)
                shopListAdapter.notifyItemRemoved(viewHolderAdapterPosition)
                   //     Snackbar.make(viewHolder.itemView,"DELETED ${deletedShopItem.name}",
                   //        Snackbar.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recyclerView)
    }}