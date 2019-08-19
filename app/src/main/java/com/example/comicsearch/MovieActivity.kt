package com.example.comicsearch

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_movie.*

class MovieActivity : AppCompatActivity() {

    private lateinit var comicVineViewModel: ComicVineViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        comicVineViewModel = ViewModelProviders.of(this).get(ComicVineViewModel::class.java)

        comicVineViewModel.fetchMovies()

        comicVineViewModel.popularMoviesLiveData.observe(this, Observer { movies ->
            Toast.makeText(this, "Llegaron: ${movies.size}", Toast.LENGTH_LONG)
            list_recycler_view.apply {
                // set a LinearLayoutManager to handle Android
                // RecyclerView behavior
                layoutManager = LinearLayoutManager(context)
                // set the custom adapter to the RecyclerView
                adapter = ListAdapter(movies)
            }
        })
    }
}
