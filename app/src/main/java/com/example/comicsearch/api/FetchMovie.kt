package com.example.comicsearch.api

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Data Model for Movie item
@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey
    val id: Int = Int.MAX_VALUE,
    val name: String = "",
    val description: String? = "",
    val image: ImageUrl = ImageUrl(thumb_url = "", medium_url = "")
)

class ImageUrl(
    var thumb_url: String,
    var medium_url: String
)

// Data Model for the Response returned from the TMDB Api
data class ComicVineMovieResponse(

    val results: List<Movie>
)

//A retrofit Network Interface for the Api
interface IComicVineApi {
    @GET("search")
    fun searchMovies(@Query("query") query: String, @Query("page") page: Int): Deferred<Response<ComicVineMovieResponse>>

    @GET("listMoviesFromRemote")
    fun listMoviesFromRemote(@Query("offset") offset: Int): Deferred<Response<ComicVineMovieResponse>>
}

data class Item(
    val movie: Movie = Movie(),
    val spinner: Boolean = false
)