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
import com.diachuk.actionbarnotes.data.databases.NoteDatabase
import com.diachuk.actionbarnotes.data.repositories.NoteRepository
import com.diachuk.actionbarnotes.views.main.MainActivity
import org.koin.android.ext.android.get


class NoteService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val dao = get<NoteRepository>()
        val notes = dao.getNotes()
        notes.observeForever {
            val notificationManager =
                this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll()

            it.forEachIndexed { i, note ->
                val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Notification.Builder(this, note.importance.toString())
                } else {
                    Notification.Builder(this)
                }

                val pI = PendingIntent.getActivity(this, 111, Intent(this, MainActivity::class.java), 0)

                builder
                    .setContentTitle(note.title)
                    .setSmallIcon(R.drawable.ic_add)
                    .setContentText(note.content)
                    .setOngoing(true)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    builder.addAction(Notification.Action.Builder(Icon.createWithResource(this, R.drawable.ic_done), "Remove", pI).build())
                } else {
                    builder.addAction(R.drawable.ic_done, "Remove", pI)
                }

                val notification = builder.build()

                if (i == 0) {
                    startForeground(note.id + 1, notification)
                } else {
                    notificationManager.notify(note.id + 1, notification)
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }
}