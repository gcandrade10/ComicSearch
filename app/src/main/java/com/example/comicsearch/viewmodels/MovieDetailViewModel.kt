package com.example.comicsearch.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.comicsearch.api.Movie
import com.example.comicsearch.repositories.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieDetailViewModel(private val repository: MovieRepository) : ViewModel() {
    val movieLiveData = MutableLiveData<Movie>()
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    fun fetchMovie(id: Int) {
        scope.launch {
            val popularMovies = repository.getMovie(id)
            movieLiveData.postValue(popularMovies)
        }
    }

}
