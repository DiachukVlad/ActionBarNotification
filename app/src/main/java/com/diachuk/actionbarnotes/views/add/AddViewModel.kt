package com.diachuk.actionbarnotes.views.add

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.diachuk.actionbarnotes.data.dao.NoteDao
import com.diachuk.actionbarnotes.data.entities.Note
import com.diachuk.actionbarnotes.data.entities.dto.NoteDTO
import com.diachuk.actionbarnotes.data.repositories.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddViewModel(application: Application, private val repo: NoteRepository): AndroidViewModel(application) {
    private val notes: LiveData<List<NoteDTO>> = repo.getNotes()
    val title = MutableLiveData<String>()
    private var note: NoteDTO? = null

    fun setId(id: Int) {
        if (id != -1) {
            viewModelScope.launch {
                note = repo.getNote(id)
                note?.let {
                    title.value = it.title
                }
            }
        }
    }

    fun updateNote() {
        val id = note?.id ?: -1
        viewModelScope.launch(Dispatchers.IO) {
            if (id == -1) {
                repo.addNote(NoteDTO(title.value.toString()))
            } else {
                val note = notes.value?.firstOrNull { it.id == id }
                note?.let {
                    note.title = title.value.toString()
                    repo.updateNote(note)
                }
            }
        }
    }
}