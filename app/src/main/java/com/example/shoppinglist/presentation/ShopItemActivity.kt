package com.example.shoppinglist.presentation;

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglist.R


class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnFinishListener {
    private var screenMode = UNDEFINED_STRING
    private var shopItemID = UNDEFINED_VAL


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        if (savedInstanceState == null) {
            settingActivityMode()
        }
    }

    private fun settingActivityMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> ShopItemFragment.createEditFragmentInstance(shopItemID)
            MODE_ADD -> ShopItemFragment.createAddFragmentInstance()
            else -> throw RuntimeException("Unknown screen mode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw
            RuntimeException("$intent : Param screen mode is absent")
        }


        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)

        if (mode != MODE_EDIT && mode != MODE_ADD && mode != null) {
            RuntimeException("$intent : UNKNOWN screen mode")
        }
        screenMode = mode!!
        if (screenMode == MODE_EDIT && !intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
            throw
            RuntimeException("$screenMode has not ID given")
        }


        if (screenMode == MODE_EDIT) {

            shopItemID = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, UNDEFINED_VAL)
        }

    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra mode"
        private const val EXTRA_SHOP_ITEM_ID = ""
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val UNDEFINED_VAL = -1
        private const val UNDEFINED_STRING = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, id: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, id)
            return intent
        }
    }

    override fun onFinishListener() {
        Toast.makeText(this,"SUCCESS", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
        this.onBackPressed()
    }
}
