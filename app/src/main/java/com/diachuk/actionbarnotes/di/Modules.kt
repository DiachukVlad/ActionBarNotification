package com.diachuk.actionbarnotes.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.diachuk.actionbarnotes.NotesApp
import com.diachuk.actionbarnotes.data.dao.NoteDao
import com.diachuk.actionbarnotes.data.databases.NoteDatabase
import com.diachuk.actionbarnotes.data.repositories.NoteRepository
import com.diachuk.actionbarnotes.helpers.NotifChanelsCreator
import com.diachuk.actionbarnotes.helpers.Preferences
import com.diachuk.actionbarnotes.views.add.AddViewModel
import com.diachuk.actionbarnotes.views.main.MainViewModel
import com.diachuk.actionbarnotes.views.main.components.NotesAdapter
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { MainViewModel(androidApplication(), get(), get()) }
    viewModel { AddViewModel(androidApplication(), get<NoteDatabase>().noteDao()) }

    single { androidApplication() as NotesApp }
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            "MainPrefs",
            Context.MODE_PRIVATE
        )
    }

    factory { Preferences() }
    factory { NotifChanelsCreator() }

    //MainActivity
    factory { NotesAdapter(listOf()) }
}

val dataModule = module {
    factory {
        Room.databaseBuilder(
            androidContext(),
            NoteDatabase::class.java,
            "note_database"
        ).build().noteDao()
    }
    factory { NoteRepository(get()) }
}