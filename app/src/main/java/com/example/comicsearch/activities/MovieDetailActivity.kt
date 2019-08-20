package com.example.comicsearch.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.comicsearch.R
import com.example.comicsearch.viewmodels.MoviewDetailViewModel
import com.example.comicsearch.databinding.ActivityMovieDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailActivity : AppCompatActivity() {

    private val moviewDetailViewModel: MoviewDetailViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMovieDetailBinding>(
            this,
            R.layout.activity_movie_detail
        )
        val id = intent.getIntExtra("id", 0)
        moviewDetailViewModel.movieLiveData.observe(this, Observer { movie ->
            binding.movie = movie
        })
        moviewDetailViewModel.fetchMovie(id)
    }
}
