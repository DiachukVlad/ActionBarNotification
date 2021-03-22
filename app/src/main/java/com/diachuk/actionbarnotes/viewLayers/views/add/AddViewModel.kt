package com.diachuk.actionbarnotes.viewLayers.views.add

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.diachuk.actionbarnotes.data.entities.dto.DefaultImportance
import com.diachuk.actionbarnotes.data.entities.dto.Importance
import com.diachuk.actionbarnotes.data.entities.dto.NoteDTO
import com.diachuk.actionbarnotes.data.repositories.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddViewModel(application: Application, private val repo: NoteRepository): AndroidViewModel(application) {
    private val notes: LiveData<List<NoteDTO>> = repo.getNotes()
    private var note: NoteDTO? = null

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val importance = MutableLiveData<Importance>(DEF_IMPORTANCE)
    val color = MutableLiveData<Int>(DEF_COLOR)
    val icon = MutableLiveData<String>()

    fun setId(id: Int) {
        if (id != -1) {
            viewModelScope.launch {
                note = repo.getNote(id)
                note?.let {
                    title.value = it.title
                    content.value = it.content
                    color.value = it.color
                    importance.value = it.importance
                }
            }
        }
    }

    fun updateNote() {
        val id = note?.id ?: -1
        println("id = ${id}")
        viewModelScope.launch(Dispatchers.IO) {
            if (id == -1) {
                repo.addNote(NoteDTO(
                    title.value.toString(),
                    content.value.toString(),
                    color.value!!,
                    importance.value!!
                ))
            } else {
                println(note)
                note?.let {
                    it.title = title.value.toString()
                    it.content = content.value.toString()
                    it.color = color.value!!
                    it.importance = importance.value!!

                    println(note)

                    repo.updateNote(it)
                }
            }
        }
    }

    companion object {
        const val DEF_COLOR = Color.CYAN
        val DEF_IMPORTANCE = DefaultImportance
    }
}