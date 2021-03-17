package com.diachuk.actionbarnotes.data.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.diachuk.actionbarnotes.data.entities.Note
import com.diachuk.actionbarnotes.data.dao.NoteDao

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}