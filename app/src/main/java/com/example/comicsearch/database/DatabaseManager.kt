package com.example.comicsearch.database

import android.content.Context
import androidx.room.Room
import com.example.comicsearch.ComicSearchApplication

object DatabaseManager {

    private const val DATABASE_NAME = "phoenix-db"
    val createDatabase: (Context, String) -> MovieDatabase = { context: Context, databaseName: String ->
        Room.databaseBuilder(
            context,
            MovieDatabase::class.java, databaseName
        ).fallbackToDestructiveMigration().build()
    }

    val database by lazy {
        createDatabase(ComicSearchApplication.context, DATABASE_NAME)
    }
}