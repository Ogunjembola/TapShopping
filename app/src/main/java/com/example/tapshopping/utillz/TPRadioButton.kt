package com.example.tapshopping.utillz

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton

class TPRadioButton (context: Context, attr: AttributeSet) : AppCompatRadioButton(context, attr) {
    init {
        applyFont()
    }


    private fun applyFont() {
        val typeface: Typeface =
            Typeface.createFromAsset(context.assets, "Montserrat-Bold.ttf")
        setTypeface(typeface)

    }
}