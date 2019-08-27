package com.example.comicsearch.api

import com.example.comicsearch.comicvineApiKey
import com.example.comicsearch.regularMoviesNumber
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiFactory {
    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("api_key", comicvineApiKey)
            .addQueryParameter("format", "json")
            .addQueryParameter("limit", "$regularMoviesNumber")
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val comicvineClient = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()

    private fun retrofit(): Retrofit = Retrofit.Builder()
        .client(comicvineClient)
        .baseUrl("https://comicvine.gamespot.com/api/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val comicVineApi: IComicVineApi = retrofit().create(IComicVineApi::class.java)
}
