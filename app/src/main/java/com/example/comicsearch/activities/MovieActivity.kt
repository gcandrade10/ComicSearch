package com.example.comicsearch.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comicsearch.viewmodels.ComicVineViewModel
import com.example.comicsearch.api.ListAdapter
import com.example.comicsearch.api.Movie
import com.example.comicsearch.R
import kotlinx.android.synthetic.main.activity_movie.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieActivity : AppCompatActivity() {

    val comicVineViewModel: ComicVineViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        comicVineViewModel.fetchMovies()

        comicVineViewModel.popularMoviesLiveData.observe(this, Observer { movies ->
            Toast.makeText(this, "Llegaron: ${movies.size}", Toast.LENGTH_LONG)
            list_recycler_view.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = ListAdapter(movies, openActivity)
            }
        })

    }

    private val openActivity = { view: View, movie: Movie ->
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("id", movie.id)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchView = menu.findItem(R.id.searchView).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                return true // Don't close
            }

            override fun onQueryTextChange(query: String): Boolean {
                comicVineViewModel.search(query)
                return false
            }
        })
        return true
    }
}
