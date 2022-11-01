package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter
    : ListAdapter<ShopItem, ShopListAdapter.ShoppingListViewHolder>
    (ShopItemDiffCallback()) {


    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    class ShoppingListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val shopItemName = view.findViewById<TextView>(R.id.tv_name)
        val shopItemCount = view.findViewById<TextView>(R.id.tv_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : ShoppingListViewHolder {
        if (viewType == SHOPITEM_ENABLED) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shop_enabled, parent, false)
            return ShoppingListViewHolder(view)
        }

        if (viewType == SHOPITEM_DISABLED) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shop_disabled, parent, false)
            return ShoppingListViewHolder(view)
        } else throw RuntimeException("unknown viewType $viewType")
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        val shopItem = getItem(position)
        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            false
        }
        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
        holder.shopItemName.text = shopItem.name
        holder.shopItemCount.text = shopItem.count.toString()
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)
        return if (shopItem.enabled) 3748139 else 91246139
    }

    companion object {
        const val MAX_POOL_SIZE = 20
        const val SHOPITEM_ENABLED = 3748139
        const val SHOPITEM_DISABLED = 91246139
    }

}