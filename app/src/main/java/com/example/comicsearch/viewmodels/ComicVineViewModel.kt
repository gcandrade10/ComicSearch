package com.example.comicsearch.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.comicsearch.TAG
import com.example.comicsearch.api.Movie
import com.example.comicsearch.repositories.MovieRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ComicVineViewModel(private val repository: MovieRepository) : ViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    val moviesLiveData = MutableLiveData<List<Movie>>()

    init {
        fetchMovies()
    }

    private fun fetchMovies(search: String = "") {
        Log.d(TAG, "search: $search")
        scope.launch {
            var movies = repository.getPopularMovies()
            if (search.isNotEmpty()) {
                movies = repository.search(search)
            }
            moviesLiveData.postValue(movies)
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelAllRequests()
    }

    private fun cancelAllRequests() = coroutineContext.cancel()

    fun search(query: String) {

        fetchMovies(query)
    }
}