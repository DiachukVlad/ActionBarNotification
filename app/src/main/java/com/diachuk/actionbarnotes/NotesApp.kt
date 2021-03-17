package com.diachuk.actionbarnotes

import android.app.Application
import com.diachuk.actionbarnotes.di.appModule
import com.diachuk.actionbarnotes.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NotesApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@NotesApp)
            modules(appModule, dataModule)
        }
    }
}