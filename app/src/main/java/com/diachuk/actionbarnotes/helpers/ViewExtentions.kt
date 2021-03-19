package com.diachuk.actionbarnotes.helpers

import android.view.View
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.MutableLiveData

fun EditText.connectToLiveData(data: MutableLiveData<String>) {
    this.doOnTextChanged { text, _, _, _ ->
        if (data.value.toString() != text.toString())
            data.value = text.toString()
    }
    data.observeForever {
        if (it.toString() != text.toString())
            setText(it)
    }
}