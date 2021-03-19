package com.diachuk.actionbarnotes.views.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.diachuk.actionbarnotes.R
import com.diachuk.actionbarnotes.data.dao.NoteDao
import com.diachuk.actionbarnotes.data.entities.dto.NoteDTO
import com.diachuk.actionbarnotes.databinding.ActivityMainBinding
import com.diachuk.actionbarnotes.helpers.Constants.EDIT_ID_EXTRA
import com.diachuk.actionbarnotes.services.NoteService
import com.diachuk.actionbarnotes.views.add.AddActivity
import com.diachuk.actionbarnotes.views.base.BaseActivity
import com.diachuk.actionbarnotes.views.main.components.NotesAdapter
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity() {

    private val vm: MainViewModel by viewModel()
    private val notesAdapter by lazy { get<NotesAdapter>() }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notesAdapter.onEditClick = ::onEditClick
        notesAdapter.onDeleteClick = ::onDeleteClick

        vm.notes.observeForever {
            updateNotes(it)
        }

        startService(Intent(this, NoteService::class.java))

        vm.onCreate()
    }

    override fun provideBinding(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.apply {
            addBtn.setOnClickListener { startAddActivity() }
            notesView.adapter = notesAdapter
        }.root
    }

    private fun startAddActivity() {
        startActivity(Intent(this, AddActivity::class.java))
    }

    private fun updateNotes(notes: List<NoteDTO>) {
        notesAdapter.notes = notes
        notesAdapter.notifyDataSetChanged()
    }

    private fun onEditClick(id: Int) {
        val intent = Intent(this, AddActivity::class.java)
        intent.putExtra(EDIT_ID_EXTRA, id)
        startActivity(intent)
    }

    private fun onDeleteClick(id: Int) {
        vm.deleteNote(id)
    }
}