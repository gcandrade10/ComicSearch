package com.example.comicsearch.repositories

import android.util.Log
import com.example.comicsearch.TAG
import com.example.comicsearch.api.IComicVineApi
import com.example.comicsearch.api.Movie
import com.example.comicsearch.database.MovieDatabase

class MovieRepository(private val api: IComicVineApi, private val dataBase: MovieDatabase) : BaseRepository() {

    suspend fun listMoviesFromRemote(offset: Int, page: Int): List<Movie> {
        try {
            return listMoviesFromRemote(offset)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
        return dataBase.movieDao().listMoviesFromDB(offset = offset)
    }

    private suspend fun listMoviesFromRemote(offset: Int): MutableList<Movie> {
        val movieResponse = safeApiCall(
            call = { api.listMoviesFromRemoteAsync(offset = offset).await() },
            errorMessage = "Error Fetching Popular Movies"
        )
        val movies = movieResponse?.results!!.toMutableList()
        dataBase.movieDao().insertMovies(movies)
        return movies
    }

    suspend fun searchMovies(query: String, offset: Int, page: Int): List<Movie> {
        try {
            return searchMoviesFromRemote(query, page)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
        return dataBase.movieDao().searchMovieFromDB("%$query%", offset = offset)
    }

    private suspend fun searchMoviesFromRemote(query: String, page: Int): MutableList<Movie> {
        val movieResponse = safeApiCall(
            call = { api.searchMoviesAsync(query, page = page).await() },
            errorMessage = "Error Searching"
        )
        val movies = movieResponse?.results!!.toMutableList()
        dataBase.movieDao().insertMovies(movies)
        return movies
    }

    suspend fun getMovie(id: Int): Movie {
        return dataBase.movieDao().getMovie(id)
    }
}



