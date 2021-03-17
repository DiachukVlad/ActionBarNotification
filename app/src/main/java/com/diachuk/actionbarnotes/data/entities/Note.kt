package com.diachuk.actionbarnotes.data.entities

import android.graphics.Color
import androidx.room.*
import com.diachuk.actionbarnotes.data.entities.dto.DefaultImportance

@Entity(tableName = "notes_table")
data class Note(
    var title: String = "",
    var content: String = "",
    var color: Int = Color.BLACK,
    var importance: String = DefaultImportance.toString(),
    var image: String = "",
    var small: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
