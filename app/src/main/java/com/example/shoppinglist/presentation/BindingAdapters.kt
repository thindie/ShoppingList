package com.example.shoppinglist.presentation

import androidx.databinding.BindingAdapter
import com.example.shoppinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

interface OnFloatingButtonStartLandScape {
    fun onClickLandScape()
}

interface OnFloatingButtonStartPortrait {
    fun onClickPortrait()
}

interface OnFinishListener {
    fun onFinishListener()
}

interface OnSaveClickModeAdd {
    fun saveAdd()
}


@BindingAdapter("bu_addShopItem_landScape")
fun setFloatingButton(button: FloatingActionButton, buttonClick: OnFloatingButtonStartLandScape) {
    button.setOnClickListener { buttonClick.onClickLandScape() }
}


@BindingAdapter("bu_addShopItem_portrait")
fun setFloatingButtonPortrait(
    button: FloatingActionButton,
    buttonStartPortrait: OnFloatingButtonStartPortrait
) {
    button.setOnClickListener {
        buttonStartPortrait.onClickPortrait()
    }

}

@BindingAdapter("tf_setText")
fun setTextField(textInputEditText: TextInputEditText, string: String){
    textInputEditText.setText(string)
}
@BindingAdapter("tf_setTextInt")
fun setTextFieldInt(textInputEditText: TextInputEditText, int: Int){
    textInputEditText.setText(int.toString())
}

@BindingAdapter("tf_errorInputName")
fun bindErrorInputName(textInputLayout: TextInputLayout, error: Boolean){
    val message = if (error ) {
        textInputLayout.context.getString(R.string.error_input_name)
    } else {
        null
    }
    textInputLayout.error = message
}

@BindingAdapter("tf_errorInputCount")
fun bindErrorInputCount(textInputLayout: TextInputLayout, error: Boolean){
    val message = if (error ) {
        textInputLayout.context.getString(R.string.error_input_count)
    } else {
        null
    }
    textInputLayout.error = message
}