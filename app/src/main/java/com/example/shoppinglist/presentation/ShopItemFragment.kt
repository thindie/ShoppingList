package com.example.shoppinglist.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {
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
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)
            .get(ShopItemActivityViewModel::class.java)
        initViews(view)
        settingActivityMode()
        setFieldsErrorListeners()


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

        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
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
        val id = shopItemID.toInt()
        viewModel.getShopItem(id)
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
        val arguments = requireArguments()
        if (!arguments.containsKey(SCREEN_MODE)) {
            throw
            RuntimeException("Params don't given")
        }
        val mode = arguments.getString(SCREEN_MODE)
        Log.d("SCREEN_MODE","VOT TAKOI $mode")


        if (mode != MODE_EDIT && mode != MODE_ADD) {
                throw RuntimeException("Unknown screen mode")
        }
        screenMode = mode.toString()

        if(mode == MODE_EDIT){
            if(!arguments.containsKey(SHOP_ITEM_ID)){
                throw RuntimeException("ID don't given")
            }
            shopItemID = arguments.getInt(SHOP_ITEM_ID)
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

        private const val SCREEN_MODE = "screen_mode"
        private const val SHOP_ITEM_ID = "shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val UNDEFINED_VAL = -1
        private const val UNDEFINED_STRING = "mode_unknown"


        fun createAddFragmentInstance() : ShopItemFragment{
            val fragment = ShopItemFragment().apply {
                arguments= Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }

            }
            return fragment
        }

        fun createEditFragmentInstance(editedShopItemID: Int) : ShopItemFragment{
            val fragment = ShopItemFragment().apply {
               arguments= Bundle().apply {
                   putString(SCREEN_MODE, MODE_EDIT)
                   putInt(SHOP_ITEM_ID, editedShopItemID)
                }
            }
             return fragment
        }
    }


}

