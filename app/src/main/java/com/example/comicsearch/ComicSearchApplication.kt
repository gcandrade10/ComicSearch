package com.example.comicsearch

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.comicsearch.repositories.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ComicSearchApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
        startKoin {
            androidLogger()
            androidContext(this@ComicSearchApplication)
            modules(repositoryModule)
        }
    }
}
