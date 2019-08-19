package com.example.comicsearch

class MovieRepository(private val api: IComicVineApi) : BaseRepository() {

    suspend fun getPopularMovies(): MutableList<ComicVineMovie>? {
        //safeApiCall is defined in BaseRepository.kt (https://gist.github.com/navi25/67176730f5595b3f1fb5095062a92f15)
        val movieResponse = safeApiCall(
            call = { api.movies().await() },
            errorMessage = "Error Fetching Popular Movies"
        )
        return movieResponse?.results!!.toMutableList()
    }

    suspend fun search(query: String): MutableList<ComicVineMovie>? {
//safeApiCall is defined in BaseRepository.kt (https://gist.github.com/navi25/67176730f5595b3f1fb5095062a92f15)
        val movieResponse = safeApiCall(
            call = { api.search(query).await() },
            errorMessage = "Error Fetching Popular Movies"
        )
        return movieResponse?.results!!.toMutableList()
    }
}