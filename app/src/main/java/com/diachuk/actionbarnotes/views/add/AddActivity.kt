package com.diachuk.actionbarnotes.views.add

import android.os.Bundle
import android.view.View
import com.diachuk.actionbarnotes.data.entities.dto.DefaultImportance
import com.diachuk.actionbarnotes.data.entities.dto.HighImportance
import com.diachuk.actionbarnotes.data.entities.dto.LowImportance
import com.diachuk.actionbarnotes.databinding.ActivityAddBinding
import com.diachuk.actionbarnotes.helpers.Constants.EDIT_ID_EXTRA
import com.diachuk.actionbarnotes.helpers.connectToLiveData
import com.diachuk.actionbarnotes.views.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel

class AddActivity : BaseActivity() {
    private val vm: AddViewModel by viewModel()

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getIntExtra(EDIT_ID_EXTRA, -1)
        vm.setId(id)

        vm.importance.observeForever {
            when(it) {
                LowImportance -> binding.lowRadio.isSelected = true
                DefaultImportance -> binding.defRadio.isSelected = true
                HighImportance -> binding.highRadio.isSelected = true
            }
        }
    }

    override fun provideBinding(): View {
        binding = ActivityAddBinding.inflate(layoutInflater)
        return binding.apply {
            titleEditText.connectToLiveData(vm.title)
            contentEditText.connectToLiveData(vm.content)
            lowRadio.setOnClickListener { vm.importance.value = LowImportance }
            defRadio.setOnClickListener { vm.importance.value = DefaultImportance }
            highRadio.setOnClickListener { vm.importance.value = HighImportance }

            doneBtn.setOnClickListener {
                vm.updateNote()
                startMainActivity()
            }
        }.root
    }

    fun startMainActivity() {
        onBackPressed()
    }
}