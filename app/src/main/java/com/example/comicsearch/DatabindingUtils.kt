package com.example.comicsearch

import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.comicsearch.api.Movie
import com.squareup.picasso.Picasso

@BindingAdapter("movieImage")
fun ImageView.setImage(movie: Movie?) {
    movie?.let {
        Picasso.get().load(movie.image.medium_url).into(this)
    }
}

@BindingAdapter("text")
fun TextView.setText(movie: Movie?) {
    if (movie?.description != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.text = Html.fromHtml(movie.description, Html.FROM_HTML_MODE_LEGACY)
        } else {
            this.text = (Html.fromHtml(movie.description))
        }
        this.movementMethod = LinkMovementMethod.getInstance()
    }
}
