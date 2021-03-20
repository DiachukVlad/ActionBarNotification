package com.diachuk.actionbarnotes.data.entities.dto

import android.graphics.Color
import com.diachuk.actionbarnotes.data.entities.Note
import com.diachuk.actionbarnotes.helpers.Constants

data class NoteDTO(
    var title: String = "",
    var content: String = "",
    var color: Int = Color.BLACK,
    var importance: Importance = LowImportance,
    var image: String = "",
    var small: Boolean = false
) {
    var id: Int = 0

    fun toNote(): Note {
        return Note(title, content, color, importance.toString(), image, small).also { it.id = id }
    }
}

fun Note.toNoteDTO(): NoteDTO {
    return NoteDTO(title, content, color, importance.toImportance(), image, small).also { it.id = id }
}

sealed class Importance {
    override fun toString(): String{
        return when(this) {
            LowImportance -> Constants.NOTIFICATION_CHANNEL_LOW_ID
            DefaultImportance -> Constants.NOTIFICATION_CHANNEL_DEF_ID
            HighImportance -> Constants.NOTIFICATION_CHANNEL_HIGH_ID
        }
    }
}

object LowImportance : Importance()
object DefaultImportance : Importance()
object HighImportance : Importance()

fun String.toImportance(): Importance {
    return when(this) {
        Constants.NOTIFICATION_CHANNEL_LOW_ID -> LowImportance
        Constants.NOTIFICATION_CHANNEL_DEF_ID -> DefaultImportance
        Constants.NOTIFICATION_CHANNEL_HIGH_ID -> HighImportance
        else -> DefaultImportance
    }
}