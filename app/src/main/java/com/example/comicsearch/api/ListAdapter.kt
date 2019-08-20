package com.example.comicsearch.api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.comicsearch.R
import com.squareup.picasso.Picasso


class ListAdapter(
    private val list: List<Movie>, private val onClickListener: (View, Movie) -> Unit
) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: Movie = list[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { view ->
            onClickListener.invoke(view, movie)
        }
    }

    override fun getItemCount(): Int = list.size
}


class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_movie, parent, false)) {
    private var mTitleView: TextView? = null
    private var mImageView: ImageView? = null


    init {
        mTitleView = itemView.findViewById(R.id.textViewName)
        mImageView = itemView.findViewById(R.id.imageView)

    }

    fun bind(movie: Movie) {
        mTitleView?.text = movie.name
        Picasso.get().load(movie.image.thumb_url).into(mImageView)

    }

}