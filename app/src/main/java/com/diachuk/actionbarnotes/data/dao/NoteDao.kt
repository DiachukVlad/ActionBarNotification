package com.diachuk.actionbarnotes.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.diachuk.actionbarnotes.data.entities.Note
import kotlinx.coroutines.selects.select

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("select * from notes_table order by id asc")
    fun getNotes(): LiveData<List<Note>>

    @Query("select * from notes_table where id == :id limit 1" )
    suspend fun getNote(id: Int): Note?

    @Query("delete from notes_table where id == :id")
    suspend fun deleteNote(id: Int)
}