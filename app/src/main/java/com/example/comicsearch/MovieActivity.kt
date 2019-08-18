package com.example.comicsearch

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class MovieActivity : AppCompatActivity() {

    private lateinit var comicVineViewModel: ComicVineViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        comicVineViewModel = ViewModelProviders.of(this).get(ComicVineViewModel::class.java)

        comicVineViewModel.fetchMovies()

        comicVineViewModel.popularMoviesLiveData.observe(this, Observer {
            Toast.makeText(this, it.size, Toast.LENGTH_LONG)
            //TODO - Your Update UI Logic
        })

    }

}