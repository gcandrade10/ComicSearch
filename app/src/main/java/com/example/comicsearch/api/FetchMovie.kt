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
    val id: Int,
    val name: String,
    val description: String?,
    val image: ImageUrl
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
    fun search(@Query("query") query: String): Deferred<Response<ComicVineMovieResponse>>

    @GET("movies")
    fun movies(): Deferred<Response<ComicVineMovieResponse>>
}
