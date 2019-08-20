package com.example.comicsearch.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.comicsearch.Apifactory.comicVineApi
import com.example.comicsearch.api.Movie
import com.example.comicsearch.database.MovieDatabase
import com.example.comicsearch.repositories.MovieRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ComicVineViewModel(private val dataBase: MovieDatabase) : ViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository: MovieRepository =
        MovieRepository(comicVineApi, dataBase)


    val popularMoviesLiveData = MutableLiveData<List<Movie>>()

    fun fetchMovies() {
        scope.launch {
            val popularMovies = repository.getPopularMovies()
            popularMoviesLiveData.postValue(popularMovies)
        }
    }


    fun cancelAllRequests() = coroutineContext.cancel()
    fun search(query: String) {
        if (query.isEmpty()) {
            fetchMovies()
        } else {
            scope.launch {
                val search = repository.search(query)
                popularMoviesLiveData.postValue(search)
            }
        }
    }

}