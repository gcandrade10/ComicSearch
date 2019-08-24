package com.example.comicsearch.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.comicsearch.activities.ActionType
import com.example.comicsearch.activities.MovieListViewData
import com.example.comicsearch.api.Movie
import com.example.comicsearch.repositories.MovieRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ComicVineViewModel(private val repository: MovieRepository) : ViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    val movieListLiveData = MutableLiveData<MovieListViewData>()
    private var movies = listOf<Movie>()
    private var offset = 0
    private var page = 0
    private var mType: ActionType? = null
    private var mQuery: String = ""

    init {
        listMovies()
    }

    private fun shouldReplaceMovies(type: ActionType, query: String = ""): Boolean {
        var replace = true
        mType?.let {
            if (query.isNotEmpty()) {
                replace = it != type || query != mQuery
                mQuery = query
            } else {
                replace = it != type
            }
        }
        mType = type
        if (replace) {
            offset = 0
            page = 0
        }
        return replace
    }

    private fun updateOffset() {
        offset += movies.size
        page++
    }

    private fun listMovies() {
        scope.launch {
            val replace = shouldReplaceMovies(ActionType.LIST_MOVIES)
            movies = repository.listMoviesFromRemote(offset, page)
            updateOffset()
            movieListLiveData.postValue(MovieListViewData(movies, replace))
        }
    }

    fun searchMovies(query: String) {
        if (query.isEmpty()) {
            listMovies()
        } else {
            val replace = shouldReplaceMovies(ActionType.SEARCH_MOVIES, query)
            scope.launch {
                movies = repository.searchMovies(query, offset, page)
                updateOffset()
                movieListLiveData.postValue(MovieListViewData(movies, replace))
            }
        }
    }

    fun loadMore() {
        searchMovies(mQuery)
    }

    override fun onCleared() {
        super.onCleared()
        cancelAllRequests()
    }

    private fun cancelAllRequests() = coroutineContext.cancel()
}