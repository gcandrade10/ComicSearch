package com.example.comicsearch.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IComicVineApi {
    @GET("search")
    fun searchMoviesAsync(@Query("query") query: String, @Query("page") page: Int): Deferred<Response<ComicVineMovieResponse>>

    @GET("listMoviesFromRemote")
    fun listMoviesFromRemoteAsync(@Query("offset") offset: Int): Deferred<Response<ComicVineMovieResponse>>
}