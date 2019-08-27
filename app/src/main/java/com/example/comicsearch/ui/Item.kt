package com.example.comicsearch.ui

import com.example.comicsearch.api.Movie

data class Item(
    val movie: Movie = Movie(),
    val spinner: Boolean = false
)