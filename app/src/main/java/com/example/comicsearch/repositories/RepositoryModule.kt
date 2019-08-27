package com.example.comicsearch.repositories

import com.example.comicsearch.api.ApiFactory
import com.example.comicsearch.api.MoshiManager
import com.example.comicsearch.database.DatabaseManager
import com.example.comicsearch.viewmodels.ComicVineViewModel
import com.example.comicsearch.viewmodels.MovieDetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single { DatabaseManager.database }
    single { MoshiManager.moshi }
    single {
        MovieRepository(
            ApiFactory.comicVineApi,
            get()
        )
    }
    viewModel { ComicVineViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
}


