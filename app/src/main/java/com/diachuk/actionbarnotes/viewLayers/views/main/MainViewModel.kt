package com.diachuk.actionbarnotes.viewLayers.views.main

import android.app.Application
import android.app.NotificationManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.diachuk.actionbarnotes.NotesApp
import com.diachuk.actionbarnotes.data.entities.dto.NoteDTO
import com.diachuk.actionbarnotes.data.repositories.NoteRepository
import com.diachuk.actionbarnotes.helpers.NotifChanelsCreator
import com.diachuk.actionbarnotes.helpers.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.get

class MainViewModel(
    application: Application,
    private val repo: NoteRepository,
    private val chanelCreator: NotifChanelsCreator
) : AndroidViewModel(application) {
    val notes: LiveData<List<NoteDTO>> = repo.getNotes()
    private val prefs = get(Preferences::class.java)

    fun onCreate() {
        CoroutineScope(Dispatchers.Default).launch {
            if (prefs.chanelsCreated.not()) {
                chanelCreator.createChanels(
                    getApplication<NotesApp>().getSystemService(
                        AppCompatActivity.NOTIFICATION_SERVICE
                    ) as NotificationManager
                )
                prefs.chanelsCreated = true
            }
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteNote(id)
        }
    }
}