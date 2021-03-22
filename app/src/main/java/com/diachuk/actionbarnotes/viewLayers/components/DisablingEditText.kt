package com.diachuk.actionbarnotes.viewLayers.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class DisablingEditText(context: Context, attrs: AttributeSet): AppCompatEditText(context, attrs) {
    private lateinit var lastBg: Drawable
    var editable: Boolean = true
        set(value) {
            field = value
            isFocusable = value
            isFocusableInTouchMode = value
            isLongClickable = value
            if (value) {
                background = lastBg
            } else {
                lastBg = background
                background = null
            }
        }
}