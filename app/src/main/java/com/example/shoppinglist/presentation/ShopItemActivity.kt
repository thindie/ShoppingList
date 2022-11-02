package com.example.shoppinglist.presentation;

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.google.android.material.textfield.TextInputLayout


class ShopItemActivity : AppCompatActivity() {
    private lateinit var viewModel: ShopItemActivityViewModel
    private lateinit var textInput: TextInputLayout
    private lateinit var countInput: TextInputLayout
    private lateinit var textEdit: EditText
    private lateinit var countEdit: EditText
    private lateinit var button: Button
    private var screenMode = UNDEFINED_STRING
    private var shopItemID = UNDEFINED_VAL


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        viewModel = ViewModelProvider(this)
            .get(ShopItemActivityViewModel::class.java)
        initViews()
        setFieldsErrorListeners()
        settingActivityMode()
    }

    private fun settingActivityMode(){
        when (screenMode) {
            MODE_EDIT -> onModeEdit()
            MODE_ADD -> onModeAdd()
        }

        viewModel.shouldCloseScreen.observe(this){
            finish()
        }
    }


    private fun setFieldsErrorListeners(){
        textEdit.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName(true)
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        countEdit.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount(true)
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        viewModel.errorInputCount.observe(this){
            val message =  if(it){
                getString(R.string.error_input_count)
            } else {
                null
            }
            countInput.error = message
        }

        viewModel.errorInputName.observe(this){
            val message =  if(it){
                getString(R.string.error_input_name)
            } else {
                null
            }
            textInput.error = message
    }


    }

    private fun onModeEdit() {
        viewModel.getShopItem(shopItemID)
        viewModel.shopItem.observe(this) {
            textEdit.setText(it.name)
            countEdit.setText(it.count.toString())
            button.setOnClickListener {
                viewModel.editShopItem(
                    textEdit.text?.toString(),
                    countEdit.text?.toString()
                )

            }

        }
    }

    private fun onModeAdd() {
        button.setOnClickListener {
            viewModel.addShopItem(
                textEdit.text?.toString(),
                countEdit.text?.toString()
            )

        }


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

    private fun initViews() {
        textInput = findViewById(R.id.til_name)
        countInput = findViewById(R.id.til_count)
        textEdit = findViewById(R.id.et_name)
        countEdit = findViewById(R.id.et_count)
        button = findViewById(R.id.save_button)
    }
}
