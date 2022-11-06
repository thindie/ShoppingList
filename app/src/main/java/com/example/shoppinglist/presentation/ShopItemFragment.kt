package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment(
    private val screenMode: String,
    private var shopItemID : Int
) : Fragment() {
    private lateinit var viewModel: ShopItemActivityViewModel
    private lateinit var textInput: TextInputLayout
    private lateinit var countInput: TextInputLayout
    private lateinit var textEdit: EditText
    private lateinit var countEdit: EditText
    private lateinit var button: Button
   // private var screenMode = UNDEFINED_STRING
   // private var shopItemID = UNDEFINED_VAL


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //setContentView(R.layout.activity_shop_item)
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        viewModel = ViewModelProvider(this)
            .get(ShopItemActivityViewModel::class.java)
        initViews(view)
        //setFieldsErrorListeners()
        // settingActivityMode()

    }


    private fun settingActivityMode() {
        when (screenMode) {
            MODE_EDIT -> onModeEdit()
            MODE_ADD -> onModeAdd()
        }

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
             activity?.onBackPressed()
        }
    }


    private fun setFieldsErrorListeners() {
        textEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName(false)
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        countEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount(false)
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            textInput.error = message
        }

        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            countInput.error = message
        }


    }

    private fun onModeEdit() {
        viewModel.getShopItem(shopItemID)
        viewModel.shopItem.observe(viewLifecycleOwner) {
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

    private fun parseParams() {
        if (screenMode != MODE_ADD && screenMode != MODE_EDIT) {
            throw
            RuntimeException("Unknown screen mode")
        }

        if (screenMode == MODE_EDIT && shopItemID == UNDEFINED_VAL) {
            throw
            RuntimeException("$screenMode has not ID given")
        }
    }

    private fun initViews(view: View) {
        with(view) {
            textInput = findViewById(R.id.til_name)
            countInput = findViewById(R.id.til_count)
            textEdit = findViewById(R.id.et_name)
            countEdit = findViewById(R.id.et_count)
            button = findViewById(R.id.save_button)
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

        fun newInstanceAddItem() : ShopItemFragment{
            return ShopItemFragment(MODE_ADD, UNDEFINED_VAL)
        }
        fun newInstanceEditItem(itemID: Int) : ShopItemFragment{
            return ShopItemFragment(MODE_ADD, itemID)
        }
    }


}

