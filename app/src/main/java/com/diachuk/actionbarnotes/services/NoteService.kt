package com.diachuk.actionbarnotes.services

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import android.os.IBinder
import com.diachuk.actionbarnotes.R
import com.diachuk.actionbarnotes.data.entities.dto.NoteDTO
import com.diachuk.actionbarnotes.data.repositories.NoteRepository
import com.diachuk.actionbarnotes.views.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get


class NoteService : Service() {
    private val notificationManager by lazy {
        this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val repo = get<NoteRepository>()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action?.let {
            when (it) {
                "REMOVE" -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        repo.deleteNote(intent.getIntExtra("id", -1))
                    }
                }
                else -> {}
            }
        }

        val notes = repo.getNotes()

        notes.observeForever {
            notificationManager.cancelAll()
            if (it.isEmpty()) {
                stopForeground(true)
            } else {
                showNotifications(it)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun showNotifications(notes: List<NoteDTO>) {
        notes.forEachIndexed { i, note ->
            val notification = buildNotification(note)

            if (i == 0) {
                startForeground(note.id + 1, notification)
            } else {
                notificationManager.notify(note.id + 1, notification)
            }
        }
    }

    private fun buildNotification(note: NoteDTO): Notification {
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, note.importance.toString())
        } else {
            Notification.Builder(this)
        }

        val removeIntent = getRemoveIntent(note.id)

        builder
            .setContentTitle(note.title)
            .setSmallIcon(R.drawable.ic_add)
            .setContentText(note.content)
            .setColor(note.color)
            .setOngoing(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            builder.addAction(
                Notification.Action.Builder(
                    Icon.createWithResource(
                        this,
                        R.drawable.ic_done
                    ), "Remove", removeIntent
                ).build()
            )
        } else {
            builder.addAction(R.drawable.ic_done, "Remove", removeIntent)
        }

        return builder.build()
    }

    private fun getRemoveIntent(id: Int): PendingIntent {
        return PendingIntent.getService(this, id*100, Intent(this, NoteService::class.java).apply {
            action = "REMOVE"
            putExtra("id", id)
        }, 0)
    }

}