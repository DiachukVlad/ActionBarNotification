package com.diachuk.actionbarnotes.views.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.diachuk.actionbarnotes.R
import com.diachuk.actionbarnotes.helpers.Constants.EDIT_ID_EXTRA
import com.diachuk.actionbarnotes.helpers.connectToLiveData
import kotlinx.android.synthetic.main.activity_add.*
import org.koin.android.viewmodel.ext.android.viewModel

class AddActivity : AppCompatActivity() {
    private val vm: AddViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val id = intent.getIntExtra(EDIT_ID_EXTRA, -1)
        vm.setId(id)

        title_edit_text.connectToLiveData(vm.title)

        done_btn.setOnClickListener {
            vm.updateNote()
            startMainActivity()
        }
    }

    fun startMainActivity() {
        onBackPressed()
    }
}