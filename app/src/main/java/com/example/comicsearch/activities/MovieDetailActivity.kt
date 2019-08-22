package com.example.comicsearch.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.comicsearch.viewmodels.MovieDetailViewModel
import com.example.comicsearch.databinding.ActivityMovieDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel
import android.view.MenuItem
import com.example.comicsearch.R


class MovieDetailActivity : AppCompatActivity() {

    private val moviewDetailViewModel: MovieDetailViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMovieDetailBinding>(
            this,
            R.layout.activity_movie_detail
        )
        val id = intent.getIntExtra("id", 0)
        moviewDetailViewModel.movieLiveData.observe(this, Observer { movie ->
            binding.movie = movie
            supportActionBar?.title=movie.name
        })
        moviewDetailViewModel.fetchMovie(id)
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
