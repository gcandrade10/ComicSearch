package com.example.comicsearch.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.comicsearch.R
import com.example.comicsearch.databinding.ActivityMovieDetailBinding
import com.example.comicsearch.viewmodels.MovieDetailViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class MovieDetailActivity : AppCompatActivity() {
    private val movieDetailViewModel: MovieDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMovieDetailBinding>(
            this,
            R.layout.activity_movie_detail
        )
        val id = intent.getIntExtra("id", 0)
        movieDetailViewModel.movieLiveData.observe(this, Observer { movie ->
            binding.movie = movie
            supportActionBar?.title = movie.name
        })
        movieDetailViewModel.fetchMovie(id)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
