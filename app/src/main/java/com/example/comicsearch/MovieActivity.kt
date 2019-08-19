package com.example.comicsearch

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchView = menu.findItem(R.id.searchView).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                return true // Don't close
            }

            override fun onQueryTextChange(query: String): Boolean {
                // This will ALWAYS work, because an empty query is a select all query
//                val cursor = helper.search(query)
//                mCursorAdapter.swapCursor(cursor)
                comicVineViewModel.search(query)
                return false
            }
        })
        return true
    }


}
