package com.diachuk.actionbarnotes.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.diachuk.actionbarnotes.data.dao.NoteDao
import com.diachuk.actionbarnotes.data.entities.dto.NoteDTO
import com.diachuk.actionbarnotes.data.entities.dto.toNoteDTO

class NoteRepository(private val dao: NoteDao) {
    fun getNotes(): LiveData<List<NoteDTO>> {
        return Transformations.map(dao.getNotes()) { it.map { it.toNoteDTO() } }
    }

    suspend fun addNote(noteDTO: NoteDTO) {
        dao.addNote(noteDTO.toNote())
    }

    suspend fun updateNote(noteDTO: NoteDTO) {
        dao.updateNote(noteDTO.toNote())
    }

    suspend fun getNote(id: Int): NoteDTO? {
        return dao.getNote(id)?.toNoteDTO()
    }

    suspend fun deleteNote(id: Int) {
        dao.deleteNote(id)
    }
}