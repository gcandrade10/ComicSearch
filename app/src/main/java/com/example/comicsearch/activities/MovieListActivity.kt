package com.example.comicsearch.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.comicsearch.R
import com.example.comicsearch.TAG
import com.example.comicsearch.api.ListAdapter
import com.example.comicsearch.api.Movie
import com.example.comicsearch.viewmodels.ComicVineViewModel
import kotlinx.android.synthetic.main.activity_movie.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.concurrent.schedule


class MovieListActivity : AppCompatActivity() {
    private var loading = true
    val movieListViewModel: ComicVineViewModel by viewModel()
    private var timer = Timer()
    lateinit var listAdapter:ListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e(TAG, "onCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        listAdapter = ListAdapter(openActivity)
        list_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
            addOnScrollListener(scrollListener)
        }
        movieListViewModel.moviesLiveData.observe(this, Observer { movies ->
            loading = listAdapter.add(movies)
            spinner.visibility=View.GONE
        })
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = (recyclerView.layoutManager) as LinearLayoutManager
            val totalItemCount = layoutManager.itemCount
            val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
            if (totalItemCount == (lastVisibleItem + 1) && !loading) {
                loading = true
                movieListViewModel.loadMore()
            }
        }

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
                timer.cancel()
                val sleep = when (query.length) {
                    0 -> 1300L
                    1 -> 1000L
                    2, 3 -> 700L
                    4, 5 -> 500L
                    else -> 300L
                }
                timer = Timer()
                timer.schedule(sleep) {
                    runOnUiThread {
                        spinner.visibility = View.VISIBLE
                    }
                    movieListViewModel.search(query)
                }
                return false
            }
        })
        return true
    }
}
