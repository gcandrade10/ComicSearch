package com.example.comicsearch.repositories

import android.content.Context
import androidx.room.Room
import com.example.comicsearch.*
import com.example.comicsearch.api.Apifactory
import com.example.comicsearch.api.ImageUrl
import com.example.comicsearch.database.MovieDatabase
import com.example.comicsearch.viewmodels.ComicVineViewModel
import com.example.comicsearch.viewmodels.MovieDetailViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single { DatabaseManager.database }
    single { MoshiManager.moshi }
    single {
        MovieRepository(
            Apifactory.comicVineApi,
            get()
        )
    }
    viewModel { ComicVineViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
}

object DatabaseManager {

    private val DATABASE_NAME = "phoenix-db"

    val createDatabase: (Context, String) -> MovieDatabase = { context: Context, databaseName: String ->
        Room.databaseBuilder(
            context,
            MovieDatabase::class.java, databaseName
        ).fallbackToDestructiveMigration().build()
    }

    val database by lazy {
        createDatabase(
            ComicSearchApplication.context,
            DATABASE_NAME
        )
    }
}

object MoshiManager {

    fun init(): JsonAdapter<ImageUrl> {
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<ImageUrl> = moshi.adapter(ImageUrl::class.java)
        return adapter
    }

    val moshi by lazy { init() }
}