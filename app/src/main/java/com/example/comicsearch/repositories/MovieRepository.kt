package com.example.comicsearch.repositories

import com.example.comicsearch.api.IComicVineApi
import com.example.comicsearch.api.Movie
import com.example.comicsearch.database.MovieDatabase

class MovieRepository(private val api: IComicVineApi, private val dataBase: MovieDatabase) : BaseRepository() {

    suspend fun getPopularMovies(): List<Movie>? {
        try {
            return getPopularMoviesFromRemote()
        } catch (e: Exception) {
        }
        return dataBase.movieDao().getMoviewList()
    }

    suspend fun search(query: String): List<Movie>? {
        try {
            return searchFromRemote(query)
        } catch (e: Exception) {}
        return dataBase.movieDao().getSearchMovie("%$query%")
    }

    private suspend fun getPopularMoviesFromRemote(): MutableList<Movie> {
        val movieResponse = safeApiCall(
            call = { api.movies().await() },
            errorMessage = "Error Fetching Popular Movies"
        )
        val movies = movieResponse?.results!!.toMutableList()
        dataBase.movieDao().insertMovies(movies)
        return movies
    }
    private suspend fun searchFromRemote(query: String): MutableList<Movie>? {
        val movieResponse = safeApiCall(
            call = { api.search(query).await() },
            errorMessage = "Error Fetching Popular Movies"
        )
        val movies = movieResponse?.results!!.toMutableList()
        dataBase.movieDao().insertMovies(movies)
        return movies
    }

    suspend fun getMovie(id: Int): Movie {
        return dataBase.movieDao().getMovie(id)
    }
}



