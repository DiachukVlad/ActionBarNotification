package com.diachuk.actionbarnotes.viewLayers.views.add

import android.os.Bundle
import android.view.View
import com.diachuk.actionbarnotes.data.entities.dto.DefaultImportance
import com.diachuk.actionbarnotes.data.entities.dto.HighImportance
import com.diachuk.actionbarnotes.data.entities.dto.Importance
import com.diachuk.actionbarnotes.data.entities.dto.LowImportance
import com.diachuk.actionbarnotes.databinding.ActivityAddBinding
import com.diachuk.actionbarnotes.helpers.Constants.EDIT_ID_EXTRA
import com.diachuk.actionbarnotes.helpers.connectToLiveData
import com.diachuk.actionbarnotes.viewLayers.views.base.BaseActivity
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import org.koin.android.viewmodel.ext.android.viewModel


class AddActivity : BaseActivity() {
    private val vm: AddViewModel by viewModel()

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getIntExtra(EDIT_ID_EXTRA, -1)
        vm.setId(id)

        vm.importance.observeForever(::updateImportance)
        vm.color.observeForever(::updateColor)
    }

    override fun provideBinding(): View {
        binding = ActivityAddBinding.inflate(layoutInflater)
        return binding.apply {
            titleEditText.connectToLiveData(vm.title)
            contentEditText.connectToLiveData(vm.content)
            lowRadio.setOnClickListener { vm.importance.value = LowImportance }
            defRadio.setOnClickListener { vm.importance.value = DefaultImportance }
            highRadio.setOnClickListener { vm.importance.value = HighImportance }

            color.setOnClickListener { showColorDialog() }

            doneBtn.setOnClickListener {
                vm.updateNote()
                startMainActivity()
            }
        }.root
    }

    private fun updateImportance(importance: Importance) {
        when (importance) {
            LowImportance -> binding.lowRadio.isChecked = true
            DefaultImportance -> binding.defRadio.isChecked = true
            HighImportance -> binding.highRadio.isChecked = true
        }
    }

    private fun updateColor(color: Int) {
        binding.color.setBackgroundColor(color)
    }

    private fun showColorDialog() {
        ColorPickerDialogBuilder
            .with(this)
            .setTitle("Choose color")
            .initialColor(vm.color.value!!)
            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
            .density(12)
            .setOnColorSelectedListener { selectedColor ->
                vm.color.value = selectedColor
            }
            .setPositiveButton("ok") { _, _, _ -> }
            .setNegativeButton("cancel") { _, _ -> }
            .build()
            .show()

    }


    private fun startMainActivity() {
        onBackPressed()
    }
}