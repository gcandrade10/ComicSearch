package com.example.comicsearch.activities

import com.example.comicsearch.api.Movie

enum class ActionType {
    LIST_MOVIES,
    SEARCH_MOVIES
}

data class MovieListViewData(val movies: List<Movie>, val replaceMovies:Boolean)