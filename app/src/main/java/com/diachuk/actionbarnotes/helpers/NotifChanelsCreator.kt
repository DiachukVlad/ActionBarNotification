package com.diachuk.actionbarnotes.helpers

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.diachuk.actionbarnotes.R
import com.diachuk.actionbarnotes.extentions.getString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NotifChanelsCreator() {
    fun createChanels(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        createChanel(notificationManager, getString(R.string.channel_low_name), getString(R.string.channel_low_description), NotificationManager.IMPORTANCE_LOW,Constants.NOTIFICATION_CHANNEL_LOW_ID)
        createChanel(notificationManager, getString(R.string.channel_def_name), getString(R.string.channel_def_description), NotificationManager.IMPORTANCE_DEFAULT,Constants.NOTIFICATION_CHANNEL_DEF_ID)
        createChanel(notificationManager, getString(R.string.channel_high_name), getString(R.string.channel_high_description), NotificationManager.IMPORTANCE_HIGH,Constants.NOTIFICATION_CHANNEL_HIGH_ID)
    }

    @SuppressLint("NewApi")
    private fun createChanel(notificationManager: NotificationManager, name: String, descriptionText: String, importance: Int, notifId: String) {
        val mChannel = NotificationChannel(notifId, name, importance)

        mChannel.description = descriptionText
        mChannel.enableVibration(false)
        mChannel.setSound(null, null)

        notificationManager.createNotificationChannel(mChannel)
    }
}